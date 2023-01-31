package org.luvsa.html;

import org.luvsa.exception.ValueException;
import org.luvsa.io.Resources;
import org.luvsa.lang.Charsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.Stack;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;

/**
 * @author Aglet
 * @create 2023/1/28 9:25
 */
public abstract class Parser<T> implements Consumer<T> {

    protected Subscriber<? super InputStream> next;

    public Parser(Consumer<Node> next) {
        this(new Handler(next));
    }

    public Parser(Subscriber<? super InputStream> next) {
        this.next = next;
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
                this.next.onError(e);
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
                    }).thenAccept(stream -> {
                        this.next.onNext(stream);
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

    private static class Handler implements Flow.Subscriber<InputStream> {
        private final Stack<Node> stack = new Stack<>();
        private final Consumer<Node> next;

        public Handler(Consumer<Node> next) {
            this.next = next;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void onNext(InputStream item) {
            try {
                onNext(item, toCharset(item));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void onNext(InputStream item, Charset charset) {
            try (var stream = new InputStreamReader(item, charset);
                 var buffered = new BufferedReader(stream)) {
                var reader = new Token.Reader(buffered);
                var tokenizer = new Tokenizer(reader, new SubmissionPublisher<>());
                while (true) {
                    var node = tokenizer.next();
                    if (node == null) {
                        break;
                    }
                    onNext(node);
                }
                this.onComplete();
            } catch (Exception e) {
               // onError(e);
            }
        }

        private Charset toCharset(InputStream stream) throws IOException {
            var array = new byte[8];
            stream.mark(0);
            var read = stream.read(array);
            if (read == -1) {
                return Charset.forName("GBK");
            }
            stream.reset();
            return Charsets.guess(array);
        }

        private void onNext(Node item) {
            if (stack.isEmpty()) {
                stack.push(item);
            } else if (item.isFinished()) {
                while (true) {
                    var pop = stack.pop();
                    if (stack.isEmpty()) {
                        if (pop.match(item)) {
                            throw new ValueException(pop);
                        }
                        throw new IllegalStateException("Node prefix[" + pop + "] not match suffix[" + item + "]");
                    }
                    var peek = stack.peek();
                    if (pop.match(item)) { //  匹配到结束标签
                        if (peek.add(pop)) {
                            break;
                        }
                        throw new IllegalStateException("");
                    }
                    // 未匹配到结束标签
                    if (peek.add(pop)) {
                        continue;
                    }
                    throw new IllegalStateException();
                }
            } else if (item instanceof Text || item.isEmpty()) {
                stack.peek().add(item);
            } else {
                stack.push(item);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (throwable instanceof RuntimeException e) {
                throw e;
            }
            throw new RuntimeException(throwable);
        }

        @Override
        public void onComplete() {
            // 接收到结束的信号
            next.accept(stack.pop());
        }
    }
}
