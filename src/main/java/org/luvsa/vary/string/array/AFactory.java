package org.luvsa.vary.string.array;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/1 13:47
 */
@Types(String[].class)
public class AFactory extends FunctionManager<String[], AProvider> implements Factory<String[]> {
    @Override
    public Function<String[], ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), clz -> {
            var provider = map.get(type);
            return provider.get(type);
        });
    }

}
