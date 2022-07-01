package org.luvsa.vary.chrono.zoned;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;
import java.util.function.Function;

/**
 * {@link ChronoZonedDateTime} 转 {@link Date} 和 {@link Long 时间戳}
 *
 * @author Aglet
 * @create 2022/6/27 16:22
 */
@Types({Date.class, Long.class})
public class ToOther extends ToInstant implements ZProvider {

    @Override
    public Function<ChronoZonedDateTime<?>, ?> get(Class<?> clazz) {
        return super.get(clazz).andThen(found(clazz));
    }

}
