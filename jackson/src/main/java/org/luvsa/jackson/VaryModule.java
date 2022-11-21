package org.luvsa.jackson;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author Aglet
 * @create 2022/11/9 8:52
 */
public class VaryModule extends SimpleModule {

    public VaryModule() {
        super(VersionUtil.parseVersion("1.0.0", "cn.luvsa", "vary"));

        addSerializer(LocalDate.class, LocalDateJsonSerializer.INSTANCE);
        addSerializer(LocalTime.class, LocalTimeJsonSerializer.INSTANCE);
        addSerializer(LocalDateTime.class, LocalDateTimeJsonSerializer.INSTANCE);
        addSerializer(Date.class, DateJsonSerializer.INSTANCE);

        addDeserializer(LocalDate.class, LocalDateJsonDeserializer.INSTANCE);
        addDeserializer(LocalTime.class, LocalTimeJsonDeserializer.INSTANCE);
        addDeserializer(LocalDateTime.class, LocalDateTimeJsonDeserializer.INSTANCE);
        addDeserializer(Date.class, DateJsonDeserializer.INSTANCE);
    }
}
