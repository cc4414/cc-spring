package cc.cc4414.spring.common.observer;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import lombok.experimental.UtilityClass;

/**
 * 消息工具
 * 
 * @author cc 2019年10月1日
 */
@UtilityClass
public class MessageUtils {
	/**
	 * 发送消息
	 * 
	 * @param channel 消息频道或者被观察者
	 * @param object  消息体，通常为{@link MessagePayload}
	 * @return 是否发送成功
	 */
	public boolean send(MessageChannel channel, Object object) {
		return channel.send(MessageBuilder.withPayload(object).build());
	}
}
