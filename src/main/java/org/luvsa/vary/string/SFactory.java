package org.luvsa.vary.string;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.DataType;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.Optional;
import java.util.function.Function;

/**
 * 字符串转换成指定数据类型的工厂
 *
 * @author Aglet
 * @create 2022/6/25 10:47
 */
@Types(String.class)
public class SFactory extends AbstractFactory<String, SProvider> {

    private final static Function<String, Optional<String>> barrier = s -> s.isBlank() ? Optional.empty() : Optional.of(s);
    
    @Override
    public Function<String, ?> create(DataType type) {
        return barrier.andThen(optional ->  optional.map(super.create(type)).orElse(null));
    }
}
