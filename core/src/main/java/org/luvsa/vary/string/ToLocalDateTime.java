package org.luvsa.vary.string;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:18
 */
@Types(LocalDateTime.class)
public class ToLocalDateTime extends BiDate<LocalDateTime> implements Provider, Function<String, LocalDateTime> {

    private final static List<DateTimeFormatter> list = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.BASIC_ISO_DATE,
            DateTimeFormatter.RFC_1123_DATE_TIME);


    @Override
    public LocalDateTime apply(String s) {
        // TODO　存在问题
        try {
            return next(s, _DATE_TIME);
        } catch (Exception e) {
            for (var formatter : list) {
                try {
                    return LocalDateTime.parse(s, formatter);
                } catch (Exception exception) {
                    //
                }
            }
            throw new UnsupportedOperationException(s + " to LocalDateTime");
        }
    }

    @Override
    public Function<String, ?> get(Type type) {
        return this;
    }

    @Override
    LocalDateTime next(String value, String format) {
        var pattern = formatters.computeIfAbsent(format, DateTimeFormatter::ofPattern);
        try {
            return LocalDateTime.parse(value, pattern);
        } catch (DateTimeException e) {
            // issue "2021-08-10" 依据 “yyyy-MM-dd” 转成 LocalDateTime 异常， 这里暂时提供 转 LocalDate 然后获取这天的开始时间
            return LocalDate.parse(value, pattern).atStartOfDay();
        }
    }

    @Override
    public String toString() {
        return String.class + " -> " + LocalDateTime.class + " function provider";
    }
}
