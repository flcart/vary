package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 *
 *
 * @author Aglet
 * @create 2022/6/27 13:55
 */
@Types({Boolean.class, boolean.class})
public class ToBool implements NProvider {

    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return number -> number.doubleValue() > 0;
    }

}
