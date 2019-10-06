package cc.cc4414.spring.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公共结果枚举类
 * 
 * @author cc 2019年10月1日
 */
@AllArgsConstructor
@Getter
public enum CommonResultEnum implements ResultEnum {

	/** 成功 */
	SUCCESS("0", "成功"),

	/** 未知错误 */
	UNKNOWN_ERROR("1", "未知错误"),

	/** 参数错误 */
	PARAM_ERROR("2", "参数错误"),

	/** 用户未登陆 */
	NOT_LOGGED("3", "用户未登陆"),

	/** 接口调用异常 */
	REST_ERROR("4", "接口调用异常"),

	/** 获取锁失败 */
	OBTAIN_LOCK_FAILED("5", "获取锁失败"),

	/** 数据已存在 */
	DUPLICATE_ERROR("6", "数据已存在"),

	;

	/** 错误码. */
	private String code;

	/** 提示信息. */
	private String message;

}
