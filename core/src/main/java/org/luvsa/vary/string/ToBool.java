package org.luvsa.vary.string;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 17:31
 */
@Types({Boolean.class})
public class ToBool implements Provider {

    /**
     * 值为 true 时的 字符串数组， 用于 String 转 Boolean 类型
     * <p>
     * 调整了字符串的顺序， 方便 二分法 查找， 后续添加，注意顺序
     */
    private final String[] POSITIVE = {"1", "ok", "on", "true", "up", "y", "yes", "对", "是", "男", "真"};

    /**
     * 将 {@link #POSITIVE} 转换成 对应的数组下标， 在数组中没有， 则下标为 -1
     */
    private final Function<String, Integer> function = value -> Arrays.binarySearch(POSITIVE, value, String::compareToIgnoreCase);

    @Override
    public Function<String, ?> get(Type type) {
        return function.andThen(index -> index >= 0 && POSITIVE.length > index);
    }

    @Override
    public String toString() {
        return String.class + " -> " + Boolean.class + " function provider";
    }
}
