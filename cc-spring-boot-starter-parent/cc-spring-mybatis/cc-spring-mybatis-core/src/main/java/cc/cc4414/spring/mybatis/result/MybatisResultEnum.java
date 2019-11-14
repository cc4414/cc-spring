package cc.cc4414.spring.mybatis.result;

import cc.cc4414.spring.common.result.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mybatis结果枚举类
 * 
 * @author cc 2019年11月12日
 */
@AllArgsConstructor
@Getter
public enum MybatisResultEnum implements ResultEnum {

	/** 创建实例错误 */
	NEW_INSTANCE_ERROR("101", "创建实例错误"),

	/** 数据不存在 */
	DATA_NOT_EXIST("102", "数据不存在"),

	/** 数据被禁用 */
	DATA_DISABLED("103", "数据被禁用"),

	;

	/** 错误码. */
	private String code;

	/** 提示信息. */
	private String message;

}
