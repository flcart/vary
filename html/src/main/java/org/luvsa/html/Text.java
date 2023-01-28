package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/1/13 14:35
 */
public class Text extends Node {

    private final String value;

    public Text(String s) {
        this.value = s;
    }

    @Override
    protected boolean hasAttributes() {
        return false;
    }

    @Override
    public String getName() {
        return "TEXT";
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean add(Node item) {
        return false;
    }
}
