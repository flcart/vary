package org.luvsa.vary;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 容器
 *
 * @author Aglet
 * @create 2022/6/29 9:56
 */
public final class Cache<T, R extends Provider<T>> {

    private final Map<Class<?>, R> map = new HashMap<>();

    private final Consumer<BiConsumer<Class<?>, R>> initiator;

    public Cache(Consumer<BiConsumer<Class<?>, R>> initiator) {
        this.initiator = initiator;
    }

    public Function<T, ?> get(Class<?> clazz) {
        if (map.isEmpty()) {
            initiator.accept(map::put);
        }
        var provider = map.get(clazz);
        if (provider == null) {
            return null;
        }
        return provider.get(clazz);
    }
}
