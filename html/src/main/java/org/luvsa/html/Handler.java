package org.luvsa.html;

import java.util.concurrent.Flow.Publisher;

/**
 * @author Aglet
 * @create 2023/1/12 9:00
 */
public interface Handler {
    Result apply(Publisher<Token> publisher, char current);

}
