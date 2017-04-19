package com.miteno.cloudna.hierarchy;

import java.util.*;

/**
 * Created by tim on 19/04/2017.
 */
public class Hierarchy<T extends Node> {
    private T node;
    private List<Hierarchy<T>> children;
    private boolean sorted = true;
    private Comparator<Hierarchy<T>> comparator;

    public Hierarchy(final Comparator<T> comparator)
    {
        this.children = new ArrayList<>();
        this.comparator = (o1, o2) -> comparator.compare(o1.getNode(), o2.getNode());
    }

    public static <T extends Node> Hierarchy<T> init(List<T> elements, String rootId, Comparator<T> comparator)
    {

        Map<Long, Hierarchy<T>> childrenMap = new HashMap<>();

        /**
         * 遍历所有节点，使用Map整理所有节点，以便快速使用Hierarchy建立各父子节点的关系
         */
        for(T n : elements)
        {
            Hierarchy<T> parent = childrenMap.get(n.getParentId());
            if(parent == null)
            {
                parent = new Hierarchy<>(comparator);
                childrenMap.put(n.getParentId(), parent);
            }

            Hierarchy<T> child = childrenMap.get(n.getId());
            if(child == null)
            {
                child = new Hierarchy<>(comparator);
                childrenMap.put(n.getId(), child);
            }
            child.setNode(n);
            parent.addChild(child);
        }

        Hierarchy<T> root = childrenMap.get(rootId);
        if(root != null)
        {
            root.normalize();
        }

        return root;
    }


    /**
     * 向当前层次结构中增加一个子层次
     * @param child 子层次
     */
    public void addChild(Hierarchy<T> child)
    {
        this.children.add(child);
        sorted = false;
    }


    public int normalize(int i, int layer)
    {
        if(node != null)
        {
            node.setLayer(layer++);
            node.setLeft(i++);
        }

        if(!sorted) {
            Collections.sort(children, comparator);
            sorted = true;
        }

        for(Hierarchy<T> child : children)
        {
            i = child.normalize(i, layer);
        }

        if(node != null)
        {
            node.setRight(i);
        }
        return ++i;
    }

    public int normalize()
    {
        return normalize(1,1);
    }

    public List<T> toList()
    {
        List<T> list = new ArrayList<T>();
        toList(this, list);
        return list;
    }

    private void toList(Hierarchy<T> hierarchy, List<T> list)
    {
        if(hierarchy.getNode() != null)
            list.add(hierarchy.getNode());
        for(Hierarchy<T> child : hierarchy.getChildren())
        {
            toList(child, list);
        }
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }

    public List<Hierarchy<T>> getChildren() {
        return children;
    }

}
