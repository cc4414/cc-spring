package cc.cc4414.spring.common.notice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对于一个系统来说，总会有异常情况<br>
 * 而异常情况也总有一些是事先考虑不到，最终需要人工处理的<br>
 * 对于这些异常，就需要能够及时通知到开发者<br>
 * 在方法上增加此注解，如果抛出异常，就会按指定方式通知人工处理<br>
 * 
 * @see NoticeAspect#around
 * 
 * @author cc 2019年10月1日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoticeAnnotation {
}
