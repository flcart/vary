package org.luvsa.lang;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import jdk.jfr.Name;
import lombok.extern.slf4j.Slf4j;
import org.luvsa.mate.Strategies;
import org.luvsa.mate.Strategy;

import java.io.IOException;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/12/20 9:00
 */
@Slf4j
public class MateJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private final Map<String, Strategy> strategies = new ConcurrentHashMap<>();

    private Function<String, String> function;

    public MateJsonSerializer() {
        var strategies = ServiceLoader.load(Strategy.class);
        for (var item : strategies) {
            var aClass = item.getClass();
            var name = aClass.getAnnotation(Name.class);
            if (name == null) {
                var guess = Strategies.guess(aClass.getSimpleName());
                this.strategies.put(guess, item);
            } else {
                this.strategies.put(name.value(), item);
            }
        }
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null){
            return;
        }
        if (function == null) {
            gen.writeString(value);
        } else {
            gen.writeString(function.apply(value));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        var sensitive = property.getAnnotation(Sensitive.class);
        var type = property.getType();
        if (sensitive == null) {
            return prov.findValueSerializer(type, property);
        }
        var value = sensitive.value();
        var strategy = strategies.get(value);
        if (strategy == null) {
            var format = sensitive.format();
            var pattern = sensitive.pattern();
            this.function = s -> {
                if (format == null || pattern == null) {
                    return s;
                }
                return s.replaceAll(pattern, format);
            };
        } else {
            this.function = strategy.get();
        }
        return this;
    }
}
