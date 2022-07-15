package org.luvsa.vary.local;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:43
 */
@Types(String.class)
public class ToString implements LProvider, Function<TemporalAccessor, String> {

    /**
     * 默认日期格式
     */
    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认时间格式
     */
    private final static String DEFAULT_TIME_FORMAT = "HH:mm:ss:SSS";

    @Override
    public Function<TemporalAccessor, ?> get(Type type) {
        return this;
    }

    @Override
    public String apply(TemporalAccessor accessor) {
        if (accessor instanceof LocalTime) {
            return DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT).format(accessor);
        }

        if (accessor instanceof LocalDate) {
            return DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT).format(accessor);
        }

        if (accessor instanceof LocalDateTime) {
            return DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT).format(accessor);
        }
        throw new UnsupportedOperationException("日期数据： " + accessor);
    }
}
