package org.luvsa.html.res;

import jdk.jfr.Name;
import org.luvsa.html.Label;

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
