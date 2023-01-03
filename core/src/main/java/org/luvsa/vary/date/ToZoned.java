package org.luvsa.vary.date;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
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
public class ToZoned extends ToInstant implements Provider {

    @Override
    public Function<Date, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

}
