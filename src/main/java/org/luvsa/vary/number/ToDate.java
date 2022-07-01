package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 16:46
 */
@Types(Date.class)
public class ToDate extends ToInstant implements NProvider {

    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return super.get(clazz).andThen(vary(clazz));
    }

}
