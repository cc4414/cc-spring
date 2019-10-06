package cc.cc4414.spring.common.feign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在调用别人接口时，如果返回的报文格式为 ResultVO<br>
 * 则可以在 @FeignClient 注解的接口上面加上 @FeignAnnotation 注解<br>
 * 可以对返回结果统一处理<br>
 * 如果返回的是 ResultVO 并且不是成功，则直接抛出异常<br>
 * 如果需要对异常进行处理，则进行捕获<br>
 * 
 * @see FeignAspect#around
 * 
 * @author cc 2019年10月1日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeignAnnotation {
}
