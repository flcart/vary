package org.luvsa.exception;

import java.io.Serial;

/**
 * 值异常， 用于携带参数
 *
 * @author Aglet
 * @create 2022/12/7 15:19
 */
public class ValueException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3822607306014117325L;
    private final Object value;

    public ValueException(Object o) {
        this.value = o;
    }

    public Object getValue() {
        return value;
    }
}
