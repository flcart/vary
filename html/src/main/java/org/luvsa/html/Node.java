package org.luvsa.html;

import java.util.Map;

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


}
