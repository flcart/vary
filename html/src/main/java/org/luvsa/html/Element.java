package org.luvsa.html;

import java.util.*;

/**
 * @author Aglet
 * @create 2023/1/11 11:23
 */
public class Element extends Node {
    /**
     * 元素标签
     */
    private Label label;

    /**
     * 元素属性
     */
    private Map<String, Object> attributes;

    /**
     * 元素子标签
     */
    private List<Node> children;

    private final boolean finish;

    public Element(Label label, Map<String, Object> attributes, boolean finish) {
        this.label = label;
        this.attributes = attributes;
        this.finish = finish;
    }

    @Override
    protected boolean hasAttributes() {
        return false;
    }

    @Override
    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return label.getName();
    }

    @Override
    public boolean isFinished() {
        return finish;
    }

    @Override
    public boolean match(Node item) {
        return Objects.equals(item.getName(), getName());
    }

    @Override
    public boolean add(Node item) {
        super.add(item);
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(item);
        return true;
    }

    @Override
    public boolean isEmpty() {
        return label.isEmpty();
    }

    @Override
    public String toString() {
        var suffix = "</" + label + '>';
        if (finish) {
            return suffix;
        }
        var prefix = '<' + this.label.toString() + this.getAttr();
        if (children == null) {
            return prefix + "/>";
        }
        var builder = new StringBuilder();
        for (var child : children) {
            builder.append(child);
        }
        return prefix + '>' + builder + suffix;
    }


    private String getAttr() {
        if (attributes.isEmpty()) {
            return "";
        }
        var attr = new StringJoiner(" ");
        attributes.forEach((s, o) -> attr.add(s + "='" + o + '\''));
        return " " + attr;
    }
}
