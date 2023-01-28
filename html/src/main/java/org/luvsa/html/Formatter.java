package org.luvsa.html;

import java.util.List;

/**
 * @author Aglet
 * @create 2023/1/28 9:39
 */
public class Formatter {

    private boolean zip;

    public String build(String text, Node node) {
        if (zip) {
            return text + node;
        }
        return text + "\r\n" + node;
    }

    public String build(String s, List<Node> children, String suffix) {
        if (zip) {
            var builder = new StringBuilder();
            for (var child : children) {
                builder.append(child);
            }
            return s + builder + suffix;
        }
        return null;
    }
}
