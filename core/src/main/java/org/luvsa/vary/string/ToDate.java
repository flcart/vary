package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public Function<String, ?> get(Type type) {
        return this;
    }

    @Override
    public Date apply(String s) {


        return next(s, _DATE_TIME);
    }

    @Override
    Date next(String value, String format) {
        var formatter = new SimpleDateFormat(format);
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
