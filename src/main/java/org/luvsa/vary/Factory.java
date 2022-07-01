package org.luvsa.vary;

import java.util.function.Function;

/**
 * 数据转换函数工厂接口
 *
 * @author Aglet
 * @create 2022/6/29 16:59
 */
public interface Factory extends TypeSupplier {

    /**
     * 创建转换函数
     *
     * @param clazz 目标数据类型
     * @param <T>   当前数据的数据类型
     * @param <R>   目标数据的数据类型
     * @return 转换函数
     */
    <T, R> Function<T, R> create(Class<R> clazz);
}