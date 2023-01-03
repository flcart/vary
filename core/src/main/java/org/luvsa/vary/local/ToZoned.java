package org.luvsa.vary.local;

import org.luvsa.annotation.Types;
import org.luvsa.vary.Vary;

import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.Temporal;
import java.util.function.Function;

/**
 * {@link  Temporal}, TemporalAdjuster, ChronoLocalDate, Serializable
 * {@link  Temporal}, TemporalAdjuster, Comparable<LocalTime>, Serializable
 * {@link  Temporal}, TemporalAdjuster, ChronoLocalDateTime<LocalDate>, Serializable
 *
 * @author Aglet
 * @create 2022/7/25 15:06
 */

@Types(ChronoZonedDateTime.class)
public class ToZoned implements Provider {

    @Override
    public Function<Temporal, ?> get(Type type) {
        return accessor -> {
            var time = Vary.change(accessor, ChronoLocalDateTime.class);
            // 转换成 ChronoLocalDateTime 继续下一步
            return time.atZone(ZoneId.systemDefault());
        };
    }
}
