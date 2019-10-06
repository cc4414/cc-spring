package cc.cc4414.spring.common.result;

import java.io.Serializable;

import lombok.Data;

/**
 * 返回给接口调用者的结果<br>
 * <br>
 * 通常在一个项目中，给别人提供的接口都会返回统一的报文格式<br>
 * 这个类就规定了该报文格式，方便统一返回结果<br>
 * 通常不会在代码中直接使用该类，而是抛出 ResultException 异常<br>
 * 然后在需要处理的地方捕获该异常，如果是web项目则交给统一异常处理<br>
 * 成功的返回值也可以统一处理<br>
 * 
 * @author cc 2019年10月1日
 */
@Data
public class ResultVO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 错误码. */
	private String code;

	/** 提示信息. */
	private String message;

	/** 具体内容. */
	private T data;

	public ResultVO(T data) {
		this();
		setData(data);
	}

	public ResultVO() {
		this(CommonResultEnum.SUCCESS);
	}

	public ResultVO(ResultEnum resultEnum) {
		this(resultEnum.getCode(), resultEnum.getMessage());
	}

	public ResultVO(ResultException e) {
		this(e.getCode(), e.getMessage());
	}

	public ResultVO(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public boolean isSuccess() {
		return CommonResultEnum.SUCCESS.equalsCode(this.code);
	}

}
