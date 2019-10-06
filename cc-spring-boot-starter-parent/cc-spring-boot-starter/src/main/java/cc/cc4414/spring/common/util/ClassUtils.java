package cc.cc4414.spring.common.util;

import java.util.Set;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.ClassUtil;
import lombok.experimental.UtilityClass;

/**
 * 类工具包
 * 
 * @author cc 2019年10月1日
 */
@UtilityClass
public class ClassUtils {
	/**
	 * 扫描指定包下所有带有 @Component 注解（包括组合注解）的类<br>
	 * 注意，忽略掉带有 @Import 注解的类
	 * 
	 * @param packageName 包名
	 * @return 类全路径的数组
	 */
	public String[] scanComponent(String packageName) {
		Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
		String[] classNames = classes.stream()
				.filter(i -> AnnotatedElementUtils.hasAnnotation(i, Component.class)
						&& !AnnotatedElementUtils.hasAnnotation(i, Import.class))
				.map(i -> i.getName()).toArray(String[]::new);
		return classNames;
	}
}
