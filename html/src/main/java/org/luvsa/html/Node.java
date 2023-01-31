package org.luvsa.html;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * @author Aglet
 * @create 2023/1/11 11:24
 */
public abstract class Node {
    /**
     * 父节点
     */
    private Node parent;

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

    public List<String> texts() {
        return Collections.emptyList();
    }

    public Object getAttribute(String key) {
        return null;
    }

    public Charset getCharset() {
        return null;
    }

    public interface Visitor {
        void accept(Node node);

    }


    public int size() {
        return 0;
    }
}
