package org.luvsa.vary.instant;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.function.Function;

/**
 * 将 Instant 转换成 Long 对象
 *
 * @author Aglet
 * @create 2022/6/27 16:07
 */
@Types({Long.class})
public class ToLong implements Provider {

    /**
     * 获取 Instant -> Long 的转换函数
     *
     * @param type 需要转换的目标数据类型
     * @return 转换函数
     */
    @Override
    public Function<Instant, ?> get(Type type) {
        return Instant::toEpochMilli;
    }

    @Override
    public String toString() {
        return "Instant -> Long 函数提供器";
    }
}
