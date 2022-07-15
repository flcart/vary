package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:58
 */
@Types({Float.class, float.class})
public class ToFloat implements NProvider {
    @Override
    public Function<Number, ?> get(Type type) {
        return Number::floatValue;
    }
}
