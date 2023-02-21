package org.luvsa.vary.string;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * {@link String} 转 {@link Date}， 案列: <pre>{@code
 * Date date = Vary.change("2022/6/28 9:03", Date.class);
 * }</pre>
 *
 * @author Aglet
 * @create 2022/6/28 9:03
 */
@Types(Date.class)
public class ToDate extends BiDate<Date> implements Provider, Function<String, Date> {

    private final Map<String, SimpleDateFormat> map = new HashMap<>();

    private final static String DEFAULT_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public ToDate() {
        map.put(DEFAULT_FORMAT, new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));
    }

    @Override
    public Function<String, ?> get(Type type) {
        return this;
    }

    @Override
    public Date apply(String s) {
        try {
            return next(s, _DATE_TIME);
        } catch (Exception e) {
            return next(s, DEFAULT_FORMAT);
        }
    }

    @Override
    Date next(String value, String format) {
        var formatter = map.computeIfAbsent(format, s -> {
            try {
                return new SimpleDateFormat(format);
            } catch (Exception e) {
                throw new IllegalArgumentException(format, e);
            }
        });
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("以[" + format + "]格式解析日期[" + value + "]失败！", e);
        }
    }

    @Override
    public String toString() {
        return String.class + " -> " + Date.class + " function provider";
    }
}
