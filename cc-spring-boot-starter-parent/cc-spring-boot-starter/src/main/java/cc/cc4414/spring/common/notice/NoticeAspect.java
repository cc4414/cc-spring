package cc.cc4414.spring.common.notice;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * 抛出异常时，通知人工处理
 * 
 * @author cc 2019年10月1日
 */
@Aspect
@Component
@RequiredArgsConstructor
@Order(0)
public class NoticeAspect {

	private final NoticeProxy noticeProxy;

	@Around("@annotation(noticeAnnotation)")
	public Object around(ProceedingJoinPoint pjp, NoticeAnnotation noticeAnnotation) throws Throwable {
		try {
			return pjp.proceed();
		} catch (Exception e) {
			MethodSignature signature = (MethodSignature) pjp.getSignature();
			Method method = signature.getMethod();
			// 调用notice方法通知异常发生，参数为发生异常的方法全路径
			noticeProxy.notice(method.getDeclaringClass().getName() + "." + method.getName());
			throw e;
		}
	}
}
