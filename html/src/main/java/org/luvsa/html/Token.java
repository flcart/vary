package org.luvsa.html;

import lombok.Data;
import org.luvsa.lang.Charsets;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2023/1/11 13:48
 */
@Data
class Token {
    private String name;
    private boolean finish;
    private boolean doctype;
    private Map<String, Object> attributes = new HashMap<>();
    private String current;
    private boolean flag;

    public Node build(String s) {
        var node = create(s);
        this.attributes.clear();
        this.name = null;
        this.finish = false;
        this.doctype = false;
        this.current = null;
        return node;
    }

    private Node create(String s) {
        if (doctype) {
            return new Document(attributes.keySet(), current, s);
        }
        var attr = Map.copyOf(this.attributes);
        var label = Label.get(Objects.requireNonNullElse(this.name, s));
        if (label == null) {
            return new Unknown(name, finish, attr);
        }
        return new Element(label, attr, finish);
    }

    public void setCurrent(String current) {
        if (current == null || current.isBlank()) {
            return;
        }
        this.current = current;
        this.flag = true;
    }

    public void accept(String s) {
        if (s == null || s.isBlank()) {
            return;
        }
        if (name == null) {
            this.name = s;
        } else if (this.current == null) {
            this.current = s;
        } else {
            attributes.put(current, null);
            this.current = s;
        }
    }

    public void set(String value) {
        if (current == null) {
            throw new IllegalArgumentException(value);
        }
        if (flag) {
            this.attributes.put(this.current, value);
        } else {
            this.attributes.put(this.current, null);
            this.current = value;
        }
        flag = false;
    }

    static final class Reader {
        private final char[] array;
        private int index = 0;

        public Reader(byte[] bytes) {
            var charset = Charsets.guess(bytes);
            var wrap = ByteBuffer.wrap(bytes);
            var decode = charset.decode(wrap);
            this.array = decode.array();
        }

        public char read() {
            if (index < array.length) {
                return array[index++];
            }
            return Character.MIN_VALUE;
        }

        public char[] chars(int position) {
//            var size = index - position;
//            if (size <= 0) {
//                return null;
//            }
//            var array = new char[size];
//            System.arraycopy(this.array, index, array, 0,
//                    Math.min(this.array.length, size));
            return array;
        }

        public int index() {
            return index;
        }

        public char[] sub(int from){
            return sub(from, index);
        }

        public char[] sub(int from, int to) {
            var size = to - from;
            if (size <= 0) {
                return null;
            }
            var array = new char[size];
            System.arraycopy(this.array, index, array, 0,
                    Math.min(this.array.length, size));
            return array;
        }
    }
}
