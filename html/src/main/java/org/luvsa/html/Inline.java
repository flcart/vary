package org.luvsa.html;

import jdk.jfr.Name;

/**
 * @author Aglet
 * @create 2023/1/11 11:01
 */
@Name("inline.label")
public class Inline implements Resource {
    @Override
    public void accept(String s) {
        var tag = new Label(s);
        tag.setBlock(false);
        tag.setFormatted(false);
        Label.register(tag);
    }
}
