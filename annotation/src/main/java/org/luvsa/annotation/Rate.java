package org.luvsa.annotation;

import java.lang.annotation.*;

/**
 * 限流
 *
 * @author Aglet
 * @create 2023/2/2 15:52
 */
public interface Rate {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface Limiter {
    }
}
