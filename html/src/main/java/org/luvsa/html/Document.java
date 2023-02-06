package org.luvsa.html;

import org.luvsa.lang.Arrays;
import org.luvsa.lang.ContextHolder;
import org.luvsa.lang.Strings;
import org.luvsa.vary.Vary;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2023/1/11 13:45
 */
public class Document extends Node {
    /**
     * doctype 的属性
     */
    private final List<String> attr;

    private final Map<String, Holder> holders = new HashMap<>();

    /**
     * 子 html node
     */
    private Node html;

    public Document(Collection<String> values, String... array) {
        this.attr = Arrays.merge(values, Strings::isNotEmpty, array);
    }

    @Override
    public boolean add(Node item) {
        super.add(item);
        this.html = item;
        return true;
    }

    @Override
    public List<String> texts() {
        return html.texts();
    }

    @Override
    public String getName() {
        return "DOCTYPE";
    }

    @Override
    protected boolean hasAttributes() {
        return false;
    }

    @Override
    public String baseUri() {
        return null;
    }

    @Override
    public String toString() {
        var text = "<!doctype " + String.join(" ", attr) + '>';
        if (html == null) {
            return text;
        }
        var conf = ContextHolder.get(Formatter.class);
        if (conf == null) {
            return text + html;
        }
        return conf.build(text, html);
    }

    @Override
    public void visit(Visitor visitor, int depth) {
        visitor.accept(this);
        html.visit(visitor, depth);
    }

    public Node getElementById(String id) {
        var target = holders.computeIfAbsent("IDENTITY", k -> new Repertory(this, Node::getId)).get(id);
        return Vary.change(target, Node.class);
    }

    public List<Node> getElementsByClass(String clazz) {
        var target = holders.computeIfAbsent("CLASS", k -> new Repertories(this, node -> {
            var clas = node.getClazz();
            if (Strings.isEmpty(clas)) {
                return null;
            }
            return clas.trim().split(" ");
        })).get(clazz);
        return Vary.asList(target, Node.class);
    }

    public List<Node> getElementsByTag(String tagName) {
        var target = holders.computeIfAbsent("TAG", s -> new Repertories(this, Node::getName)).get(tagName);
        return Vary.asList(target, Node.class);
    }

    public interface Holder {

        Object get(String key);

    }


    private static class Repertories implements Holder {
        private final Map<String, List<Node>> cache = new HashMap<>();

        public Repertories(Node root, Function<Node, ?> function) {
            root.visit(node -> {
                var o = function.apply(node);
                if (o == null) {
                    return;
                }
                var aClass = o.getClass();
                if (o instanceof String key && Strings.isNotEmpty(key)) {
                    cache.computeIfAbsent(key, s -> new ArrayList<>()).add(node);
                } else if (o instanceof List<?> list) {
                    for (var item : list) {
                        if (item instanceof String key && Strings.isNotEmpty(key)) {
                            cache.computeIfAbsent(key, s -> new ArrayList<>()).add(node);
                        }
                    }
                } else if (aClass.isArray()) {
                    for (int i = 0, size = Array.getLength(o); i < size; i++) {
                        var item = Array.get(o, i);
                        if (item instanceof String key && Strings.isNotEmpty(key)) {
                            cache.computeIfAbsent(key, s -> new ArrayList<>()).add(node);
                        }
                    }
                }
            }, 0);
        }

        @Override
        public List<Node> get(String key) {
            return cache.get(key);
        }
    }

    private static class Repertory implements Holder {
        private final Map<String, Node> cache = new HashMap<>();

        public Repertory(Node root, Function<Node, String> function) {
            root.visit(node -> {
                var key = function.apply(node);
                if (Strings.isNotEmpty(key)) {
                    cache.put(key, node);
                }
            }, 0);
        }

        @Override
        public Node get(String key) {
            return cache.get(key);
        }
    }
}
