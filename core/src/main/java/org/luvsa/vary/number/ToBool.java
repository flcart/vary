package org.luvsa.vary.number;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 *
 *
 * @author Aglet
 * @create 2022/6/27 13:55
 */
@Types({Boolean.class})
public class ToBool implements Provider {

    @Override
    public Function<Number, ?> get(Type type) {
        return number -> number.doubleValue() > 0;
    }

    @Override
    public String toString() {
        return Boolean.class.toString();
    }
}
