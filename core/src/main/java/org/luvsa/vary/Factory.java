package org.luvsa.vary;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * 函数转换工厂，用于创建数据转换函数
 *
 * @author Aglet
 * @create 2022/6/25 10:31
 */
public interface Factory<T> extends TypeSupplier {
    /**
     * 创建转换函数
     *
     * @param type 目标数据类型
     * @return 转换函数
     */
    Function<T, ?> create(Type type);
}
