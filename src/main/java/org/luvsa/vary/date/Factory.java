package org.luvsa.vary.date;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.Date;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(Date.class)
public class Factory extends AbstractFactory<Date, Provider> {
    @Override
    public String toString() {
        return "Date-Factory";
    }

}
