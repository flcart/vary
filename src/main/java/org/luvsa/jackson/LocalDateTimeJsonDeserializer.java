package org.luvsa.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Aglet
 * @create 2022/11/2 11:08
 */
class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
    public static final LocalDateTimeJsonDeserializer INSTANCE = new LocalDateTimeJsonDeserializer();

    private LocalDateTimeJsonDeserializer() {
    }
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext context) throws IOException {
        var value = p.readValueAs(String.class);
        return Vary.change(value, LocalDateTime.class);
    }
}

