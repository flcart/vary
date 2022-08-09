package org.luvsa.vary.string.array;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

/**
 * @author Aglet
 * @create 2022/7/1 13:47
 */
@Types(String[].class)
public class Factory extends AbstractFactory<String[], Provider> {
    @Override
    public String toString() {
        return "String[]-Factory";
    }
}
