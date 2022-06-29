package org.luvsa.vary.instant;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(Instant.class)
public class IFactory extends FManager<Instant> implements Factory {
    private final Cache<Instant, IProvider> map = new Cache<>(){};

    public IFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<Instant, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
