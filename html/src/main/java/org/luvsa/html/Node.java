package org.luvsa.html;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Aglet
 * @create 2023/1/11 11:24
 */
public abstract class Node {
    /**
     * 父节点
     */
    private Node parent;

    public boolean hasParent() {
        return parent != null;
    }

    protected abstract boolean hasAttributes();

    public Map<String, Object> attributes() {
        throw new UnsupportedOperationException();
    }

    public abstract String getName();

    public boolean isFinished() {
        return false;
    }

    public boolean match(Node item) {
        return false;
    }

    public boolean add(Node item) {
        item.parent = this;
        return true;
    }

    protected String getClazz() {
        return null;
    }

    protected String getId() {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public void add(List<Node> list) {
    }

    public abstract void visit(Node.Visitor visitor, int depth);

    public interface Visitor {
        void accept(Node node);

    }


    protected void forEach(Consumer<Node> action) {
    }

    protected boolean hasChildren() {
        return false;
    }

    private Node next() {
        return null;
    }

    public int size() {
        return 0;
    }
}
