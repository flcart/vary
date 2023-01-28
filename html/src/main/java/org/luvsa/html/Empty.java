package org.luvsa.html;

import jdk.jfr.Name;

/**
 * @author Aglet
 * @create 2023/1/11 11:00
 */
@Name("empty.label")
public class Empty implements Resource {
    @Override
    public void accept(String s) {
        var label = Label.get(s);
        label.setEmpty(true);
    }
}
