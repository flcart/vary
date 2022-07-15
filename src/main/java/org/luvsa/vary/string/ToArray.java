package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/6 20:26
 */
@Types(String[].class)
public class ToArray implements SProvider {

    @Override
    public Function<String, ?> get(Type type) {
//        return s -> {
//            // 需要分割符号
//        };
//        Thread.currentThread().set
        return null;
    }
}
