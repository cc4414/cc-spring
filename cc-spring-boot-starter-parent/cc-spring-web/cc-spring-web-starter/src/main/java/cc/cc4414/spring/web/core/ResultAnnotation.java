package cc.cc4414.spring.web.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回统一报文格式 ResultVO 的时候不需要自己组装<br>
 * 只需要在controller方法上面加上这个注解就可以<br>
 * 对于成功的返回，会自动把你的返回结果放到 ResultVO 的 data 中<br>
 * 对于失败的返回，当然是方法内直接抛出异常，交给 ControllerResponseBodyAdvice 进行统一异常处理<br>
 * 
 * @see ControllerResponseBodyAdvice#beforeBodyWrite
 * 
 * @author cc 2019年10月1日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResultAnnotation {
}
