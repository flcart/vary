package org.luvsa.vary.string.array;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Provider;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/1 13:47
 */
public interface AProvider extends Provider<String[]> {
    @Override
    default Function<String[], ?> get(Class<?> clazz) {
        throw new RuntimeException();
    }

    Function<String[], ?> get(DataType type);
}
