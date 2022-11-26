package org.luvsa.vary;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Dale
 * @create 2022/9/2 22:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(StructScannerRegistrar.RepeatingRegistrar.class)
public @interface VaryScans {

	VaryScan[] value();

}
