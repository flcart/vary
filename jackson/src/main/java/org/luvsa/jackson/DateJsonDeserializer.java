package org.luvsa.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.luvsa.vary.Vary;

import java.io.IOException;
import java.util.Date;

/**
 * @author Aglet
 * @create 2022/11/9 9:07
 */
class DateJsonDeserializer extends JsonDeserializer<Date> {

    public static final DateJsonDeserializer INSTANCE = new DateJsonDeserializer();

    private DateJsonDeserializer() {
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        var value = p.readValueAs(String.class);
        return Vary.change(value, Date.class);
    }
}
