package org.luvsa.parse;

import org.luvsa.node.Document;

import java.io.StringReader;

/**
 * @author Aglet
 * @create 2023/2/18 15:26
 */
public class Parser {

    public static Document just(String html) {
        return just(html, "");
    }

    public static Document just(String html, String uri) {
        var reader = new StringReader(html);

        return null;
    }
}
