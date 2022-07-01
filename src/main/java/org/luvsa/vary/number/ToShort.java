package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:57
 */
@Types({Short.class, short.class})
public class ToShort implements NProvider {

    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return Number::shortValue;
    }
}
