package org.luvsa.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.time.LocalTime;

/**
 * @author Aglet
 * @create 2022/11/2 11:09
 */
public class LocalTimeJsonSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var s = Vary.change(value, String.class);
        gen.writeString(s);
    }
}
