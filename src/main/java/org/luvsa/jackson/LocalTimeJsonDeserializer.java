package org.luvsa.jackson;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.time.LocalTime;

/**
 * @author Aglet
 * @create 2022/11/2 11:09
 */
class LocalTimeJsonDeserializer extends JsonDeserializer<LocalTime> {

    public static final LocalTimeJsonDeserializer INSTANCE = new LocalTimeJsonDeserializer();

    private LocalTimeJsonDeserializer() {
    }

    @Override
    public LocalTime deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext context) throws IOException {
        var value = p.readValueAs(String.class);
        return Vary.change(value, LocalTime.class);
    }
}