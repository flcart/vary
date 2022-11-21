package org.luvsa.vary.number;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数字转 其他数据
 *
 * @author Aglet
 * @create 2022/6/27 9:30
 */
@Types({Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigDecimal.class, BigInteger.class})
public class Factory extends AbstractFactory<Number, Provider> {
    @Override
    public String toString() {
        return "Number-Factory";
    }

}
