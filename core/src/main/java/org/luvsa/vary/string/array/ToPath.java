package org.luvsa.vary.string.array;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 11:17
 */
public class ToPath implements Provider {

    @Override
    public Function<String[], ?> get(Type type) {
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return "String[]-Path";
    }
}
