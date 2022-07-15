package org.luvsa.vary.local;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class LFactory extends AbstractFactory<TemporalAccessor, LProvider> {

}
