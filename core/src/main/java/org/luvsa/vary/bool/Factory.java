package org.luvsa.vary.bool;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.annotation.Types;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({Boolean.class})
public class Factory extends AbstractFactory<Boolean, Provider> {

    @Override
    public String toString() {
        return "Boolean-Factory";
    }

}
