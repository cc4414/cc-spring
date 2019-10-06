package cc.cc4414.spring.web.core;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import cc.cc4414.spring.common.notice.NoticeProxy;
import cc.cc4414.spring.common.result.CommonResultEnum;
import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.common.result.ResultVO;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常及返回结果统一处理，返回ResultVO
 * 
 * @author cc 2019年10月1日
 */
@RestControllerAdvice
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cc-spring.web", name = "enable-default-advice", havingValue = "true", matchIfMissing = true)
@Slf4j
public class ControllerResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	private final NoticeProxy noticeProxy;

	/**
	 * 未预期的异常<br>
	 * 如果抛出了该异常，则会发送通知<br>
	 * 
	 * @param e Exception
	 * @return data为e.getMessage()
	 */
	@ExceptionHandler(value = Exception.class)
	public ResultVO<String> handlerException(Exception e) {
		noticeProxy.notice(CommonResultEnum.UNKNOWN_ERROR.getMessage());
		log.error(e.getMessage(), e);
		ResultVO<String> resultVO = new ResultVO<>(CommonResultEnum.UNKNOWN_ERROR);
		resultVO.setData(e.getMessage());
		return resultVO;
	}

	/**
	 * http请求异常<br>
	 * 对于http请求异常，不做处理，交给spring处理<br>
	 * spring可以将http状态码更好的返回<br>
	 * 
	 * @param e Exception
	 * @throws Exception 抛出异常
	 */
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
			HttpMediaTypeNotAcceptableException.class, MissingPathVariableException.class,
			ServletRequestBindingException.class, ConversionNotSupportedException.class, TypeMismatchException.class,
			HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
			MissingServletRequestPartException.class, BindException.class, NoHandlerFoundException.class,
			AsyncRequestTimeoutException.class })
	public void handlerHttpException(Exception e) throws Exception {
		throw e;
	}

	/**
	 * 业务异常c 代码中遇到非正常情况的返回，不要自己手动组装 ResultVO <br>
	 * 直接抛出 ResultException 即可<br>
	 * 该方法会帮助你组装返回结果的<br>
	 * 
	 * @param e ResultException
	 * @return data为null
	 */
	@ExceptionHandler(value = ResultException.class)
	public ResultVO<Object> handlerException(ResultException e) {
		log.info("code: {}, message: {}", e.getCode(), e.getMessage());
		return new ResultVO<>(e);
	}

	/**
	 * 接口调用异常<br>
	 * http请求调用别人的时候发生的异常<br>
	 * 如连接超时、404、500等<br>
	 * 
	 * @param e RestClientException
	 * @return data为e.getMessage()
	 */
	@ExceptionHandler(value = RestClientException.class)
	public ResultVO<String> handlerException(RestClientException e) {
		log.error(e.getMessage(), e);
		ResultVO<String> resultVO = new ResultVO<>(CommonResultEnum.REST_ERROR);
		resultVO.setData(e.getMessage());
		return resultVO;
	}

	/**
	 * 主键或唯一键约束异常<br>
	 * 数据库来处理唯一约束会更加准确<br>
	 * 有唯一约束的时候尽量交给数据库来处理<br>
	 * 
	 * @param e DuplicateKeyException
	 * @return data为e.getMessage()
	 */
	@ExceptionHandler(value = DuplicateKeyException.class)
	public ResultVO<String> handlerException(DuplicateKeyException e) {
		log.error(e.getMessage(), e);
		ResultVO<String> resultVO = new ResultVO<>(CommonResultEnum.DUPLICATE_ERROR);
		resultVO.setData(e.getMessage());
		return resultVO;
	}

	/**
	 * 参数异常（url上参数校验异常）
	 * 
	 * @param e ConstraintViolationException
	 * @return data为e.getMessage()
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResultVO<String> handlerException(ConstraintViolationException e) {
		log.debug(e.getMessage());
		ResultVO<String> resultVO = new ResultVO<>(CommonResultEnum.PARAM_ERROR);
		resultVO.setData(e.getMessage());
		return resultVO;
	}

	/**
	 * 参数异常（请求体中参数校验异常）
	 * 
	 * @param e MethodArgumentNotValidException
	 * @return data为 参数: 错误信息
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResultVO<String> handlerException(MethodArgumentNotValidException e) {
		log.debug(e.getMessage());
		ResultVO<String> resultVO = new ResultVO<>(CommonResultEnum.PARAM_ERROR);
		FieldError fieldError = e.getBindingResult().getFieldError();
		resultVO.setData(fieldError.getField() + ": " + fieldError.getDefaultMessage());
		return resultVO;
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.hasMethodAnnotation(ResultAnnotation.class);
	}

	/**
	 * 返回结果统一处理<br>
	 * 对于加了 @ResultAnnotation 注解的方法<br>
	 * 将你的结果组装成 ResultVO 返回<br>
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		ResultVO<Object> resultVO = new ResultVO<>(body);
		if (body instanceof String) {
			return JSONUtil.toJsonStr(resultVO);
		}
		return resultVO;
	}

}
