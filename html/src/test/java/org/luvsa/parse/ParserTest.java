package org.luvsa.parse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aglet
 * @create 2023/2/18 15:50
 */
class ParserTest {

    @Test
    void just() {
        var html = "<html><head><title>First!</title></head><body><p>First post! <img src=\"foo.png\" /></p></body></html>";
        var doc = Parser.just(html);
        var p = doc.body().child(0);

    }
}