package org.luvsa.html;

import org.junit.jupiter.api.Test;
import org.luvsa.html.Parser.Resource;

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
       var  parser = new Resource(this::next);
       parser.accept("index.html");
    }

    private void next(Node node) {
        if (node instanceof Document document) {
            var content = document.getElementById("content");
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
//        parser = new Remote("http://www.linlida.net/35_35949/39217178.html");
//        parser.subscribe(this::next);
//        parser.resolve();
//        TimeUnit.SECONDS.sleep(30);
    }

}