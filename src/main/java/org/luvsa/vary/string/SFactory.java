package org.luvsa.vary.string;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * 字符串转换成指定数据类型的工厂
 *
 * @author Aglet
 * @create 2022/6/25 10:47
 */
@Types(String.class)
public class SFactory extends FManager<String> implements Factory {

    private final Cache<String, SProvider> map = new Cache<>(){};

    public SFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<String, ?> offer(Class<?> clazz) {
        if (CharSequence.class.isAssignableFrom(clazz)) {
            return String::toString;
        }
        return map.get(clazz);
    }
}
