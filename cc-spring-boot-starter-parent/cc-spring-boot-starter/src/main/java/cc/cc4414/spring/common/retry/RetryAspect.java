package cc.cc4414.spring.common.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过在方法上增加注解进行重试
 * 
 * @author cc 2019年10月1日
 */
@Aspect
@Component
@Order(2)
@Slf4j
public class RetryAspect {

	@Around("@annotation(retryAnnotation)")
	public Object around(ProceedingJoinPoint pjp, RetryAnnotation retryAnnotation) throws Throwable {
		Object result = null;
		retryAnnotation = AnnotationUtils.synthesizeAnnotation(retryAnnotation, null);
		int retries = retryAnnotation.retries();
		long interval = retryAnnotation.interval();
		for (int i = 0; i <= retries; i++) {
			try {
				result = pjp.proceed();
				break;
			} catch (Exception e) {
				log.error("方法执行失败第{}次", i + 1);
				if (i == retries) {
					throw e;
				}
				Thread.sleep(interval << i);
			}
		}
		return result;
	}

}
