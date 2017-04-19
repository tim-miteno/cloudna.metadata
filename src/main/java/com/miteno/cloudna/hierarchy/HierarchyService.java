package com.miteno.cloudna.hierarchy;

/**
 * Created by tim on 19/04/2017.
 */
public interface HierarchyService<T extends Node> {

    /**
     * 创建一个层次结构的顶级
     * @param node 节点信息
     * @return
     */
    void create(T node);
    /**
     * 将一个节点添加为指定父节点的最后一个子节点
     * @param node 节点信息
     * @see Node
     */
    void addLast(T node);

    /**
     * 将一个节点添加为指定父节点的第一个子节点
     * @param node 节点信息
     * @see Node
     */
    void addFirst(T node);

    /**
     * 将一个节点添加到指定节点的左侧
     * @param node 节点信息
     * @param refId 参考节点
     */
    void addLeft(T node, Long refId);

    /**
     * 将一个节点添加到指定节点的右侧
     * @param node 节点信息
     * @param refId 参考节点
     */
    void addRight(T node, Long refId);

    /**
     * 删除一个节点
     * @param id 要删除的节点ID
     */
    void delete(Long id);

    /**
     * 将一个节点左移一位
     * @param id 要移动的节点ID
     */
    void shiftLeft(Long id);

    /**
     * 将一个节点右移一位
     * @param id 要移动的节点ID
     */
    void shiftRight(Long id);

    /**
     * 将一个节点移动到本级节点的最后一位（不会跨越父节点）
     * @param id 要移动的节点ID
     */
    void shiftToLast(Long id);

    /**
     * 将一个节点移动到本级节点的第一位（不会跨越父节点）
     * @param id 要移动的节点ID
     */
    void shiftToFirst(Long id);

    /**
     * 将一个节点移动到另一个节点之下，新的父节点不能是当前节点的子节点或原父节点
     * @param id 要移动的节点
     * @param parentId 新的父节点
     * @return 移动是否成功
     */
    boolean changeParent(Long id, Long parentId);
}
