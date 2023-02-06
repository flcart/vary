package org.luvsa.html.labels;

import org.luvsa.html.Node;

/**
 * @author Aglet
 * @create 2023/2/1 9:00
 */
public class Script extends Node {
    @Override
    public String getName() {
        return null;
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
