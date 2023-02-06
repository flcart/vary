package org.luvsa.html;

import org.luvsa.io.Resources;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * @author Aglet
 * @create 2023/1/28 9:25
 */
public abstract class Parser<T> implements Consumer<T> {

    Handler next;

    public Parser(Consumer<Node> next) {
        this.next = new Handler(next);
    }

    public static class Resource extends Parser<String> {
        public Resource(Consumer<Node> next) {
            super(next);
        }

        @Override
        public void accept(String s) {
            try (var stream = Resources.asStream(s)) {
                this.next.onNext(stream);
            } catch (Exception e) {
                throw new RuntimeException("获取资源文件流【" + s + "】失败！");
            }
            this.next.onComplete();
        }
    }

    public static class Remote extends Parser<URI> {
        private final HttpClient client = HttpClient.newHttpClient();
        private HttpRequest.Builder builder;

        public Remote(Consumer<Node> next) {
            super(next);
        }

        @Override
        public void accept(URI uri) {
            var builder = this.builder == null ? HttpRequest.newBuilder(uri) : this.builder.uri(uri);
            client.sendAsync(builder.build(), BodyHandlers.ofInputStream())
                    .handle((response, throwable) -> {
                        if (throwable == null) {
                            return response.body();
                        }
                        throw new IllegalStateException("网络访问 【" + uri + "】 异常!", throwable);
                    }).thenAccept(bytes -> {
                        this.next.onNext(bytes);
                        this.next.onComplete();
                    })
                    .exceptionally(throwable -> {
                        this.next.onError(throwable);
                        return null;
                    });
        }

        public void setRequest(Builder builder) {
            this.builder = builder;
        }
    }

    private static class Handler {
        /**
         * 标签语法栈
         */
        private final Stack<Node> stack = new Stack<>();

        private final Consumer<Node> next;

        public Handler(Consumer<Node> next) {
            this.next = next;
        }

        public void onNext(InputStream item) {
            try (var stream = new ByteArrayOutputStream()) {
                for (int i = item.read(); i != -1; i = item.read()) {
                    stream.write(i);
                }
                var bytes = stream.toByteArray();
                var tokenizer = Tokenizer.of(bytes);
                for (var node = tokenizer.next(); node != null; node = tokenizer.next()) {

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private void onNext(Node item) {
            var info = item.getInfo();
            if (info.isOpen()) {
                if (info.isFinished()) {
                    stack.peek().add(item);
                } else {
                    stack.push(item);
                }
                return;
            }
            // 结束标签默认 finished = true
            while (!stack.isEmpty()) {
                // 1. 去除栈定元素
                var pop = stack.pop();
                if (pop.match(item)) {
                    // 栈顶元素匹配到当前结束标签
                    if (stack.isEmpty()) {
                        next.accept(pop);
                    } else {
                        // 出栈
                        stack.peek().add(pop);
                    }
                } else {
                    if (stack.isEmpty()) {
                        throw new IllegalStateException("无法匹配结束标签【" + item + "】");
                    }
                    stack.peek().add(pop);
                }
            }
        }

        public void onError(Throwable throwable) {
            if (throwable instanceof RuntimeException e) {
                throw e;
            }
            throw new RuntimeException(throwable);
        }

        public void onComplete() {
            // 接收到结束的信号
            next.accept(stack.pop());
        }
    }
}
