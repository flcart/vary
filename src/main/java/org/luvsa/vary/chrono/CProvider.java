package org.luvsa.vary.chrono;

import org.luvsa.vary.Provider;

import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:58
 */
public interface CProvider extends Provider<ChronoLocalDateTime<?>> {

    Function<ChronoLocalDateTime<?>,?> get(Class<?> clazz);

}
