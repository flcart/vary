package org.luvsa.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.util.Date;

/**
 * @author Aglet
 * @create 2022/11/9 9:04
 */
class DateJsonSerializer extends JsonSerializer<Date> {

    public static final DateJsonSerializer INSTANCE = new DateJsonSerializer();

    private DateJsonSerializer() {
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var s = Vary.change(value, String.class);
        gen.writeString(s);
    }
}
