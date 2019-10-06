package cc.cc4414.spring.common.retry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 在调用接口失败等场景下，会选择重试<br>
 * 可在需要重试的方法上增加该注解，自动进行重试<br>
 * 该注解可以加在接口上，如feign中定义的接口可以使用<br>
 * 
 * @see RetryAspect#around
 * 
 * @author cc 2019年10月1日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RetryAnnotation {
	/**
	 * 重试次数，默认0次不重试
	 */
	@AliasFor("retries")
	int value() default 0;

	/**
	 * @see #value()
	 */
	@AliasFor("value")
	int retries() default 0;

	/**
	 * 重试基础时间间隔<br>
	 * 重试时间间隔会每次加倍<br>
	 * 例如基础时间间隔是1秒，则后面时间是2秒、4秒、8秒。。。<br>
	 * 如果不需要时间间隔可以设置为0L<br>
	 */
	long interval() default 1000L;
}
