package org.luvsa.html.labels;

import org.luvsa.html.Node;

/**
 * @author Aglet
 * @create 2023/2/1 8:58
 */
public class Doctype extends Node {
    @Override
    public String getName() {
        return "doctype";
    }

    @Override
    protected boolean hasAttributes() {
        return false;
    }

    @Override
    public String baseUri() {
        return null;
    }

    @Override
    public void visit(Visitor visitor, int depth) {
    }
}
