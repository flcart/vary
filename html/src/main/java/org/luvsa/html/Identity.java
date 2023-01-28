package org.luvsa.html;

import org.luvsa.html.Document.Holder;
import org.luvsa.lang.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aglet
 * @create 2023/1/28 16:39
 */
public class Identity implements Holder {
    private final Map<String, Node> cache = new HashMap<>();

    public Identity(Node root) {
        root.visit(node -> {
            var key = node.getId();
            if (Strings.isNotEmpty(key)){
                cache.put(key, node);
            }
        }, 0);
    }

    @Override
    public Node get(String id) {
        return cache.get(id);
    }
}
