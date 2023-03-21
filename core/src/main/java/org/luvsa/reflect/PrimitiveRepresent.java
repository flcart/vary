package org.luvsa.reflect;

import org.luvsa.annotation.Types;

/**
 * Object 转 map 过程中的一些sh够处理
 *
 * @author Aglet
 * @create 2023/3/18 11:27
 */
@Types({
        byte.class, Byte.class,
        short.class, Short.class,
        int.class, Integer.class,
        long.class, Long.class,
        float.class, Float.class,
        double.class, Double.class,
        char.class, Character.class,
        boolean.class, Boolean.class,
        String.class
})
public class PrimitiveRepresent implements Represent {
    @Override
    public Object next(Object o) {
        return o;
    }
}
