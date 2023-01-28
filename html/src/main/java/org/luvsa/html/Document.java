package org.luvsa.html;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Aglet
 * @create 2023/1/11 13:45
 */
public class Document extends Node {
    private String[] array;
    private Node html;

    public Document(Collection<String> values, String... array) {
        var list = new ArrayList<String>();
        for (var value : values) {
            if (value == null || value.isBlank()) {
                continue;
            }
            list.add(value);
        }
        for (String s : array) {
            if (s == null || s.isBlank()) {
                continue;
            }
            list.add(s);
        }
        this.array = list.toArray(new String[0]);
    }

    @Override
    protected boolean hasAttributes() {
        return false;
    }

    @Override
    public boolean add(Node item) {
        super.add(item);
        this.html = item;
        return true;
    }

    @Override
    public String getName() {
        return "Doc";
    }

    @Override
    public String toString() {
        return "<!doctype " + String.join(" ", array) + ">\r\n" + html;
    }
}
