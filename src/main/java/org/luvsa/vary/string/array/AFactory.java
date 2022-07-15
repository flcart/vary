package org.luvsa.vary.string.array;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.DataType;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/1 13:47
 */
@Types(String[].class)
public class AFactory extends AbstractFactory<String[], AProvider> {

    @Override
    public Function<String[], ?> create(DataType type) {
        var provider = map.get(type);
        if (provider == null){
            throw new IllegalArgumentException();
        }
        return provider.get(type);
    }
}
