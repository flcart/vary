package org.luvsa.vary;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 加载， 载入
 *
 * @author Aglet
 * @create 2022/7/14 11:40
 */
public interface Loader {

    default <R extends Support> void load(Class<R> clazz, BiConsumer<Class<?>, R> consumer) {
        load(clazz, consumer, r -> {
            // 不做处理
        });
    }

    /**
     * 转载 指定 class 实列
     *
     * @param clazz 指定 Class 对象
     * @param next  加载成功 指定 Class对象处理器 (指定了 {@link Support#getTypes() 类型})
     * @param err   加载失败 指定 Class对象处理器 (未指定 {@link Support#getTypes() 类型})
     * @param <R>   指定 Class 对象数据类型
     */
    <R extends Support> void load(Class<R> clazz, BiConsumer<Class<?>, R> next, Consumer<R> err);

}
