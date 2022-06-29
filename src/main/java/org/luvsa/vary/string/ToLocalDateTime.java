package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:18
 */
@Types(LocalDateTime.class)
public class ToLocalDateTime extends BiDate implements SProvider, Function<String, LocalDateTime> {

    @Override
    public LocalDateTime apply(String s) {
        if (s.isBlank()) {
            return null;
        }

        var dateTime = s.trim();
        var format = format(_DATE_TIME, dateTime);
        if (format.isBlank()) {
            throw new IllegalArgumentException("Unable to convert " + dateTime + " to LocalDateTime");
        }

        var pattern = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDateTime.parse(dateTime, pattern);
        } catch (DateTimeException e) {
            // issue "2021-08-10" 依据 “yyyy-MM-dd” 转成 LocalDateTime 异常， 这里暂时提供 转 LocalDate 然后获取这天的开始时间
            return LocalDate.parse(dateTime, pattern).atStartOfDay();
        }
    }

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return this;
    }
}
