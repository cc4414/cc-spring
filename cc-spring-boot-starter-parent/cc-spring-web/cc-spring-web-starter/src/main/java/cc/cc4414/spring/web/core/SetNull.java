package cc.cc4414.spring.web.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将指定入参或出参字段设置为null
 * 
 * @see cc.cc4414.spring.web.core.ControllerAspect#setNull
 * @see cc.cc4414.spring.web.core.ControllerAspect#parameterSetNull
 * @see cc.cc4414.spring.web.core.ControllerAspect#returnSetNull
 * 
 * @author cc 2019年10月1日
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SetNull {
	Class<?>[] value() default {};
}
