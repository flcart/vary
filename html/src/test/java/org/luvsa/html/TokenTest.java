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
        var builder = new Builder("index.html");
        var node = builder.create();
        System.out.println(node);
    }

}