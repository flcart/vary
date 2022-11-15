package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:57
 */
@Types({Short.class})
public class ToShort implements Provider {

    @Override
    public Function<Number, ?> get(Type type) {
        return Number::shortValue;
    }

    @Override
    public String toString() {
        return Short.class.toString();
    }
}
