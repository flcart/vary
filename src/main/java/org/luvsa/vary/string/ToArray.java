package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * 字符串转字符串数组
 *
 * @author Aglet
 * @create 2022/7/6 20:26
 */
@Types(String[].class)
public class ToArray implements Provider {

    @Override
    public Function<String, ?> get(Type type) {
        return s -> {
            // 暂时不支持直接转换， 需要了解转换规则
            throw new UnsupportedOperationException();
        };
    }

    @Override
    public String toString() {
        return String.class + " -> " + String[].class + " function provider";
    }
}
