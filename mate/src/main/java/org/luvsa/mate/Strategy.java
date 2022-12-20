package org.luvsa.mate;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Aglet
 * @create 2022/12/19 17:27
 */
public interface Strategy extends Supplier<Function<String, String>> {

}
