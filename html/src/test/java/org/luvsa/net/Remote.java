package org.luvsa.net;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.TimeUnit;

/**
 * @author Aglet
 * @create 2023/3/17 16:06
 */
public class Remote {


    @Test
    void send() throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        //
        var uri = URI.create("https://www.beqege.cc/16607/22142197.html");
        var request = HttpRequest.newBuilder(uri).build();


        client.sendAsync(request, BodyHandlers.ofString())
                .handle((response, throwable) -> {
                    if (throwable == null) {
                        return response.body();
                    }
                    throw new RuntimeException(throwable);
                }).thenAccept(s -> {
                    System.out.println(s);
                });

        TimeUnit.SECONDS.sleep(30);

    }


}
