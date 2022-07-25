package org.luvsa.vary.instant;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 *
 * @author Aglet
 * @create 2022/6/27 14:40
 */
@Types(ZonedDateTime.class)
public class ToZoned implements Provider {
    @Override
    public Function<Instant, ?> get(Type type) {
        return instant -> instant.atZone(ZoneId.systemDefault());
    }

}
