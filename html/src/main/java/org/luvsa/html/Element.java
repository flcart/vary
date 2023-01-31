package org.luvsa.html;

import org.luvsa.lang.ContextHolder;
import org.luvsa.vary.Vary;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author Aglet
 * @create 2023/1/11 11:23
 */
public class Element extends Node {
    /**
     * 元素标签
     */
    private final Label label;

    /**
     * 元素属性
     */
    private final Map<String, Object> attributes;

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
    public Charset getCharset() {
        if (ContextHolder.get(false)) {
            return super.getCharset();
        }
        if (Objects.equals(label.getName(), "meta")) {
            var content = getAttribute("content");
            if (ObjectUtils.isEmpty(content)) {
                return null;
            }
            var change = Vary.change(content, String.class);
            var index = change.indexOf("charset");
            if (index < 0) {
                return null;
            }
            int from = -1, to = -1;
            for (int size = change.length(); index < size; index++) {
                var c = change.charAt(index);
                if (c == '=') {
                    from = index + 1;
                } else if (from > 0) {
                    if (c == ';' || c == ' ') {
                        to = index;
                        break;
                    }
                }
            }
            if (from > 0) {
                var s = to > from ? change.substring(from, to) : change.substring(from);
                return Charset.forName(s);
            }
        }
        return super.getCharset();
    }

    @Override
    public Object getAttribute(String key) {
        if (attributes == null) {
            return super.getAttribute(key);
        }
        return attributes.get(key);
    }

    @Override
    public String getName() {
        return label.getName();
    }

    @Override
    public boolean isFinished() {
        if (label.isEmpty()) {
            return false;
        }
        return finish && isBlank();
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
    public List<String> texts() {
        if (children == null || children.isEmpty()) {
            return super.texts();
        }
        if (label.isBlock()) {
            var list = new ArrayList<String>();
            collect(node -> {
                if (node instanceof Text text) {
                    list.add(text.toString());
                } else {
                    list.addAll(node.texts());
                }
            });
            return list;
        }
        var joiner = new StringJoiner(" ");
        collect(node -> {
            if (node instanceof Text text) {
                joiner.add(text.toString());
            } else {
                for (String text : node.texts()) {
                    joiner.add(text);
                }
            }
        });
        return List.of(joiner.toString());
    }

    private void collect(Consumer<Node> consumer) {
        if (children == null || children.isEmpty()) {
            return;
        }
        for (var child : children) {
            consumer.accept(child);
        }
    }

    @Override
    public void visit(Visitor visitor, int depth) {
        visitor.accept(this);
        if (this.children != null) {
            for (var child : this.children) {
                child.visit(visitor, depth + 1);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return label.isEmpty();
    }

    @Override
    protected String getId() {
        if (attributes == null) {
            return null;
        }
        var id = attributes.get("id");
        return Vary.change(id, String.class);
    }

    @Override
    protected String getClazz() {
        if (attributes == null) {
            return null;
        }
        var aClass = attributes.get("class");
        return Vary.change(aClass, String.class);
    }

    @Override
    public String toString() {
        var suffix = "</" + label + '>';
        if (finish) {
            if (isEmpty()) {
                return "<" + label + " />";
            }
            if (isBlank()) {
                return suffix;
            }
        }
        var prefix = '<' + this.label.toString() + this.getAttr();
        if (children == null) {
            return prefix + "/>";
        }
        var formatter = ContextHolder.get(Formatter.class);
        if (formatter == null) {
            var builder = new StringBuilder();
            for (var child : children) {
                builder.append(child);
            }
            return prefix + '>' + builder + suffix;
        }
        return formatter.build(prefix + '>', children, suffix);
    }

    private boolean isBlank() {
        return attributes == null || attributes.isEmpty();
    }

    private String getAttr() {
        if (isBlank()) {
            return "";
        }
        var attr = new StringJoiner(" ");
        attributes.forEach((s, o) -> attr.add(s + "='" + o + '\''));
        return " " + attr;
    }
}
