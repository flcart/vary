package org.luvsa.html;

import jdk.jfr.Name;

/**
 * @author Aglet
 * @create 2023/1/11 11:01
 */
@Name("scope.label")
public class Scope implements Resource {
    @Override
    public void accept(String s) {
        var label = Label.get(s);
        if (label != null) {
            label.setAutism(true);
        }
    }
}
