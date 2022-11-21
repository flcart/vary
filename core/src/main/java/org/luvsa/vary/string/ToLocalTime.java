package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;
import org.luvsa.vary.Vary;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:14
 */
@Types(LocalTime.class)
public class ToLocalTime extends BiDate<LocalTime> implements Provider, Function<String, LocalTime> {

    @Override
    public Function<String, ?> get(Type type) {
        return this;
    }

    @Override
    public LocalTime apply(String s) {
        return next(s, ARRAY_TIME);
    }

    @Override
    LocalTime next(String value, String format) {
        try {
            var formatter = DateTimeFormatter.ofPattern(format);
            return LocalTime.parse(value, formatter);
        } catch (Exception e) {
            var change = Vary.change(value, LocalDateTime.class);
            return change.toLocalTime();
        }
    }

    @Override
    public String toString() {
        return String.class + " -> " + LocalTime.class + " function provider";
    }
}
