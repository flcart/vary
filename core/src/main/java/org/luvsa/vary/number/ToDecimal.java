package org.luvsa.vary.number;

import org.luvsa.annotation.Types;
import org.luvsa.vary.Vary;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/28 11:45
 */
@Types(BigDecimal.class)
public class ToDecimal extends ToString implements Provider{

    @Override
    public Function<Number, ?> get(Type type) {
        return super.get(type).andThen(o -> Vary.convert(o, type));
    }

    @Override
    public String toString() {
        return BigDecimal.class.toString();
    }
}
