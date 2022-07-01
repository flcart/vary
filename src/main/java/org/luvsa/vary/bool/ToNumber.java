package org.luvsa.vary.bool;

import org.luvsa.vary.TypeSupplier.Types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * {@link Boolean bool} 转 {@link Number 数字}
 *
 * @author Aglet
 * @create 2022/6/28 9:32
 */
@Types({Byte.class, byte.class, Short.class, short.class, Long.class, long.class, Float.class, float.class, Double.class, double.class, BigInteger.class, BigDecimal.class})
public class ToNumber extends ToInteger implements BProvider {

    @Override
    public Function<Boolean, ?> get(Class<?> clazz) {
        return super.get(clazz).andThen(vary(clazz));
    }

}
