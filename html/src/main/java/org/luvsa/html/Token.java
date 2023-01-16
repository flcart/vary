package org.luvsa.html;

import lombok.Data;

import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.concurrent.Flow.Subscription;

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
        var attr = Collections.unmodifiableMap(attributes);
        var label = Label.get(s);
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
            attributes.put(current, s);
            this.current = null;
        }
    }

    public void set(String value) {
        if (current == null) {
            throw new IllegalArgumentException(value);
        }
        this.attributes.put(this.current, value);
    }

    public static final class Reader implements Subscription, Traverser {

        static final int MAX_BUFFER_LEN = 1024 * 32;
        private static final int MIN_READ_AHEAD_LEN = 1024;
        static final int READ_AHEAD_LIMIT = (int) (MAX_BUFFER_LEN * 0.75);
        /**
         * 字符流
         */
        private final java.io.Reader reader;

        /**
         * 缓冲区
         */
        private final char[] buffer;

        private final List<Integer> lines = new ArrayList<>();

        /**
         * 已经读取完成
         */
        private boolean finished;

        /**
         * 当前 字符 位置
         */
        private int position;

        /**
         * 下一个标记点
         */
        private int point;

        /**
         * 标记
         */
        private int mark = -1;

        /**
         * 读取的 buffer 长度
         */
        private int length;

        /**
         * 起始-结束位置
         */
        private int index;

        public Reader(String input) {
            this(new StringReader(input), input.length());
        }

        public Reader(java.io.Reader input) {
            this(input, MAX_BUFFER_LEN);
        }

        public Reader(java.io.Reader reader, int size) {
            if (reader == null) {
                throw new NullPointerException("Reader source is null");
            }
            if (!reader.markSupported()) {
                throw new IllegalArgumentException("Required supported mark reader : " + reader.getClass());
            }
            this.reader = reader;
            this.buffer = new char[Math.min(size, MAX_BUFFER_LEN)];
            forward();
        }

        public static Disposer newBuilder() {
            return null;
        }

        /**
         * 向后推进
         */
        private void forward() {
            if (finished || this.position < point) {
                return;
            }
            final int position, offset;
            if (mark == -1) {// 证明是初始值
                position = this.position;
                offset = 0;
            } else {
                position = mark;
                offset = this.position - mark;
            }
            try {
                final var skipped = reader.skip(position);
                reader.mark(MAX_BUFFER_LEN);
                var read = 0;
                while (read <= MIN_READ_AHEAD_LEN) {
                    var index = reader.read(buffer, read, buffer.length - read);
                    if (index == -1) {
                        this.finished = true;
                    }
                    if (index <= 0) {
                        break;
                    }
                    read += index;
                }
                reader.reset();
                if (read > 0) {
                    if (skipped != position) {
                        // Previously asserted that there is room in buf to skip, so this will be a WTF
                        throw new IllegalStateException();
                    }
                    this.length = read;
                    this.index += position;
                    this.position = offset;
                    if (this.mark != -1)
                        this.mark = 0;
                    this.point = Math.min(length, READ_AHEAD_LIMIT);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 获取内容读取的位置， 从 0 开始
         *
         * @return 位置
         */
        public int position() {
            return index + position;
        }

        /**
         * 获取当前行
         *
         * @return 行
         */
        public int line() {
            throw new UnsupportedOperationException("暂时不支持获取行");
        }

        /**
         * Get the char at the current position.
         *
         * @return char
         */
        public char current() {
            forward();
            return position >= length ? Character.MIN_VALUE : buffer[position];
        }

        /**
         * 消费掉当前字符， 拿到当前字符，并将位置向后移动
         *
         * @return char
         */
        public char consume() {
            forward();
            var val = position >= length ? Character.MIN_VALUE : buffer[position];
            position++;
            return val;
        }

        @Override
        public String next() {
            return null;
        }

        void unconsume() {
            if (position < 1)
                throw new UncheckedIOException(new IOException("WTF: No buffer left to unconsume.")); // a bug if this fires, need to trace it.
            position--;
        }

        public void advance() {
            position++;
        }

        void mark() {
            // make sure there is enough look ahead capacity
            if (length - position < MIN_READ_AHEAD_LEN)
                point = 0;
            forward();
            mark = position;
        }

        void unmark() {
            mark = -1;
        }

        int indexOf(char c) {
            forward();
            for (int i = position; i < length; i++) {
                if (c == buffer[i]) return i - position;
            }
            return -1;
        }

        int indexOf(CharSequence sequence) {
            forward();
            var start = sequence.charAt(0);
            for (int i = position, len = sequence.length(); i < length; i++) {
                while (i < length && start != buffer[i]) {
                    i++;
                }
                var last = i + len;
                if (last >= length) {
                    break;
                }
                for (int j = 0; i < last && sequence.charAt(j) == buffer[i]; j++) {
                    i++;
                }
                if (last == i) {
                    return i - position;
                }
            }
            return -1;
        }

        @Override
        public void request(long n) {
        }

        @Override
        public void cancel() {
        }

        public char consumeData() {
            return 0;
        }

        public String sub(int i) {
            return null;
        }
    }

}
