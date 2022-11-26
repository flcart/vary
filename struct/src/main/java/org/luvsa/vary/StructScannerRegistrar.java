package org.luvsa.vary;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描 mapstruct mapper 数据转换器
 *
 * @author Dale
 * @create 2022/9/2 22:31
 */
public class StructScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		//从启动类上面获取 MapperScan 注解
		var map = metadata.getAnnotationAttributes(VaryScan.class.getName());
		var attributes = AnnotationAttributes.fromMap(map);
		if (attributes != null) {
			var name = generateBaseBeanName(metadata, 0);
			registerBeanDefinitions(attributes, registry, name);
		}
	}

	private void inject(List<String> packages, String[] array) {
		for (var s : array) {
			if (StringUtils.hasText(s)) {
				packages.add(s);
			}
		}
	}

	/**
	 * 注册 bean definition
	 *
	 * @param attributes 注解属性
	 * @param registry   bean definition 注册器
	 * @param name       bean 名称
	 */
	void registerBeanDefinitions(AnnotationAttributes attributes, BeanDefinitionRegistry registry, String name) {
		//存在的话那么注册自己的BeanDefinitions
		var builder = BeanDefinitionBuilder.genericBeanDefinition(StructScannerConfigurer.class);
		var packages = new ArrayList<String>();
		// 注入注解属性

		inject(packages, attributes.getStringArray("value"));
		var generatorClass = attributes.getClass("generator");
		if (!BeanNameGenerator.class.equals(generatorClass)) {
			builder.addPropertyValue("generator", BeanUtils.instantiateClass(generatorClass));
		}

		// 添加 basePackage 的属性
		builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));
		builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

		var definition = builder.getBeanDefinition();
		// 添加到注册器
		registry.registerBeanDefinition(name, definition);
	}

	/**
	 * 生成 beanName
	 *
	 * @param metadata 注解数据
	 * @param index    下标
	 * @return name
	 */
	private static String generateBaseBeanName(AnnotationMetadata metadata, int index) {
		return metadata.getClassName() + "#" + StructScannerRegistrar.class.getSimpleName() + "#" + index;
	}

	static class RepeatingRegistrar extends StructScannerRegistrar {
		@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
			var map = metadata.getAnnotationAttributes(VaryScans.class.getName());
			var attributes = AnnotationAttributes.fromMap(map);
			if (attributes != null) {
				var annotations = attributes.getAnnotationArray("value");
				for (int i = 0; i < annotations.length; i++) {
					var name = generateBaseBeanName(metadata, i);
					registerBeanDefinitions(annotations[i], registry, name);
				}
			}
		}
	}
}
