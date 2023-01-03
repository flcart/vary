package org.luvsa.vary.zone;

import org.luvsa.annotation.Types;
import org.luvsa.vary.AbstractFactory;

import java.time.ZonedDateTime;

/**
 * @author Aglet
 * @create 2022/6/27 15:29
 */
@Types(ZonedDateTime.class)
public class Factory extends AbstractFactory<ZonedDateTime, Provider> {

    @Override
    public String toString() {
        return "ZonedDateTime-Factory";
    }

}
