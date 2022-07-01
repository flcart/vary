package org.luvsa.vary.number;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * 数字转 其他数据
 *
 * @author Aglet
 * @create 2022/6/27 9:30
 */
@Types({Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class,
        Double.class, double.class, BigDecimal.class, BigInteger.class})
public class NFactory extends FunctionManager<Number, NProvider> implements Factory<Number> {

    @Override
    public Function<Number, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }

}
