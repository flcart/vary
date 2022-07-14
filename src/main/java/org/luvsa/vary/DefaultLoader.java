package org.luvsa.vary;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 默认采用 java spi 机制加载
 *
 * @author Aglet
 * @create 2022/7/14 11:44
 */
public class DefaultLoader implements Loader {
    @Override
    public <R extends TypeSupplier> void load(Class<R> clazz, BiConsumer<Class<?>, R> next, Consumer<R> err) {
        var providers = ServiceLoader.load(clazz);
        for (var item : providers) {
            var aClass = item.getClass();
            // 获取 进行数据转换的数据类型
            var types = aClass.getAnnotation(Types.class);
            // 如果 注解 Types 没有声明， 则直接调 Provider.getTypes() 方法，
            var classes = types == null ? item.getTypes() : types.value();
            for (var clas : classes) {
                // 处理当前 被转换数据的类型 和 Provider
                // 一个 被转换数据的类型 对应一个 Provider
                next.accept(clas, item);
            }
            if (classes.length == 0) {
                // 为载入后续处理
                err.accept(item);
            }
        }
    }
}
