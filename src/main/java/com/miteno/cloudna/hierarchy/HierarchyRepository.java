package com.miteno.cloudna.hierarchy;

/**
 * 负责层次结构持久化的接口
 * @author Tim.Huang
 */
public interface HierarchyRepository<T extends Node> {
    /**
     * 通过ID找到节点信息
     * @param id
     * @return 节点信息，若找不到，则返回null
     */
     T findNode(Long id);

    /**
     * 查找兄弟节点
     * @param idx 若查找左兄弟节点，则为当前节点的左值；否则为当前节点的右值
     * @param layer 节点所在层次
     * @param direction 查找方向，0为左，其它为右
     * @return 找到的节点，若找不到，则返回null
     * <br/>
     * select * from ... where right = :idx-1 and layer = :layer --左兄弟<br/>
     * select * from ... where left = :idx+1 and layer = :layer --右兄弟<br/>
     */
    T findBrother(String hierarchy, int idx, int layer, int direction);
    /**
     * 将右边的节点移动几个位置，腾出空间给新元素
     * @param baseLeft 基准左值
     * @param baseRight 基准右值
     * @param step 移动几位
     * <br/>
     * update ... set left = left+2*step,
     *         right = right+2*step
     *         where left > baseLeft and right > baseRight
     */
    void shiftRightNode(String hierarchy, int baseLeft, int baseRight, int step);


    /**
     * 调整某个节点的容量,以适应新的大小（该节点的父节点也会同步调整）
     * @param baseLeft 需要调整的节点的左值
     * @param baseRight 需要调整的节点的右值
     * @param n 元素数量
     * <br/>
     * update ... set right = right+2*n
     *     where left <= baseLeft and right >= baseRight
     */
    void resize(String hierarchy, int baseLeft, int baseRight, int n);

    /**
     * 删除基准节点,包括所有子节点
     * @param baseLeft
     * @param baseRight
     * <br/>
     * delete ... where left >= baseLeft and right <= baseRight
     */
    void delete(String hierarchy, int baseLeft, int baseRight);

    /**
     * 修改单个节点信息
     * @param node 节点信息
     * @param layerDelta 层次距离
     * @param distance 移动距离
     * <br/>
     * update .... set left = left+distance, right = right+distance, layer+layerDelta where left >= node.left and right <= node.right
     */
    void shift(T node, int layerDelta, int distance);

    /**
     * 获取整棵树最大的右值
     * @return
     */
    int getMaxRight(String hierarchy);
}
