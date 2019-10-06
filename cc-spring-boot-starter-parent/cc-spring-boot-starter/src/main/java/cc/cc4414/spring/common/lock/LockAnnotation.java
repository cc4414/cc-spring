package cc.cc4414.spring.common.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 分布式锁的注解，在需要加锁的方法上加上该注解即可<br>
 * 没抢到锁会抛出 CommonResultEnum.OBTAIN_LOCK_FAILED 异常<br>
 * 
 * @see LockAspect#around
 * 
 * @author cc 2019年10月1日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockAnnotation {
	/**
	 * 与锁关联的字符串
	 */
	@AliasFor("key")
	String value() default "";

	/**
	 * @see #value()
	 */
	@AliasFor("value")
	String key() default "";

	/**
	 * 如果该值不为空，则锁的{@link #key}后面再加上该表达式结果<br>
	 * 支持SpEL表达式<br>
	 */
	String spelKey() default "";

	/**
	 * 等待锁的最长时间，单位为毫秒
	 */
	long time() default 0L;
}
