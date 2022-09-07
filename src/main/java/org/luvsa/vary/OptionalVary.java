package org.luvsa.vary;

import java.lang.reflect.Type;

/**
 * 结合 Optional 性质实现数据转换
 *
 * @author Aglet
 * @create 2022/7/13 15:09
 */
public class OptionalVary implements Vary {
    @Override
    public <T> Object apply(T value, Class<?> clazz, Type type) throws Exception {
        return null;
    }
}
