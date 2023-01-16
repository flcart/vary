package org.luvsa.html;

import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * @author Aglet
 * @create 2023/1/12 9:51
 */
class Disposer implements Subscriber<Character> {
    static final long START = 1;
    static final long NEXT = 2;
    static final long LABEL = 3;
    /**
     * 单词
     */
    static final long WORD = 4;

    static final long DEFINE = 5;

    static final long DOC = 6;

    static final long TEXT = 7;
    static final long END = -1;
    static final long STR = -2;

    private Subscription subscription;
    private final StringBuilder builder = new StringBuilder(1024);
    private final Stack<Character> stack = new Stack<>();

    public Disposer(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(Character item) {
        if (item == null || Objects.equals(item, Character.MIN_VALUE)) {
            subscription.cancel();
            return;
        }

        char chr = item;
        switch (chr) {
            case '<' -> {
                if (stack.isEmpty()) {
                    if (!builder.isEmpty()) {
                        subscription.request(TEXT);
                    }
                    stack.push('>');
                    subscription.request(START);
                } else {
                    var peek = stack.peek();
                    if (peek == '\'' || peek == '"') {
                        builder.append(item);
                        subscription.request(NEXT);
                    } else {
                        stack.push('>');
                        subscription.request(START);
                    }
                }
            }

            case '>' -> {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException();
                }
                var peek = stack.peek();
                if (Objects.equals(peek, item)) {
                    stack.pop();
                    subscription.request(LABEL);
                } else if (peek == '\'' || peek == '"') {
                    // "<>"
                    builder.append(item);
                    subscription.request(NEXT);
                }
            }

            case '/' -> {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException();
                }
                var peek = stack.peek();
                if (Objects.equals(peek, '>')) {
                    subscription.request(END);
                } else {
                    builder.append(item);
                    subscription.request(NEXT);
                }
            }

            case '\'', '"' -> {
                if (stack.isEmpty()) {
                    stack.push(item);
                } else {
                    var peek = stack.peek();
                    if (Objects.equals(peek, item)) {
                        stack.pop();
                        subscription.request(STR);
                    } else {
                        stack.push(item);
                        subscription.request(NEXT);
                    }
                }
            }

            case '!' -> {
                if (stack.isEmpty()) {
                    subscription.request(NEXT);
                } else {
                    var peek = stack.peek();
                    if (Objects.equals(peek, '>')) {
                        subscription.request(DOC);
                    } else {
                        builder.append(item);
                        subscription.request(NEXT);
                    }
                }
            }

            case ' ' -> {
                if (builder.isEmpty()) {
                    subscription.request(NEXT);
                } else {
                    subscription.request(DEFINE);
                }
            }

            case '=' -> {
                subscription.request(WORD);
            }

            default -> {
                builder.append(chr);
                subscription.request(NEXT);
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public String clean() {
        var s = builder.toString();
        builder.setLength(0);
        return s;
    }
}
