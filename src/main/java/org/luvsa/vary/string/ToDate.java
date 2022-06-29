package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:03
 */
@Types(Date.class)
public class ToDate extends BiDate implements SProvider, Function<String, Date> {

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return this;
    }

    @Override
    public Date apply(String s) {
        if (s.isBlank()) {
            throw new IllegalArgumentException("空字符串！");
        }
        var dateTime = s.trim();
        // 格式
        var schema = format(_DATE_TIME, dateTime);
        if (schema.isBlank()) {
            throw new IllegalArgumentException("无法解析目标日期： " + dateTime);
        }

        var format = new SimpleDateFormat(schema);
        try {
            return format.parse(dateTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("以[" + schema + "]格式解析日期[" + dateTime + "]失败！", e);
        }
    }
}
