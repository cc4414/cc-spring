package cc.cc4414.spring.common.feign;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.common.result.ResultVO;

/**
 * 调用接口后的统一处理
 * 
 * @author cc 2019年10月1日
 */
@Aspect
@Component
@Order(-1)
public class FeignAspect {

	@Around("@within(feignAnnotation)")
	public Object around(ProceedingJoinPoint pjp, FeignAnnotation feignAnnotation) throws Throwable {
		Object result = pjp.proceed();
		if (result instanceof ResultVO<?> && !((ResultVO<?>) result).isSuccess()) {
			// 如果返回的是 ResultVO 并且不是成功，则直接抛出异常。如果需要对异常进行处理，则进行捕获
			throw new ResultException((ResultVO<?>) result);
		}
		return result;
	}

}
