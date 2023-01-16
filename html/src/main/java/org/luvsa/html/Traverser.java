package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/1/12 9:21
 */
public interface Traverser {

    char current();

    char consume();

    String next();
}
