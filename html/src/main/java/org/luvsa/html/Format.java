package org.luvsa.html;

import jdk.jfr.Name;

/**
 * @author Aglet
 * @create 2023/1/11 11:00
 */
@Name("format.label")
public class Format implements Resource {
    @Override
    public void accept(String s) {
        var label = Label.get(s);
        if (label != null) {
            label.setFormatted(false);
        }
    }
}
