package org.luvsa.html;

import org.junit.jupiter.api.Test;

/**
 * @author Aglet
 * @create 2023/1/11 16:23
 */
class TokenTest {

    @Test
    void reset() {
    }

    @Test
    void indexOf() {
        var parser = new FileParser("index.html");
        var node = parser.resolve();
        if (node instanceof Document document) {
            var content = document.getElementById("content");
            var list = document.getElementsByClass("footer_link");
            var a = document.getElementsByTag("a");
            System.out.println(content);
        } else {
            System.out.println(node);
        }
    }

}