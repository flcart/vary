package org.luvsa.vary.string;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * {@link String} 转 {@link LocalDate}
 *
 * @author Aglet
 * @create 2022/6/28 9:19
 */
@Types(LocalDate.class)
public class ToLocalDate extends BiDate<LocalDate> implements Provider, Function<String, LocalDate> {

    @Override
    public LocalDate apply(String s) {
        return next(s, ARRAY_DATE);
    }

    @Override
    public Function<String, ?> get(Type type) {
        return this;
    }

    @Override
    LocalDate next(String value, String format) {
        if (value.length() > format.length()) {
            value = value.substring(0, format.length()).trim();
        }
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
    }

    @Override
    public String toString() {
        return String.class + " -> " + LocalDate.class + " function provider";
    }
}
