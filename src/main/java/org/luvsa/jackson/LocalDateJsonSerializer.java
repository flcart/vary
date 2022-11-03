package org.luvsa.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Aglet
 * @create 2022/11/2 11:08
 */
public class LocalDateJsonSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var s = Vary.change(value, String.class);
        gen.writeString(s);
    }
}
