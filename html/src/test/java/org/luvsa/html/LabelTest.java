package org.luvsa.html;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

/**
 * @author Aglet
 * @create 2023/1/11 10:10
 */
class LabelTest {

    private static final String[] inlineTags = {
            "pre", "plaintext", "title", "textarea"
    };

    @Test
    void test() {
        var joiner = new StringJoiner(";");
        for (var item : inlineTags) {
            joiner.add(item);
        }
        System.out.println(joiner);
    }

    @Test
    void load(){
        var label = new Label();
        var name = label.getName();
        System.out.println(label);
    }

}