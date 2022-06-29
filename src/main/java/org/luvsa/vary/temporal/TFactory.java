package org.luvsa.vary.temporal;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:29
 */
@Types(ZonedDateTime.class)
public class TFactory extends FManager<ZonedDateTime> implements Factory {

    private final Cache<ZonedDateTime, TProvider> map = new Cache<>() {};

    public TFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<ZonedDateTime, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
