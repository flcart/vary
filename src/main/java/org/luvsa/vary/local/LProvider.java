package org.luvsa.vary.local;

import org.luvsa.vary.Provider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

/**
 * {@link LocalDate}、{@link LocalTime}、{@link LocalDateTime} 转换成 指定数据 数据的函数供应商
 *
 * @author Aglet
 * @create 2022/6/27 14:53
 */
public interface LProvider extends Provider<TemporalAccessor> {

}
