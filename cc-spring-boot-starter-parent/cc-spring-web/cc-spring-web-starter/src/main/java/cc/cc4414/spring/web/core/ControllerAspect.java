package cc.cc4414.spring.web.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.ClassUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 所有带有 @RestController 注解的默认切面
 * 
 * @author cc 2019年10月1日
 */
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cc-spring.web", name = "enable-default-aspect", havingValue = "true", matchIfMissing = true)
@Slf4j
public class ControllerAspect {

	private final HttpServletRequest request;

	@Around("@within(restController)")
	public Object around(ProceedingJoinPoint pjp, RestController restController) throws Throwable {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Object[] args = pjp.getArgs();
		parameterSetNull(method, args);
		log.debug("请求url: {}, 参数: {}", request.getRequestURI(), args);
		Object result = pjp.proceed();
		returnSetNull(method, result);
		log.debug("结果: {}", result);
		return result;
	}

	/**
	 * 根据参数上的{@link @SetNull}注解将参数的部分字段设置空值
	 * 
	 * @param method 方法
	 * @param args   参数
	 */
	private void parameterSetNull(Method method, Object[] args) {
		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			if (parameter.isAnnotationPresent(SetNull.class)) {
				SetNull annotation = parameter.getAnnotation(SetNull.class);
				Class<?>[] groups = annotation.value();
				setNull(args[i], groups);
			}
		}
	}

	/**
	 * 根据方法上的{@link @SetNull}注解将返回结果的部分字段设置空值
	 * 
	 * @param method 方法
	 * @param result 返回结果
	 */
	private void returnSetNull(Method method, Object result) {
		if (method.isAnnotationPresent(SetNull.class)) {
			SetNull annotation = method.getAnnotation(SetNull.class);
			Class<?>[] groups = annotation.value();
			setNull(result, groups);
		}
	}

	/**
	 * 将对象obj中有 SetNull 注解的字段根据分组class设置空值<br>
	 * 字段上的 SetNull 注解没有value参数时，该字段设置为空<br>
	 * 字段上的 SetNull 注解有value参数时，参数包含了分组class中的任意一个class，该字段设置为空<br>
	 * 
	 * @param obj   对象
	 * @param clazz 分组class
	 */
	@SneakyThrows
	public static void setNull(Object obj, Class<?>[] clazz) {
		if (obj == null) {
			// null不遍历字段
			return;
		}
		if (ClassUtil.isSimpleTypeOrArray(obj.getClass())) {
			// 简单值类型及其数组不遍历字段
			return;
		}
		if (obj instanceof Map) {
			// Map不遍历字段
			return;
		}
		if (obj instanceof TemporalAccessor) {
			// 时间类型不遍历字段
			return;
		}
		if (obj instanceof Collection) {
			// 集合遍历设置空值
			for (Object iter : (Collection<?>) obj) {
				setNull(iter, clazz);
			}
			return;
		}
		if (obj.getClass().isArray()) {
			// 数组遍历设置空值
			Object[] array = (Object[]) obj;
			for (Object object : array) {
				setNull(object, clazz);
			}
			return;
		}
		// 遍历所有字段
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(SetNull.class)) {
				// 如果有@SetNull注解，根据group的值来判断是否设置空值
				List<Class<?>> groups = Arrays.asList(field.getAnnotation(SetNull.class).value());
				boolean b = groups.size() == 0;
				for (Class<?> c : clazz) {
					b = b || groups.contains(c);
				}
				if (b) {
					field.set(obj, null);
				}
			} else {
				// 递归设置空值
				setNull(field.get(obj), clazz);
			}
		}
	}
}
