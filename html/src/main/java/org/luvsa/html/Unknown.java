package org.luvsa.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aglet
 * @create 2023/1/13 16:58
 */
public class Unknown extends Node {

    private final String name;

    private final boolean finish;

    private final Map<String, Object> attributes;

    /**
     * 元素子标签
     */
    private List<Node> children;

    public Unknown(String name, boolean finish, Map<String, Object> attributes) {
        this.name = name;
        this.finish = finish;
        this.attributes = attributes;
    }

    @Override
    public boolean isFinished() {
        return finish;
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
    public void visit(Visitor visitor, int depth) {
        visitor.accept(this);
        if (children == null) {
            return;
        }
        for (var child : this.children) {
            child.visit(visitor, depth + 1);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected boolean hasAttributes() {
        return false;
    }

    @Override
    public String baseUri() {
        return null;
    }
}
