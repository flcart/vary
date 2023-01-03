package org.luvsa.exception;

import java.io.Serial;

/**
 * @author Aglet
 * @create 2022/7/13 11:07
 */
public class FactoryNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = -7448270884351058056L;

    public FactoryNotFoundException(Class<?> clazz) {
        this("Type of " + clazz + " factory not found!");
    }

    public FactoryNotFoundException(String message) {
        super(message);
    }

    public FactoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FactoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public FactoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
