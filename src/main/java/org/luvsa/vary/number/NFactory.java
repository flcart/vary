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
@Types({Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class,
        Double.class, double.class, BigDecimal.class, BigInteger.class})
public class NFactory extends AbstractFactory<Number, NProvider> {
}
