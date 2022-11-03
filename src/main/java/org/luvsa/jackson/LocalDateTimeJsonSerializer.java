package org.luvsa.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Aglet
 * @create 2022/11/2 11:02
 */
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        var s = Vary.change(value, String.class);
        generator.writeString(s);
    }

}
