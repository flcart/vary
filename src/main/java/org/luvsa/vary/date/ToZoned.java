package org.luvsa.vary.date;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.function.Function;

/**
 * {@link java.util.Date} 转 {@link ZonedDateTime}
 *
 * @author Aglet
 * @create 2022/6/27 17:05
 */
@Types(ZonedDateTime.class)
public class ToZoned extends ToInstant implements DProvider {

    @Override
    public Function<Date, ?> get(Class<?> clazz) {
        return super.get(clazz).andThen(vary(clazz));
    }

}
