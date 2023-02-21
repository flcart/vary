package org.luvsa.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 叶子节点
 *
 * @author Aglet
 * @create 2023/2/18 13:39
 */
public abstract class Leaf extends Node {
    Object value;
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> attributes() {
        if (value instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        var core = this.value;
        var map = new HashMap<String, Object>();
        this.value = map;
        if (core != null) {
            map.put(getName(), core);
        }
        return map;
    }

    public Object get() {
        return attr(getName());
    }

    public void set(Object value) {
        attr(getName(), value);
    }

    @Override
    protected List<Node> getNodes() {
        return EMPTY;
    }

    @Override
    protected boolean hasAttribute() {
        return value instanceof Map<?, ?>;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void attr(String key, Object value) {
        if (this.value == null) {
            var name = getName();
            if (Objects.equals(key, name)) {
                this.value = value;
            } else {
                var map = new HashMap<>();
                map.put(key, value);
                this.value = map;
            }
        } else {
            super.attr(key, value);
        }
    }
}
