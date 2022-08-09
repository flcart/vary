package org.luvsa.vary.array;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

/**
 * @author Aglet
 * @create 2022/7/18 11:09
 */
@Types({byte[].class, short[].class, int[].class, long[].class, float[].class, long[].class, char[].class, boolean[].class})
public class Factory extends AbstractFactory<Object, Provider> {
}
