package org.luvsa.vary.bool;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({Boolean.class, boolean.class})
public class BFactory extends AbstractFactory<Boolean, BProvider> {

}
