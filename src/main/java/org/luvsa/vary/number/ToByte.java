package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:57
 */
@Types({Byte.class, byte.class})
public class ToByte implements NProvider {

    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return Number::byteValue;
    }

}
