package org.luvsa.vary.number;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:58
 */
@Types({Long.class})
public class ToLong implements Provider {
    @Override
    public Function<Number, ?> get(Type type) {
        return Number::longValue;
    }

    @Override
    public String toString() {
        return Long.class.toString();
    }
}
