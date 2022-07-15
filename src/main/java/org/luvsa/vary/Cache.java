package org.luvsa.vary;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Provider 缓存
 *
 * @author Aglet
 * @create 2022/6/29 9:56
 */
public final class Cache<T, R extends Provider<T>> extends Manager<R> {
    /**
     * 初始化处理器
     */
    private final Initiator<R> initiator;

    public Cache(Initiator<R> initiator) {
        this.initiator = initiator;
    }

    /**
     * 获取转换函数
     *
     * @param clazz 目标数据类型
     * @return 转换函数
     */
    public Function<T, ?> getFunction(Class<?> clazz) {
        var provider = offer(clazz);
        if (provider == null) {
            return null;
        }
        return provider.get(clazz);
    }

    /**
     * 获取 Provider
     *
     * @param type 目标数据类型
     * @return Provider
     */
    public R get(DataType type) {
        return offer(type.getClazz());
    }

    @Override
    protected R computeIfAbsent(DataType type, Function<Class<?>, R> function) {
        return super.computeIfAbsent(type, function);
    }

    /**
     * 获取 Provider
     *
     * @param clazz 目标数据类型
     * @return Provider
     */
    @Override
    protected R offer(Class<?> clazz) {
        if (this.isEmpty()) {
            initiator.accept(this::put);
        }
        return this.get(clazz);
    }

    public void next(Class<R> clazz, Consumer<R> consumer) {
        loader.load(clazz, this::put, consumer);
    }
}
