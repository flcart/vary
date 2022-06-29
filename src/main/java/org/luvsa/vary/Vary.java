package org.luvsa.vary;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/6/29 16:47
 */
public interface Vary {
    static <T, R> R change(T value, Class<R> clazz) {
        return DefaultVary.instance.accept(value, clazz);
    }

    static <T, R> R change(T value, R dev) {
        @SuppressWarnings("unchecked")
        var clazz = (Class<R>) dev.getClass();
        var con = change(value, clazz);
        return Objects.requireNonNullElse(con, dev);
    }

    static <T, R> Object realize(T o, Class<R> clazz) {
        try {
            var rtn = change(o, clazz);
            return rtn == null ? o : rtn;
        } catch (Exception e) {
            return o;
        }
    }

    <T, R> R accept(T value, Class<R> aClass);
}
