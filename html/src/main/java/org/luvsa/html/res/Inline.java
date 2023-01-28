package org.luvsa.html.res;

import jdk.jfr.Name;
import org.luvsa.html.Label;

/**
 * @author Aglet
 * @create 2023/1/11 11:01
 */
@Name("inline.label")
public class Inline implements Resource {
    @Override
    public void accept(String s) {
        var tag = Label.get(s);
        tag.setBlock(false);
        tag.setFormatted(false);
    }
}
