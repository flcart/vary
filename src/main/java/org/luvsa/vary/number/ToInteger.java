package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:58
 */
@Types({Integer.class, int.class})
public class ToInteger implements NProvider {
    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return Number::intValue;
    }
}
