package org.luvsa.vary;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;

/**
 * @author Aglet
 * @create 2022/11/21 15:08
 */
@Slf4j
public class StructMapperScanner extends ClassPathBeanDefinitionScanner {

    public StructMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader reader) throws IOException {
        var metadata = reader.getClassMetadata();
        if (metadata.isInterface() && metadata.isIndependent()) {
            inject(metadata.getClassName());
        }
        return false;
    }

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
                StructVary.accept(parameterType, genericReturnType, method, mapper);
            });
        } catch (ClassNotFoundException e) {
            //
        }
    }

}
