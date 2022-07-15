package org.luvsa.vary.chrono.zoned;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoZonedDateTime;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoZonedDateTime.class)
public class ZFactory extends AbstractFactory<ChronoZonedDateTime<?>, ZProvider> {


}
