package org.luvsa.node;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * 元素
 *
 * @author Aglet
 * @create 2023/2/18 13:40
 */
public class Element extends Node {
    private Label label;
    protected List<Node> children;
    private Map<String, Object> attributes;

    protected WeakReference<List<Element>> childrenRef;

    public Element(String label) {

    }

    @Override
    protected List<Node> getNodes() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    @Override
    public Map<String, Object> attributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }
        return attributes;
    }

    public Element append(String name) {
        var element = new Element(name);
        append(element);
        return element;
    }

    public Element append(Element item) {
        var nodes = getNodes();
        var size = nodes.size();
        nodes.add(item);
        setIndex(size);
        item.setParent(this);
        return this;
    }

    @Override
    protected boolean hasAttribute() {
        return attributes != null;
    }

    @Override
    public String getName() {
        return label.getName();
    }

    @Override
    public int size() {
        return 0;
    }

    protected List<Element> getElements() {
        if (this.children == null) {
            return Collections.emptyList();
        }
        if (childrenRef == null) {
            return element0();
        }
        var elements = childrenRef.get();
        if (elements == null) {
            return element0();
        }
        return elements;
    }

    private List<Element> element0() {
        var children = new ArrayList<Element>(this.children.size());
        for (var child : this.children) {
            if (child instanceof Element item) {
                children.add(item);
            }
        }
        childrenRef = new WeakReference<>(children);
        return children;
    }
}
