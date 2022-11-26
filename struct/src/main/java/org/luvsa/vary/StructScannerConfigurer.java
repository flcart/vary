package org.luvsa.vary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import static org.springframework.util.Assert.notNull;

/**
 * @author Dale
 * @create 2022/9/3 8:58
 */
@Slf4j
public class StructScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    /**
     * 配置的扫描路径
     */
    private String basePackage;

    private String beanName;

    private ApplicationContext applicationContext;

    private BeanNameGenerator generator;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void setGenerator(BeanNameGenerator generator) {
        this.generator = generator;
    }

    /**
     * 扫描基础包
     *
     * @param basePackage 包路径
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void afterPropertiesSet() {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        var map = applicationContext.getBeansOfType(PropertyResourceConfigurer.class, false, false);
        if (!map.isEmpty() && applicationContext instanceof ConfigurableApplicationContext context) {
            var factory = context.getBeanFactory();
            var definition = factory.getBeanDefinition(this.beanName);
            var values = definition.getPropertyValues();
			log.debug(" values : {}", values);
        }
        var scanner = new StructMapperScanner(registry);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setBeanNameGenerator(this.generator);
        // 添加扫描过滤逻辑
        scanner.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            var className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
        scanner.addIncludeFilter((reader, factory) -> true);
        var strings = StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        scanner.scan(strings);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
