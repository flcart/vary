package org.luvsa.vary.date;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.function.Function;

/**
 * {@link java.util.Date} 转 {@link LocalDate}、  {@link LocalTime}、 {@link LocalDateTime}
 *
 * @author Aglet
 * @create 2022/6/27 17:08
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class ToLocal extends ToZoned implements Provider {

    @Override
    public Function<Date, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

}
