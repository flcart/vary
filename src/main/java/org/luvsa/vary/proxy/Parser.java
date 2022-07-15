package org.luvsa.vary.proxy;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/15 14:34
 */
public interface Parser {

    Function<Object, ?> create(String code) throws Exception;

    default void register(Object o) {

    }

    default void register(String name, Object o) {

    }
}
