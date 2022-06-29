package org.luvsa.vary.date;

import org.luvsa.vary.Cache;
import org.luvsa.vary.Factory;
import org.luvsa.vary.Factory.FManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(Date.class)
public class DFactory extends FManager<Date> implements Factory {

    private final Cache<Date, DProvider> map = new Cache<>(){};

    public DFactory() {
        map.register(this::load);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

    @Override
    protected Function<Date, ?> offer(Class<?> clazz) {
        return map.get(clazz);
    }
}
