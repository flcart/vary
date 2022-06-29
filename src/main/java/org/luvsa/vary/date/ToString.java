package org.luvsa.vary.date;

import org.luvsa.vary.TypeSupplier.Types;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 17:00
 */
@Types(String.class)
public class ToString implements DProvider {

    private final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Function<Date, ?> get(Class<?> clazz) {
        return DEFAULT_FORMAT::format;
    }
}
