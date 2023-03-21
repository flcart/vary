package org.luvsa.reflect;

import org.luvsa.annotation.Types;
import org.luvsa.vary.Vary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author Aglet
 * @create 2023/3/18 11:31
 */
@Types({Date.class, LocalDate.class, LocalTime.class, LocalDateTime.class, UUID.class})
public class DateRepresent implements Represent {
    @Override
    public Object next(Object o) {
        return Vary.change(o, String.class);
    }

}
