package org.luvsa.vary.string.array;

import org.luvsa.vary.DataType;
import org.luvsa.reflect.Generics;
import org.luvsa.vary.TypeSupplier.Types;
import org.luvsa.vary.Vary;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/30 17:24
 */
@Types(List.class)
public class ToList implements AProvider {

    @Override
    public Function<String[], ?> get(DataType type) {
        return array -> {
            var size = array.length;
            var list = new ArrayList<>(size);
            Generics.accept(type.getGenericType(), 0, clazz -> {
                for (var s : array) {
                    list.add(Vary.change(s, clazz));
                }
            });
            return list;
        };
    }
}
