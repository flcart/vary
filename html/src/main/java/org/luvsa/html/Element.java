package org.luvsa.html;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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

    private boolean finish;

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
    public String toString() {
        var suffix = "</" + label + '>';
        if (finish) {
            return suffix;
        }
        var attr = new StringJoiner(" ");
        attributes.forEach((s, o) -> attr.add(s + "=" + o));
        var trim = attr.toString();
        var prefix = '<' + this.label.toString() + (trim.isBlank() ? "" : " " + trim);
        if (children == null) {
            return prefix + "/>";
        }
        var builder = new StringBuilder(prefix + '>');
        if (label.isBlock()) {
            builder.append("\r\n");
        }
        for (var child : children) {
            builder.append(child);
        }
        if (label.isBlock()) {
            builder.append("\r\n");
        }
        builder.append(suffix);
        return builder.toString();
    }
}
