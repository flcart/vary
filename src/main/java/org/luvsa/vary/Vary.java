package org.luvsa.vary;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/6/29 16:47
 */
public interface Vary {

    @SuppressWarnings("unchecked")
    static <T, R> R change(T value, Class<R> cls) {
        return (R) DefaultVary.instance.apply(value, new Clazz(cls));
    }

    static <T> Object change(T value, DataType type) {
        return DefaultVary.instance.apply(value, type);
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

    <T> Object apply(T value, DataType type);
}
