package org.luvsa.vary.chrono.zoned;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoZonedDateTime.class)
public class ZFactory extends FManager<ChronoZonedDateTime<?>> implements Factory {

    private final Cache<ChronoZonedDateTime<?>, ZProvider> map = new Cache<>(){};

    public ZFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<ChronoZonedDateTime<?>, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
