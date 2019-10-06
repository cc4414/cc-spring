package cc.cc4414.spring.common.core;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import cc.cc4414.spring.common.util.ClassUtils;

/**
 * 自动装配，扫描带有 @Component 注解的类<br>
 * 需要实现 ImportSelector 接口进行自动装配的模块直接继承该类即可<br>
 * 
 * @author cc 2019年10月1日
 */
public class BaseAutoConfigurationImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		String packageName = this.getClass().getPackage().getName();
		return ClassUtils.scanComponent(packageName);
	}

}
