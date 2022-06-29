package org.luvsa.vary.chrono;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoLocalDateTime.class)
public class CFactory extends FManager<ChronoLocalDateTime<?>> implements Factory {

    private final Cache<ChronoLocalDateTime<?>, CProvider> map = new Cache<>(){};

    public CFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<ChronoLocalDateTime<?>, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
