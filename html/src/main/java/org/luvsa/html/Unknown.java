package org.luvsa.html;

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
    protected boolean hasAttributes() {
        return attributes.isEmpty();
    }

    @Override
    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return name;
    }
}
