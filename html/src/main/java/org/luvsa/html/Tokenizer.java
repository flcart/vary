package org.luvsa.html;

import org.luvsa.reflect.Reflects;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

/**
 * @author Aglet
 * @create 2023/1/11 13:47
 */
class Tokenizer implements Publisher<Token> {

    private final static Map<Character, Handler> CACHE = new HashMap<>();

    static {
        var clazz = Handler.class;
        var loader = clazz.getClassLoader();
        var interfaces = new Class<?>[]{clazz};
        Reflects.doWithMethods(Tokenizer.class, method -> {
            var dispose = method.getAnnotation(Dispose.class);
            if (dispose == null) {
                return;
            }
            var value = dispose.value();
            var o = (Handler) Proxy.newProxyInstance(loader, interfaces, (proxy, meth, args) -> {
                var name = meth.getName();
                if (Objects.equals(name, "apply")) {
                    return method.invoke(args[0], args[1]);
                }
                if (Objects.equals(name, "toString")) {
                    return "Proxy-" + method.getName();
                }
                throw new UnsupportedOperationException();
            });
            for (var c : value) {
                CACHE.put(c, o);
            }
        });
    }

    private final StringBuilder builder = new StringBuilder();
    /**
     * 符号栈
     */
    private final Stack<Character> stack = new Stack<>();

    /**
     * 字符读取器
     */
    private final Token.Reader reader; // html input
    /**
     * 处理报错
     */
    private final Publisher<Throwable> publisher;

    /**
     * Node 属性暂存器
     */
    private final Token token = new Token();

    Tokenizer(Token.Reader reader, Publisher<Throwable> publisher) {
        this.reader = reader;
        this.publisher = publisher;
    }

    @Override
    public void subscribe(Subscriber<? super Token> subscriber) {
    }

    Node next() {
        while (true) {
            var current = reader.consume();
            if (current == Character.MIN_VALUE) {
                return null;
            }
            var handler = CACHE.get(current);
            if (handler == null) {
                builder.append(current);
            } else {
                var accept = handler.apply(this, current);
                var node = accept.get();
                if (node != null) {
                    return node;
                }
            }
        }
    }


    @Dispose('<')
    public Result handle0(char item) {
        if (stack.isEmpty()) {
            stack.push(item);
            if (builder.isEmpty()) {
                return Result.next;
            }
            final var s = builder.toString();
            builder.setLength(0);
            if (s.isBlank()) {
                return Result.next;
            }
            return () -> new Text(s);
        }
        var peek = stack.peek();
        if (peek == '\'' || peek == '"') {
            builder.append(item);
            return Result.next;
        }
        if (peek == item) {
            var s = item + builder.toString();
            builder.setLength(0);
            return () -> new Text(s);
        }
        throw new IllegalArgumentException();
    }

    @Dispose('>')
    public Result handle1(char item) {
        if (stack.isEmpty()) {
            builder.append(item);
            return Result.next;
        }
        var peek = stack.peek();
        if (peek == '<') {
            stack.pop();
            var s = builder.toString();
            builder.setLength(0);
            return () -> token.build(s);
        }
        if (peek == '\'' || peek == '"') {
            // "<>"
            builder.append(item);
            return Result.next;
        }
        throw new IllegalArgumentException();
    }

    @Dispose('!')
    public Result handle2(char item) {
        if (stack.isEmpty()) {
            builder.append(item);
        } else {
            var peek = stack.peek();
            if (peek == '<') {
                token.setDoctype(true);
            } else if (peek == '\'' || peek == '"') {
                builder.append(item);
            }
        }
        return Result.next;
    }

    @Dispose(' ')
    public Result handle3(char item) {
        if (stack.isEmpty()) {
            builder.append(item);
        } else {
            var peek = stack.peek();
            if (peek == '<') {
                var s = builder.toString();
                builder.setLength(0);
                token.accept(s);
            } else if (peek == '\'' || peek == '"') {
                builder.append(item);
            }
        }
        return Result.next;
    }

    @Dispose('=')
    public Result handle4(char item) {
        if (stack.isEmpty()) {
            builder.append(item);
        } else {
            var peek = stack.peek();
            if (peek == '<') {
                var s = builder.toString();
                builder.setLength(0);
                token.setCurrent(s);
            } else if (peek == '\'' || peek == '"') {
                builder.append(item);
            }
        }
        return Result.next;
    }

    @Dispose({'"', '\''})
    public Result handle5(char item) {
        if (stack.isEmpty()) {
            builder.append(item);
            return Result.next;
        }
        var peek = stack.peek();
        if (peek == '<') {
            stack.push(item);
            return Result.next;
        }
        if (peek == item) {
            stack.pop();
            var s = builder.toString();
            builder.setLength(0);
            token.set(s);
            return Result.next;
        }
        builder.append(item);
        return Result.next;
    }

    @Dispose('/')
    public Result handle6(char item) {
        if (stack.isEmpty()) {
            builder.append(item);
            return Result.next;
        }
        var peek = stack.peek();
        if (peek == '<') {
            token.setFinish(true);
        } else if (peek == '\'' || peek == '"') {
            builder.append(item);
        }
        return Result.next;
    }
}
