package org.luvsa.vary.zone;

import org.luvsa.vary.TypeSupplier.Types;
import org.luvsa.vary.Vary;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/25 15:52
 */
@Types({Date.class, Long.class})
public class ToOther implements Provider {
    @Override
    public Function<ZonedDateTime, ?> get(Type type) {
        return time -> {
            var instant = Vary.change(time, Instant.class);
            return Vary.convert(instant, type);
        };
    }
}
