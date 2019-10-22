package cc.cc4414.spring.resource.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 权限异常统一处理
 * 
 * @author cc 2019年10月21日
 */
@RestControllerAdvice
public class ControllerAdvice {

	/**
	 * 权限异常直接外抛，交给spring处理，返回http状态码401、403等
	 * 
	 * @param e 权限异常
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public void handlerHttpException(AccessDeniedException e) throws Exception {
		throw e;
	}

}
