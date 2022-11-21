package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:58
 */
@Types({Double.class})
public class ToDouble implements Provider {

    @Override
    public Function<Number, ?> get(Type type) {
        return Number::doubleValue;
    }

    @Override
    public String toString() {
        return Double.class.toString();
    }
}
