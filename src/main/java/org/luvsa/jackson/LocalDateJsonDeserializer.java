package org.luvsa.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Aglet
 * @create 2022/11/2 11:05
 */
class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {

    public static final LocalDateJsonDeserializer INSTANCE = new LocalDateJsonDeserializer();

    private LocalDateJsonDeserializer() {
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        var value = parser.readValueAs(String.class);
        return Vary.change(value, LocalDate.class);
    }
}
