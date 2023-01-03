package org.luvsa.vary.local;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;
import java.util.function.Function;

/**
 * {@link LocalDate}、{@link LocalTime}、{@link LocalDateTime} 转换成  {@link ChronoLocalDateTime}
 * 数据的函数供应商
 *
 * @author Aglet
 * @create 2022/6/27 15:48
 */
@Types(ChronoLocalDateTime.class)
public class ToChrono implements Provider {

    @Override
    public Function<Temporal, ?> get(Type type) {
        return ChronoLocalDateTime::from;
    }

}
