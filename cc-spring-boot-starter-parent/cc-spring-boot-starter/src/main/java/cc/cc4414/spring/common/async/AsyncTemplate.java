package cc.cc4414.spring.common.async;

import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 * 异步
 * 
 * @author cc 2019年12月21日
 */
@Service
public class AsyncTemplate {
	/**
	 * 异步
	 * 
	 * @param <T>      返回结果类型
	 * @param supplier 需要异步执行的代码块
	 * @return 代码块的返回结果，如果没有则需要返回null
	 */
	@Async
	public <T> Future<T> async(Supplier<T> supplier) {
		return AsyncResult.forValue(supplier.get());
	}
}
