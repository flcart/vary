package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:14
 */
@Types(LocalTime.class)
public class ToLocalTime extends BiDate implements SProvider, Function<String, LocalTime> {

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return this;
    }

    @Override
    public LocalTime apply(String s) {
        if (s.isBlank()) {
            return null;
        }
        var value = s.trim();
        var format = format(ARRAY_TIME, value);
        if (format.isBlank()) {
            throw new IllegalArgumentException("Unable to convert " + value + " to LocalDateTime");
        }
        var formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(value, formatter);
    }

}
