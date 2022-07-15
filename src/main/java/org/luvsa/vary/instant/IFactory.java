package org.luvsa.vary.instant;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(Instant.class)
public class IFactory extends AbstractFactory<Instant, IProvider> {

}
