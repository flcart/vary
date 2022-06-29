package org.luvsa.vary;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author Aglet
 * @create 2022/6/29 16:59
 */
abstract class Manager<T> {

    /**
     * 数据转换函数的缓存
     */
    protected final Map<Class<?>, T> cache = new ConcurrentHashMap<>();

    /**
     * 加载 TypeSupplier 的子接口
     *
     * @param clazz TypeSupplier的子接口
     * @param consumer   保存 TypeSupplier 实现对象的存储器
     * @param <R>   TypeSupplier 的子接口
     */
    protected <R extends TypeSupplier> void load(Class<R> clazz, BiConsumer<Class<?>, R> consumer) {
        var providers = ServiceLoader.load(clazz);
        for (var item : providers) {
            var aClass = item.getClass();
            var types = aClass.getAnnotation(Types.class);
            var classes = types == null ? item.getTypes() : types.value();
            for (var clas : classes) {
                consumer.accept(clas, item);
            }
        }
    }

    /**
     * 获取转换函数
     *
     * @param clazz 目标数据类型
     * @return 转换函数
     */
    protected abstract T offer(Class<?> clazz);
}
