package org.luvsa.vary.temporal;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.ZonedDateTime;

/**
 * @author Aglet
 * @create 2022/6/27 15:29
 */
@Types(ZonedDateTime.class)
public class TFactory extends AbstractFactory<ZonedDateTime, TProvider> {

}
