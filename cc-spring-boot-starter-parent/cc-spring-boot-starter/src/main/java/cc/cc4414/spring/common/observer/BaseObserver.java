package cc.cc4414.spring.common.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 * 
 * @author cc 2019年10月1日
 */
public interface BaseObserver extends Observer {

	/**
	 * 通常不需要知道被观察者这个对象<br>
	 * 所以比{@link #update}方法减少一个参数，方便使用<br>
	 * 
	 * @param arg 接收到的数据
	 */
	void receive(Object arg);

	/**
	 * 默认实现，不使用Observable参数
	 * 
	 * @param o   被观察者
	 * @param arg 接收到的数据
	 */
	@Override
	default void update(Observable o, Object arg) {
		receive(arg);
	}

}
