package com.miteno.cloudna.hierarchy;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tim on 19/04/2017.
 */
public class HierarchyServiceImpl implements HierarchyService<Node> {

    @Autowired
    private HierarchyRepository<Node> repo;

//    public void setHierarchyRepository(HierarchyRepository<Node> hierarchyRepository) {
//        this.repo = hierarchyRepository;
//    }


    @Override
    public void create(Node node) {
        node.setLeft(1);
        node.setRight(2);
        node.setLayer(1);

    }

    @Override
    public void addLast(Node node) {
        Node parent = null;
        if(node.getParentId() != null)
            parent = repo.findNode(node.getParentId());
        if(parent != null)
        {
            node.setLeft(parent.getRight());
            node.setRight(node.getLeft()+1);
            node.setLayer(parent.getLayer()+1);
        }
        else
        {
            int maxRight = repo.getMaxRight(node.getHierarchy());
            node.setLeft(maxRight+1);
            node.setRight(node.getLeft()+1);
            node.setLayer(1);
        }

        doAdd(node, parent);
    }

    @Override
    public void addFirst(Node node) {
        Node parent = null;
        if(node.getParentId() != null)
            parent = repo.findNode(node.getParentId());
        if(parent != null)
        {
            node.setLeft(parent.getLeft()+1); //取父节点左值+1为左值
            node.setRight(node.getLeft()+1); //新节点仅有一个元素，右值+1
            node.setLayer(parent.getLayer()+1);
        }
        else
        {
            node.setLeft(1);
            node.setRight(2);
            node.setLayer(1);
        }

        doAdd(node, parent);
    }

    @Override
    public void addLeft(Node node, Long refId) {
        Node ref = repo.findNode(refId);
        Node parent = null;
        if(node.getParentId() != null)
            parent = repo.findNode(ref.getParentId());
        node.setLeft(ref.getLeft());//以参考节点左值为左值，占用参考节点的位置
        node.setRight(node.getLeft()+1);
        node.setLayer(ref.getLayer());
        node.setParentId(ref.getParentId());

        doAdd(node, parent);
    }

    @Override
    public void addRight(Node node, Long refId) {
        Node ref = repo.findNode(refId);
        Node parent = null;
        if(node.getParentId() != null)
            parent = repo.findNode(ref.getParentId());
        node.setLeft(ref.getRight()+1); //以参考节点右值+1为左值，放在该节点右边
        node.setRight(node.getLeft()+1);
        node.setLayer(ref.getLayer());
        node.setParentId(ref.getParentId());

        doAdd(node, parent);
    }

    @Override
    public void delete(Long id) {
        Node node = repo.findNode(id);
        int n = (node.getRight()-node.getLeft()+1)/2;

        repo.delete(node.getHierarchy(), node.getLeft(), node.getRight());
        repo.resize(node.getHierarchy(), node.getLeft(), node.getRight(), -n);
        repo.shiftRightNode(node.getHierarchy(), node.getLeft(), node.getRight(), -n);
    }

    @Override
    public void shiftLeft(Long id) {
        Node node = repo.findNode(id);
        Node left = repo.findBrother(node.getHierarchy(), node.getLeft(), node.getLayer(), 0);
        if(left == null)
            return;
        shift(node, -1);
    }

    @Override
    public void shiftRight(Long id) {
        Node node = repo.findNode(id);
        Node right = repo.findBrother(node.getHierarchy(), node.getLeft(), node.getLayer(), 1);
        if(right == null)
            return;
        shift(node, 1);
    }

    @Override
    public void shiftToLast(Long id) {
        Node node = repo.findNode(id);
        Node parent = repo.findNode(node.getParentId());
        int distance;
        if(parent != null)
            distance = (parent.getRight() - node.getRight() + 1)/2; //追加在末位，右向可扩展，距离比实际多1
        else
            distance = (repo.getMaxRight(node.getHierarchy())+1 - node.getRight() + 1)/2;
        if(distance != 0)
            shift(node, distance);
    }

    @Override
    public void shiftToFirst(Long id) {
        Node node = repo.findNode(id);
        Node parent = repo.findNode(node.getParentId());
        int distance;

        if(parent != null)
            distance = (parent.getLeft() - node.getLeft() + 1)/2;//占用该层次首位的位置，左向不可扩展，因此计算出实际距离
        else
            distance = (1 - node.getLeft())/2;
        if(distance != 0)
            shift(node, distance);
    }

    @Override
    public boolean changeParent(Long id, Long parentId) {
        Node node = repo.findNode(id);
        if(node.getParentId().equals(parentId)) return true;

        Node newParent = repo.findNode(parentId);

        if(node.contains(newParent)) //不能将一个节点移到它的子节点内
            return false;

        Node oldParent = repo.findNode(node.getParentId());

        int layerDelta = newParent.getLayer() - node.getLayer() +1; //层次变化
        int distance = newParent.getRight() - node.getLeft();
        int n = (node.getRight() - node.getLeft() +1)/2;

        repo.shiftRightNode(node.getHierarchy(), newParent.getRight(), newParent.getRight()+1, n); //增加到新父节点的末位,新父节点后的所有元素右移n位，为新元素腾出空间

        repo.resize(node.getHierarchy(), newParent.getLeft(), newParent.getRight(), n);//新父节点容量增加n位，以便容纳新元素

        if(distance < 0)//左移的情况下，原节点左右值已经发生变化，需要重设；距离也发生了变化（增加了n*2），需要增加n*2
        {
            node.setLeft(node.getLeft()+n*2);
            node.setRight(node.getRight()+n*2);
            oldParent.setLeft(oldParent.getLeft()+n*2);//父节点右移了
            oldParent.setRight(oldParent.getRight()+n*2);
            repo.shift(node, layerDelta, distance-n*2); //旧元素更改左右值及层次深度

        }
        else//右移情况下，原节点左右值未被更改，可直接移动
        {
            repo.shift(node, layerDelta, distance);

        }
        repo.shiftRightNode(node.getHierarchy(), node.getLeft(), node.getRight(), -n);//原位置之后的元素,左移n位

        repo.resize(node.getHierarchy(), oldParent.getLeft(), oldParent.getRight(), -n);//原父节点容量变小

        return true;
    }

    private void shift(Node node, int distance)//XXX 未经测试的方法
    {
        if(distance == 0)
            return;
        int n = (node.getRight() - node.getLayer() +1)/2; //node子节点数
        int newLeft = node.getLeft() + distance; //计算移动后的左值/右值
        int newRight = node.getRight() + distance;


        if(distance < 0) //往左移，此时node的左右值已经被更改，需要更新
        {
            repo.shiftRightNode(node.getHierarchy(), newLeft-1, newLeft-1, n); //左向不可扩展，目标位置原先已有元素，也要向后移
            node.setLeft(newLeft);
            node.setRight(newRight);
            repo.shift(node, 0, distance-n*2); //node与目标的的距离拉开了n个位置
            repo.shiftRightNode(node.getHierarchy(), node.getLeft(), node.getRight(), -n);//node被挪走，node右边的所有节点前移
        }
        else
        {
            repo.shiftRightNode(node.getHierarchy(), newLeft, newLeft, n);
            repo.shift(node, 0, distance+n*2);
            repo.shiftRightNode(node.getHierarchy(), node.getLeft(), node.getRight(), -n);//node被挪走，node右边的所有节点前移
        }

    }

    /**
     * 为parent增加一个节点node
     * @param node 要增加的节点，左值、右值及层次均已计算好
     * @param parent 父节点
     */
    private void doAdd(Node node, Node parent)
    {
        /*
         * step 1. 后续节点左右值均+2,腾出位置给新节点
         * update ... set left = left+2, right = right+2 where left > parent.left and right > parent.right
         */
        repo.shiftRightNode(node.getHierarchy(), node.getLeft()-1, node.getRight()-1, 1);
        /*
         * step 2. 本家族所有父级节点右值+2，表达增加了一个孩子
         * update ... set right = right+2 where left <= parent.left and right >= parent.right
         */
        if(parent != null)
            repo.resize(node.getHierarchy(), parent.getLeft(), parent.getRight(), 1);
        /*
         * step 3. 插入新的节点
         * insert into ... (left, right, layer ...) values(node.left, node.right, node.layer, ...)
         */
        //repo.save(node);
    }
}
