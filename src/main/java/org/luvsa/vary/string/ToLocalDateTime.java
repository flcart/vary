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
public class ToLocalDateTime extends BiDate<LocalDateTime> implements SProvider, Function<String, LocalDateTime> {

    @Override
    public LocalDateTime apply(String s) {
        return next(s, _DATE_TIME);
    }

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return this;
    }

    @Override
    LocalDateTime next(String value, String format) {
        var pattern = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDateTime.parse(value, pattern);
        } catch (DateTimeException e) {
            // issue "2021-08-10" 依据 “yyyy-MM-dd” 转成 LocalDateTime 异常， 这里暂时提供 转 LocalDate 然后获取这天的开始时间
            return LocalDate.parse(value, pattern).atStartOfDay();
        }
    }
}
