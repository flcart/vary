package org.luvsa.html;

import java.util.function.Supplier;

/**
 * @author Aglet
 * @create 2023/1/13 14:20
 */
public interface Result extends Supplier<Node> {

    Result next = () -> null;

}
