package org.luvsa.vary;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * 数据转换函数的提供者接口
 *
 * @author Dale
 * @create 2022/5/5 1:45
 */
public interface Provider<T> extends TypeSupplier {

    /**
     * 获取数据转换函数
     *
     * @param type 需要转换的目标数据类型
     * @return 数据转换函数
     */
    Function<T, ?> get(Type type);
}
