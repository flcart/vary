package org.luvsa.node;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * 元素
 *
 * @author Aglet
 * @create 2023/2/18 13:40
 */
public class Element implements Nodule {
    @Override
    public Node apply(String s) {
        if (s == null || s.isBlank() || s.startsWith("<!")) {
            return null;
        }
        var reader = new Reader(s);
        return reader.build();
    }

    private static class Reader {
        private final Stack<Character> stack = new Stack<>();
        private final Stack<String> cache = new Stack<>();
        private final Map<String, String> map = new HashMap<>();
        private final char[] array;
        private final boolean ended;
        private final boolean finished;
        private int index = 0;
        private int point = 0;

        public Reader(String s) {
            var sub = s.substring(1, s.length() - 1);
            this.ended = sub.startsWith("/");
            this.finished = sub.endsWith("/");
            var length = s.length();
            this.array = s.substring(ended ? 2 : 1, finished ? length - 2 : length - 1).trim().toCharArray();
        }

        public boolean isEnded() {
            return ended;
        }

        public boolean isFinished() {
            return finished;
        }

        public Builder build() {
            for (int size = array.length; index < size; index++) {
                var item = array[index];
                if (item == ' ') {
                    if (stack.isEmpty()) {
                        var next = next();
                        if (next.isBlank()) {
                            continue;
                        }
                        cache.add(next);
                    }
                } else if (item == '"' || item == '\'') {
                    if (stack.isEmpty()) {
                        point = index;
                        stack.push(item);
                    } else {
                        var peek = stack.peek();
                        if (peek == item) {
                            stack.pop();
                        }
                        if (stack.isEmpty()) {
                            var next = next();
                            if (next.isBlank()) {
                                continue;
                            }
                            var pop = cache.pop();
                            map.put(pop, next);
                        }
                    }
                } else if (item == '=') {
                    if (stack.isEmpty()) {
                        var next = next();
                        if (next.isBlank()) {
                            continue;
                        }
                        cache.add(next);
                    }
                }
            }

            if (cache.isEmpty()){

            }

            return null;
        }

        private String next() {
            var txt = new String(array, point, index - point + 1);
            point = index + 1;
            return txt;
        }
    }


    static class Builder extends Node {
        private Label label;
        protected List<Node> children;
        private Map<String, Object> attributes;
        protected WeakReference<List<Builder>> childrenRef;

        public Builder(String label) {
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

        public Builder append(String name) {
            var element = new Builder(name);
            append(element);
            return element;
        }

        public Builder append(Builder item) {
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

        protected List<Builder> getElements() {
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

        private List<Builder> element0() {
            var children = new ArrayList<Builder>(this.children.size());
            for (var child : this.children) {
                if (child instanceof Builder item) {
                    children.add(item);
                }
            }
            childrenRef = new WeakReference<>(children);
            return children;
        }
    }
}
