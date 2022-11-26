package org.luvsa.vary;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Dale
 * @create 2022/9/2 22:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(StructScannerRegistrar.class)
@Repeatable(VaryScans.class)
public @interface VaryScan {

    String[] value() default {};

    Class<? extends BeanNameGenerator> generator() default BeanNameGenerator.class;
}
