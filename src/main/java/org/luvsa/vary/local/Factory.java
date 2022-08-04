package org.luvsa.vary.local;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;

/**
 * {@link LocalDate}、{@link LocalTime}、{@link LocalDateTime} 转换成 指定数据 数据的函数工厂
 *
 *
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class Factory extends AbstractFactory<Temporal, Provider> {

    @Override
    public String toString() {
        return "Local-Factory";
    }

}
