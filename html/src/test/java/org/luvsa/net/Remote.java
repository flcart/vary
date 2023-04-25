package org.luvsa.net;

import org.junit.jupiter.api.Test;
import org.luvsa.io.Files;
import org.luvsa.node.Node;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2023/3/17 16:06
 */
public class Remote {
    private final Map<String, Function<URI, String>> functions = Map.of(
            ":authority", s -> "www.xxbiqudu.com",
            ":method", s -> "GET",
            ":scheme", s -> "https",
            ":path", URI::getPath
    );

    @Test
    void send() throws Exception {
        var client = HttpClient.newHttpClient();
        //
        var uri = URI.create("https://www.xxbiqudu.com/49_49754/138115698.html");
        var builder = HttpRequest.newBuilder(uri);
        Files.readResource("headers", (s, throwable) -> {
            if (throwable == null) {
                var index = s.indexOf(":");
                if (index < 0) {
                    return;
                }
                var key = s.substring(0, index);
                var value = s.substring(index + 1);
                builder.header(key.trim(), value.trim());
            }
        });
        var request = builder.build();
        var gbk = Charset.forName("gbk");

        var handler = BodyHandlers.ofString();
        client.sendAsync(request, info -> BodySubscribers.ofString(gbk))
                .handle((response, throwable) -> {
                    if (throwable == null) {
                        return response.body();
                    }
                    throw new RuntimeException(throwable);
                }).thenAccept(txt -> {
                    System.out.println(txt);
                    var from = Node.from(txt);
                }).exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
        TimeUnit.SECONDS.sleep(30);
    }


}
