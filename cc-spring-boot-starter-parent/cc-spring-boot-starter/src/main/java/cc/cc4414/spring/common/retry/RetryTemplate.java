package cc.cc4414.spring.common.retry;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 重试
 * 
 * @author cc 2019年12月21日
 */
@Service
@Slf4j
public class RetryTemplate {
	/**
	 * @param <T>      返回结果类型
	 * @param supplier 需要重试执行的代码块
	 * @param retries  重试次数
	 * @return 代码块的返回结果，如果没有则需要返回null
	 */
	public <T> T retry(Supplier<T> supplier, int retries) {
		return retry(supplier, retries, 1000L);
	}

	/**
	 * @param <T>      返回结果类型
	 * @param supplier 需要重试执行的代码块
	 * @param retries  重试次数
	 * @param interval 重试基础时间间隔
	 * @return 代码块的返回结果，如果没有则需要返回null
	 */
	public <T> T retry(Supplier<T> supplier, int retries, long interval) {
		T result = null;
		for (int i = 0; i <= retries; i++) {
			try {
				result = supplier.get();
				break;
			} catch (Exception e) {
				log.error("方法执行失败第{}次", i + 1);
				if (i == retries) {
					throw e;
				}
				try {
					Thread.sleep(interval << i);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
					throw new RuntimeException(ie);
				}
			}
		}
		return result;
	}
}
