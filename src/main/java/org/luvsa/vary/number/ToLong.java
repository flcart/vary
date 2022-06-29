package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:58
 */
@Types({Long.class, long.class})
public class ToLong implements NProvider {
    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return Number::longValue;
    }
}
