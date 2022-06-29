package org.luvsa.vary.bool;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({Boolean.class, boolean.class})
public class BFactory extends FManager<Boolean> implements Factory {

    private final Cache<Boolean, BProvider> map = new Cache<>(){};

    public BFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<Boolean, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
