package org.luvsa.vary;

import org.springframework.util.ReflectionUtils;

/**
 * @author Aglet
 * @create 2022/11/21 15:08
 */
public class StructMapperScanner {


    private void inject(String className) {
        try {
            var aClass = Class.forName(className);
            var mapper = Mappers.getMapper(aClass);
            log.info("加载 mapstruct 数据转换接口 ： {}", aClass);
            ReflectionUtils.doWithLocalMethods(aClass, method -> {
                var parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    return;
                }
                var parameterType = parameterTypes[0];
                var genericReturnType = method.getGenericReturnType();
                StructVary.accept(parameterType, genericReturnType,  method, mapper);
            });
        } catch (ClassNotFoundException e) {
            //
        }
    }

}
