package org.luvsa.vary.date;

import org.luvsa.vary.Provider;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:35
 */
public interface DProvider extends Provider<Date> {

    Function<Date, ?> get(Class<?> clazz);

}
