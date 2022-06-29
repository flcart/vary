package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:19
 */
@Types(LocalDate.class)
public class ToLocalDate extends BiDate implements SProvider, Function<String, LocalDate> {

    @Override
    public LocalDate apply(String s) {
        if (s.isBlank()) {
            return null;
        }

        var value = s.trim();
        var format = format(ARRAY_DATE, value).trim();

        if (format.isBlank()) {
            //不支持
            throw new IllegalArgumentException("Unable to convert " + value + " to LocalDate");
        }

        if (value.length() > format.length()) {
            value = value.substring(0, format.length()).trim();
        }

        return LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
    }

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return this;
    }
}
