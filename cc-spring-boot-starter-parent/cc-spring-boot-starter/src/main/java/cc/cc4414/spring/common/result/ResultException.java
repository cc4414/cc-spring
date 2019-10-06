package cc.cc4414.spring.common.result;

import lombok.Getter;

/**
 * 自定义业务异常<br>
 * 通常只需要使用{@link ResultException#ResultException(ResultEnum)} 方法<br>
 * 在大多数情况下，按照如下方法使用：<br>
 * 
 * <pre>
 * {@code
 * throw new ResultException(CommonResultEnum.UNKNOWN_ERROR);
 * }
 * </pre>
 * 
 * @author cc 2019年10月1日
 */
@Getter
public class ResultException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 错误码. */
	private String code;

	/**
	 * 通常需要抛出异常的时候使用 {@link ResultException#ResultException(ResultEnum)} 方法<br>
	 * 尽量先去定义一个 ResultEnum ，再抛出异常<br>
	 * 
	 * @param code    错误码
	 * @param message 提示信息
	 */
	public ResultException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * 通过 ResultEnum 构造
	 * 
	 * @param resultEnum 结果枚举
	 */
	public ResultException(ResultEnum resultEnum) {
		this(resultEnum.getCode(), resultEnum.getMessage());
	}

	/**
	 * 在调用接口拿到该类型的返回值时，如果需要继续外抛，则使用该方法<br>
	 * 该方法通常也不需要主动使用<br>
	 * 在 {@link cc.cc4414.spring.common.feign.FeignAspect#around} 中可以统一处理
	 * 
	 * @param resultVO 通用返回结果
	 */
	public ResultException(ResultVO<?> resultVO) {
		this(resultVO.getCode(), resultVO.getMessage());
	}

}
