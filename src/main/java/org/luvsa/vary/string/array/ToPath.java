package org.luvsa.vary.string.array;

import org.luvsa.vary.DataType;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 11:17
 */
public class ToPath implements AProvider{
    @Override
    public Function<String[], ?> get(DataType type) {
        return strings -> {
            return null;
        };
    }
}
