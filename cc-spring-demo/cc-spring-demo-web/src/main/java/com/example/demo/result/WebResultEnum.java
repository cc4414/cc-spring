package com.example.demo.result;

import cc.cc4414.spring.common.result.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebResultEnum implements ResultEnum {
	/** xxx错误 */
	XXX_ERROR("100101", "xxx错误"),

	;

	/** 错误码. */
	private String code;

	/** 提示信息. */
	private String message;
}