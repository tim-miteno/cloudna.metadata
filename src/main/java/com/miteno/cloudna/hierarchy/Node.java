package com.miteno.cloudna.hierarchy;

import java.util.Objects;

/**
 * Created by tim on 19/04/2017.
 */
public class Node implements Comparable<Node> {
    /**
     * 节点ID，ROOT节点的ID与其父级节点ID相同
     */
    private Long id;
    /**
     * 父级节点ID，ROOT节点的ID与其父级节点ID相同
     */
    private Long parentId;
    /**
     * 当前节点所在的层次结构代码,用于标识整棵树。
     */
    private String hierarchy;
    /**
     * 当前节点左值
     */
    private int left;
    /**
     * 当前节点右值
     */
    private int right;
    /**
     * 当前节点所在层次，从1开始计算
     */
    private int layer;

    /**
     * 测试对象是否包含指定节点
     * @param sub
     * @return
     */
    public boolean contains(Node sub){
        if(sub == null)
            return false;
        return sub.getLeft() >= this.getLeft() && sub.getRight() <= this.getRight() ;
    }

    /**
     * 判断一个节点是否是根节点
     * @return
     */
    public boolean isRoot() {
        if(Objects.equals(id, parentId)) return true;
        return false;
    }

    @Override
    public int compareTo(Node o) {
        if(this.id.equals(o.id)) {
            return 0;
        }
        if(this.left > o.left && this.right < o.right) {
            return -1;
        }
        if (this.left < o.left && this.right > o.right) {
            return 1;
        }
        throw new RuntimeException(String.format("错误的节点，两个节点（%d:%d）比较时，左右值范围重叠", this.id, o.id));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }
}
