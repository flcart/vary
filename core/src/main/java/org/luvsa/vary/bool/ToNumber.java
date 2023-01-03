package org.luvsa.vary.bool;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * {@link Boolean bool} 转 {@link Number 数字}
 *
 * @author Aglet
 * @create 2022/6/28 9:32
 */
@Types({Byte.class, Short.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class})
public class ToNumber extends ToInteger implements Provider {

    @Override
    public Function<Boolean, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

    @Override
    public String toString() {
        return "Boolean-Number-Provider";
    }
}
