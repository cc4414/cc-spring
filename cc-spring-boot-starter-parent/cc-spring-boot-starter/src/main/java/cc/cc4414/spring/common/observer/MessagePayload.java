package cc.cc4414.spring.common.observer;

import lombok.Data;

/**
 * 观察者模式与发布订阅共用的消息体
 * 
 * @author cc 2019年10月1日
 */
@Data
public class MessagePayload<T> {
	/** 消息类型 */
	private String type;

	/** 消息体 */
	private T data;
}
