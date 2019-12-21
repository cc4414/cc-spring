package cc.cc4414.spring.common.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * 通过在方法上增加注解进行重试
 * 
 * @author cc 2019年10月1日
 */
@Aspect
@Component
@RequiredArgsConstructor
@Order(2)
public class RetryAspect {

	private final RetryTemplate retryTemplate;

	@Around("@annotation(retryAnnotation)")
	public Object around(ProceedingJoinPoint pjp, RetryAnnotation retryAnnotation) throws Throwable {
		retryAnnotation = AnnotationUtils.synthesizeAnnotation(retryAnnotation, null);
		int retries = retryAnnotation.retries();
		long interval = retryAnnotation.interval();
		return retryTemplate.retry(() -> {
			try {
				return pjp.proceed();
			} catch (RuntimeException re) {
				throw re;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}, retries, interval);
	}

}
