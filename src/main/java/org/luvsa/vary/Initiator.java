package org.luvsa.vary;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Dale
 * @create 2022/7/3 13:50
 */
public interface Initiator<R> extends Consumer<BiConsumer<Class<?>, R>> {
}
