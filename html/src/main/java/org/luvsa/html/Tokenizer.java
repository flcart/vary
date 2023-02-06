package org.luvsa.html;

import org.luvsa.html.Token.Reader;

import java.util.Stack;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

/**
 * @author Aglet
 * @create 2023/1/11 13:47
 */
class Tokenizer implements Publisher<Token> {
    private final static char PREFIX = '<', SUFFIX = '>';

    private final Stack<Character> stack = new Stack<>();

    /**
     * 字符读取器
     */
    private final Token.Reader reader; // html input

    private int index = 0;
    private int prefix = -1;

    Tokenizer(Token.Reader reader) {
        this.reader = reader;
    }

    public static Tokenizer of(byte[] bytes) {
        var reader = new Reader(bytes);
        return new Tokenizer(reader);
    }

    @Override
    public void subscribe(Subscriber<? super Token> subscriber) {
    }

    Nodes next() {
        this.index = reader.index();
        while (true) {
            var read = reader.read();
            var option = switch (read) {
                case PREFIX -> open(read);
                case SUFFIX -> close(read);
                case '"', '\'' -> guess(read);
                default -> null;
            };
            if (option != null) {
                return option;
            }
        }
    }

    private Nodes guess(char read) {
        if (stack.isEmpty()) {
            stack.push(read);
        } else {
            var peek = stack.peek();
            if (peek == read) {
                stack.pop();
            } else {
                stack.push(read);
            }
        }
        return null;
    }

    private Nodes close(char read) {
        if (stack.isEmpty()) {
            return null;
        }
        var peek = stack.peek();
        if (peek == SUFFIX) {
            var sub = reader.sub(prefix);
            var node = Node.of(sub);
            if (node == null) {
                return null;
            }
            return new Pair(asText(), node);
        }
        return null;
    }

    private Nodes open(char read) {
        if (stack.isEmpty()) {
            stack.push(read);
            prefix = reader.index();
        } else if (stack.peek() == read) {
            prefix = reader.index();
        }
        return null;
    }

    private Text asText() {
        var sub = reader.sub(index, prefix);
        return sub == null ? null : new Text(sub);
    }

    interface Nodes {
        Text text();

        Node node();
    }

    record Pair(Text text, Node node) implements Nodes {
    }
}
