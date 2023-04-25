package org.luvsa.node;

import java.util.Map;
import java.util.Stack;

/**
 * @author Aglet
 * @create 2023/3/27 11:07
 */
final class Tokenizer {

    private final Reader reader;

    public Tokenizer(String html) {
        this.reader = new Reader(html);
    }

    public void run() {
        var stack = new Stack<Node>();
        while (reader.hasNext()) {
            var next = reader.next();
            if (stack.isEmpty()) {
                stack.push(next);
            }
        }
    }

    private static class Reader {
        private static final Map<Character, Character> map = Map.of('<', '>', '"', '"', '\'', '\'');
        private static final char START = '<', END = '>';
        private final char[] array;
        private int index;
        private final int length;
        private final Stack<Character> stack = new Stack<>();

        public Reader(String source) {
            this.array = source.trim().toCharArray();
            this.length = array.length;
        }

        public boolean hasNext() {
            return index < length;
        }

        public Node next() {
            var position = this.index;
            for (; this.index < length; index++) {
                var c = array[index];
                if (c == START) {
                    var s = new String(array, position, index - position);
                    if (!s.isBlank()){
                        return Text.of(s);
                    }
                    if (stack.isEmpty()) {
                        stack.push(END);
                    }
                } else if (c == END) {
                    var peek = stack.peek();
                    if (peek == c) {
                        stack.pop();
                        if (stack.isEmpty()) {
                            return Nodule.of(new String(array, position, ++index - position).trim());
                        }
                    }
                } else {
                    var item = map.get(c);
                    if (item == null) {
                        continue;
                    }
                    var peek = stack.peek();
                    if (peek == item) {
                        stack.pop();
                    } else {
                        stack.push(item);
                    }
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
