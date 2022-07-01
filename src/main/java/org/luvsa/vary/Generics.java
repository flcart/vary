package org.luvsa.vary;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * @author Aglet
 * @create 2022/7/1 13:53
 */
public class Generics {

    public static void accept(Class<?> clazz, int index, Consumer<Class<?>> consumer) {
        accept(clazz.getGenericSuperclass(), index, consumer);
    }

    public static void accept(Type generic, int index, Consumer<Class<?>> consumer) {
        if (generic instanceof ParameterizedType type) {
            var arguments = type.getActualTypeArguments();
            var argument = arguments[index];
            if (argument instanceof Class<?> cls) {
                consumer.accept(cls);
                return;
            }
            throw new RuntimeException("泛型类型[" + argument + "]错误！");
        }
    }
}
