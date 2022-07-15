package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 16:46
 */
@Types(Date.class)
public class ToDate extends ToInstant implements NProvider {
    @Override
    public Function<Number, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }
}
