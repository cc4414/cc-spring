package cc.cc4414.spring.common.lock;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;

import cc.cc4414.spring.common.result.CommonResultEnum;
import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.common.util.SpelUtils;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * 通过方法上的注解进行加锁
 * 
 * @author cc 2019年10月1日
 */
@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
public class LockAspect {

	private final LockRegistry lockRegistry;

	@Around("@annotation(lockAnnotation)")
	public Object around(ProceedingJoinPoint pjp, LockAnnotation lockAnnotation) throws Throwable {
		lockAnnotation = AnnotationUtils.synthesizeAnnotation(lockAnnotation, null);
		String lockKey = lockAnnotation.key();
		String spelKey = lockAnnotation.spelKey();
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		if (StrUtil.isBlank(lockKey)) {
			// 如果注解中的key值为空，则默认使用方法的全路径名，注意，同一个类里面的同名方法会加同样的锁
			lockKey = method.getDeclaringClass().getName() + "." + method.getName();
		}
		if (StrUtil.isNotBlank(spelKey)) {
			// 解析spel表达式，作为key的后缀
			lockKey += ":" + SpelUtils.parse(spelKey, method, pjp.getArgs());
		}
		Lock lock = lockRegistry.obtain(lockKey);
		if (lock.tryLock(lockAnnotation.time(), TimeUnit.MILLISECONDS)) {
			try {
				return pjp.proceed();
			} finally {
				lock.unlock();
			}
		} else {
			throw new ResultException(CommonResultEnum.OBTAIN_LOCK_FAILED);
		}
	}
}
