package org.luvsa.vary.number;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:57
 */
@Types({Byte.class})
public class ToByte implements Provider {

    @Override
    public Function<Number, ?> get(Type type) {
        return Number::byteValue;
    }

    @Override
    public String toString() {
        return Byte.class.toString();
    }
}
