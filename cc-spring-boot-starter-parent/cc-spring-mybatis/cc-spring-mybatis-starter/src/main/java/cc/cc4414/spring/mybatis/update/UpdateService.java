package cc.cc4414.spring.mybatis.update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import cc.cc4414.spring.common.notice.NoticeAnnotation;
import cc.cc4414.spring.common.observer.MessagePayload;
import cc.cc4414.spring.common.observer.MessageUtils;

/**
 * 冗余字段更新发送消息
 * 
 * @author cc 2019年11月3日
 */
@Service
public class UpdateService {
	@Autowired
	private UpdateObservable updateObservable;

	/**
	 * 发送字段更新的消息，这里用了异步和异常通知
	 * 
	 * @param func 方法引用
	 * @param t    更新后的实体
	 */
	@Async
	@NoticeAnnotation
	public <T> void update(SFunction<T, ?> func, T t) {
		update(func, t, new ArrayList<>());
	}

	/**
	 * 发送字段更新的消息，这里用了异步和异常通知
	 * 
	 * @param func    方法引用
	 * @param t       更新后的实体
	 * @param channel 消息频道或者被观察者
	 */
	@Async
	@NoticeAnnotation
	public <T> void update(SFunction<T, ?> func, T t, MessageChannel channel) {
		update(func, t, Arrays.asList(channel));
	}

	/**
	 * 发送字段更新的消息，这里用了异步和异常通知
	 * 
	 * @param func     方法引用
	 * @param t        更新后的实体
	 * @param channels 消息频道或者被观察者列表，默认会使用一个冗余字段更新的被观察者
	 */
	@Async
	@NoticeAnnotation
	public <T> void update(SFunction<T, ?> func, T t, List<MessageChannel> channels) {
		MessagePayload<UpdateInfo<T>> messagePayload = new MessagePayload<>();
		messagePayload.setType("redundancyFieldUpdate");
		UpdateInfo<T> data = UpdateUtils.getUpdateInfo(func);
		data.setData(t);
		messagePayload.setData(data);
		// 默认冗余字段更新的被观察者发送消息
		MessageUtils.send(updateObservable, messagePayload);
		// 每一个其他的被观察者都发送消息
		channels.forEach(i -> MessageUtils.send(i, messagePayload));
	}
}
