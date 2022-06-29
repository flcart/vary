package org.luvsa.vary.local;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class LFactory extends FManager<TemporalAccessor> implements Factory {

    private final Cache<TemporalAccessor, LProvider> map = new Cache<>(){};

    public LFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<TemporalAccessor, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
