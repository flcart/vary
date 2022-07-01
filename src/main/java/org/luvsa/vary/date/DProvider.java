package org.luvsa.vary.date;

import org.luvsa.vary.Provider;

import java.util.Date;
import java.util.function.Function;

/**
 * {@link java.util.Date} 数据转换器函数提供者
 *
 * @author Aglet
 * @create 2022/6/27 14:35
 */
public interface DProvider extends Provider<Date> {

    Function<Date, ?> get(Class<?> clazz);

}
