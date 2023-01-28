package org.luvsa.html.res;

import jdk.jfr.Name;
import org.luvsa.html.Label;

/**
 * @author Aglet
 * @create 2023/1/11 11:00
 */
@Name("format.label")
public class Format implements Resource {
    @Override
    public void accept(String s) {
        var label = Label.get(s);
        label.setFormatted(false);
    }
}
