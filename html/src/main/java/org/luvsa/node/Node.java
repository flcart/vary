package org.luvsa.node;

import java.util.*;
import java.util.function.Consumer;

/**
 *  节点
 *
 * @author Aglet
 * @create 2023/2/18 10:29
 */
public abstract class Node {
    static final List<Node> EMPTY = Collections.emptyList();
    /**
     * 父节点
     */
    Node parent;
    /**
     * 下标
     */
    int index;

    protected Node() {
    }

    protected abstract List<Node> getNodes();

    public abstract String getName();

    public abstract Map<String, Object> attributes();

    protected abstract boolean hasAttribute();

    public void attr(String key, Object value) {
        // TODO 大小写问题
        var name = Objects.requireNonNullElse(key, "");
        attributes().put(name.toLowerCase(), value);
    }

    public Object attr(String key) {
        if (!hasAttribute()) {
            return null;
        }
        var name = Objects.requireNonNullElse(key, "");
        if (name.isBlank()) {
            return null;
        }
        var abs = "abs:";
        if (name.startsWith(abs)) {
            name = name.substring(abs.length()).trim();
        }
        var attributes = attributes();
        return attributes.get(name.toLowerCase());
    }

    /**
     * 获取指定下标的子节点
     *
     * @param index 下标
     * @return 节点
     */
    public Node child(int index) {
        return null;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Node> children() {
        var size = size();
        if (size == 0) {
            return EMPTY;
        }
        var list = new ArrayList<Node>(size);
        list.addAll(getNodes());
        return Collections.unmodifiableList(list);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Node parent() {
        return parent;
    }

    /**
     * 根节点
     *
     * @return 根节点
     */
    public Node root() {
        var node = this;
        while (node.parent != null)
            node = node.parent;
        return node;
    }

    /**
     * 删除当前 节点
     *
     * @return true： 表示删除成功
     */
    public boolean remove() {
        if (parent == null) {
            throw new IllegalStateException("No parent!");
        }
        return parent.remove(this);
    }

    protected boolean remove(Node node) {
        if (node == parent) {
            throw new IllegalArgumentException("Can't remove parent node");
        }
        if (node.parent == this.parent) {
            var index = node.index;
            var nodes = getNodes();
            nodes.remove(index);
            reindex(index);
            return true;
        }
        throw new IllegalArgumentException("Can't remove other node child!");
    }

    private void reindex(int from) {
        var nodes = getNodes();
        for (int i = from, size = nodes.size(); i < size; i++) {
            nodes.get(i).setIndex(i);
        }
    }

    /**
     * 获取相邻的下一个节点
     *
     * @return 节点
     */
    public Node next() {
        if (parent == null) {
            return null;
        }
        var nodes = parent.getNodes();
        var index = this.index + 1;
        return index < nodes.size() ? nodes.get(index) : null;
    }

    public Node prev() {
        if (parent == null) {
            return null;
        }
        var index = this.index - 1;
        if (index < 0) {
            return null;
        }
        return parent.getNodes().get(index);
    }

    public Node first() {
        var nodes = parent.getNodes();
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        return nodes.get(0);
    }

    public Node last() {
        var nodes = parent.getNodes();
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        return nodes.get(nodes.size() - 1);
    }

    public void visit(Visitor visitor) {
        visit(visitor, 0);
    }

    public void visit(Visitor visitor, int depth) {
        visitor.accept(this, depth);
        var nodes = getNodes();
        for (var node : nodes) {
            node.visit(visitor, depth + 1);
        }
    }

    public void forEach(Consumer<? super Node> action) {
        visit((node, depth) -> action.accept(node));
    }

    public abstract int size();

    public interface Visitor {
        void accept(Node node, int depth);
    }
}
