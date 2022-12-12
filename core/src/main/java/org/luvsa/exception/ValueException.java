package org.luvsa.exception;

/**
 * @author Aglet
 * @create 2022/12/7 15:19
 */
public class ValueException extends RuntimeException {
    private final Object value;

    public ValueException(Object o) {
        this.value = o;
    }

    public Object getValue() {
        return value;
    }
}
