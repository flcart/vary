package org.luvsa.vary;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/1 16:26
 */
public class Clazz implements DataType {

    private final Class<?> value;

    public Clazz(Class<?> value) {
        this.value = value;
    }

    @Override
    public Class<?> getClazz() {
        return value;
    }

    @Override
    public Type getGenericType() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        var clazz = (Clazz) o;
        return Objects.equals(value, clazz.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
