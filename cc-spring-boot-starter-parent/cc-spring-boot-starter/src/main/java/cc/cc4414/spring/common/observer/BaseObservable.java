package cc.cc4414.spring.common.observer;

import java.util.Observable;
import java.util.Observer;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * 被观察者，为了与发布订阅统一，所以实现了 MessageChannel 接口
 * 
 * @author cc 2019年10月1日
 */
public class BaseObservable extends Observable implements MessageChannel {
	@Override
	public boolean send(Message<?> message, long timeout) {
		setChanged();
		notifyObservers(message.getPayload());
		return false;
	}

	/**
	 * @see #addObserver(BaseObserver)
	 */
	@Override
	@Deprecated
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
	}

	/**
	 * 为了方便使用lambda表达式，所以新增此方法
	 * 
	 * @param o 观察者
	 */
	public synchronized void addObserver(BaseObserver o) {
		super.addObserver(o);
	}
}
