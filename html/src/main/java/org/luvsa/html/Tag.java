package org.luvsa.html;

import org.luvsa.html.Document.Holder;
import org.luvsa.lang.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aglet
 * @create 2023/1/28 17:07
 */
public class Tag implements Holder {
    private final Map<String, List<Node>> cache = new HashMap<>();

    public Tag(Node root) {
        root.visit(node -> {
            var name = node.getName();
            if (Strings.isNotEmpty(name)) {
                cache.computeIfAbsent(name, s -> new ArrayList<>()).add(node);
            }
        }, 0);
    }

    @Override
    public Object get(String id) {
        return null;
    }
}
