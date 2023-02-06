package org.luvsa.html;

import org.junit.jupiter.api.Test;
import org.luvsa.html.Parser.Remote;
import org.luvsa.html.Parser.Resource;

import java.net.URI;

/**
 * @author Aglet
 * @create 2023/1/11 16:23
 */
class TokenTest {


    @Test
    void reset() {
    }

    @Test
    void file() {
        var parser = new Resource(this::next);
        parser.accept("index.html");
    }

    private void next(Node node) {
        if (node instanceof Document document) {
            var content = document.getElementById("content");
            if (content == null) {
                return;
            }
//            var list = document.getElementsByClass("footer_link");
//            var a = document.getElementsByTag("a");
            var texts = content.texts();
            for (String text : texts) {
                System.out.println(text);
            }
        } else {
            System.out.println(node);
        }
    }

    @Test
    void net() throws InterruptedException {
        var parser = new Remote(this::next);
        parser.accept(URI.create("http://www.linlida.net/35_35949/39217178.html"));

//        parser = new Remote("http://www.linlida.net/35_35949/39217178.html");
//        parser.subscribe(this::next);
//        parser.resolve();
//        TimeUnit.SECONDS.sleep(30);
    }

}