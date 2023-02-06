package org.luvsa.html;

import org.luvsa.lang.Strings;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * @author Aglet
 * @create 2023/1/11 11:24
 */
public abstract class Node {

    static final List<Node> NODES = Collections.emptyList();

    static final String EMPTY = "";
    /**
     * 父节点
     */
    private Node parent;

    int index;

    public static Node of(char[] array) {
        if (array == null) {
            return null;
        }
        var s = new String(array);
        if (s.isBlank()) {
            return null;
        }


        return null;
    }

    protected Node() {
    }

    /**
     * 获取此节点的节点名称。用于调试目的，而不是逻辑切换（为此，使用实例）
     *
     * @return 节点名称
     */
    public abstract String getName();

    protected abstract boolean hasAttributes();

    public abstract String baseUri();

    public boolean hasParent() {
        return parent != null;
    }

    public String attr(String key) {
        if (Strings.isEmpty(key)) {
            throw new IllegalArgumentException("Key must not be null");
        }
//        if (hasAttributes()) {
////            var val = attributes().getIgnoreCase(key);
////            if (Strings.isNotEmpty(val))
////                return val;
////            if (key.startsWith("abs:"))
////                return absUrl(key.substring(4));
//        }
        return EMPTY;
    }

    public String absUrl(String key) {
//        if (Strings.isEmpty(key))
//            throw new IllegalArgumentException("Key must not be empty");
//        if (hasAttributes() && attributes().hasKeyIgnoreCase(key)) { // not using hasAttr, so that we don't recurse down hasAttr->absUrl
//            return Util.resolve(baseUri(), attributes().getIgnoreCase(key));
//        }
        return "";
    }


//    public abstract Attributes attributes();

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

    public boolean close() {
        return false;
    }

    public boolean open() {
        return false;
    }

    boolean isStarted() {
        return false;
    }

    public Describe getInfo() {
        throw new IllegalStateException();
    }

    /**
     * Node 详细描述信息
     */
    interface Describe {

        boolean isOpen();

        boolean isFinished();
    }

    /**
     * Node 的访问器， 用于遍历 Node 树
     */
    public interface Visitor {

        void accept(Node node);

    }


    public int size() {
        return 0;
    }
}
