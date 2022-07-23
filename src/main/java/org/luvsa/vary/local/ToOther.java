package org.luvsa.vary.local;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.function.Function;

/**
 *  {@link LocalDate}、{@link LocalTime}、{@link LocalDateTime} 转换成  {@link Instant}、{@link Date}、{@link Long}
 *  数据的函数供应商
 *
 * @author Aglet
 * @create 2022/6/29 17:20
 */
@Types({Instant.class, Date.class, Long.class})
public class ToOther extends ToChrono implements LProvider {

    @Override
    public Function<TemporalAccessor, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

}
