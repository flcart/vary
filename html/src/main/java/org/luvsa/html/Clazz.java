package org.luvsa.html;

import org.luvsa.html.Document.Holder;
import org.luvsa.lang.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aglet
 * @create 2023/1/28 16:48
 */
public class Clazz implements Holder {

    private final Map<String, List<Node>> cache = new HashMap<>();
    public Clazz(Node root) {
        root.visit(node -> {
            var clazz = node.getClazz();
            if (Strings.isEmpty(clazz)){
                return;
            }
            var array = clazz.trim().split(" ");
            for (var s : array) {
                if (Strings.isNotEmpty(s)){
                    cache.computeIfAbsent(s, key -> new ArrayList<>()).add(node);
                }
            }
        }, 0);
    }

    @Override
    public Object get(String id) {
        return cache.get(id);
    }
}
