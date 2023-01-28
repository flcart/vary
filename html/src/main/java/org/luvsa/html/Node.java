package org.luvsa.html;

import java.util.List;
import java.util.Map;

/**
 * @author Aglet
 * @create 2023/1/11 11:24
 */
public abstract class Node {
    public static final String TAB = "\t";

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

    public boolean isEmpty() {
        return false;
    }


    public void add(List<Node> list) {

    }

}
