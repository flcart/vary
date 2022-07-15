package org.luvsa.vary.chrono;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoLocalDateTime;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoLocalDateTime.class)
public class CFactory extends AbstractFactory<ChronoLocalDateTime<?>, CProvider> {

}
