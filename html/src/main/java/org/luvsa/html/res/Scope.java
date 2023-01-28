package org.luvsa.html.res;

import jdk.jfr.Name;
import org.luvsa.html.Label;

/**
 * @author Aglet
 * @create 2023/1/11 11:01
 */
@Name("scope.label")
public class Scope implements Resource {
    @Override
    public void accept(String s) {
        var label = Label.get(s);
        label.setAutism(true);
    }
}
