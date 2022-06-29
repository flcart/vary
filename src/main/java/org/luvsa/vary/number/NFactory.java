package org.luvsa.vary.number;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
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
public class NFactory extends FManager<Number> implements Factory {

    private final Cache<Number, NProvider> map = new Cache<>(){};

    public NFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<Number, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
