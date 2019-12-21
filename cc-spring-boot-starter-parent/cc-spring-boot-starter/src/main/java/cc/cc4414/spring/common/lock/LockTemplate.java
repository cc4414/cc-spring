package cc.cc4414.spring.common.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.common.result.CommonResultEnum;
import cc.cc4414.spring.common.result.ResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 加锁
 * 
 * @author cc 2019年12月21日
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LockTemplate {
	private final LockRegistry lockRegistry;

	/**
	 * 加锁
	 * 
	 * @param <T>      返回结果类型
	 * @param supplier 需要被锁执行的代码块
	 * @param lockKey  与锁关联的字符串
	 * @return 代码块的返回结果，如果没有则需要返回null
	 */
	public <T> T lock(Supplier<T> supplier, String lockKey) {
		return lock(supplier, lockKey, 0L);
	}

	/**
	 * 加锁
	 * 
	 * @param <T>      返回结果类型
	 * @param supplier 需要被锁执行的代码块
	 * @param lockKey  与锁关联的字符串
	 * @param time     等待锁的最长时间
	 * @return 代码块的返回结果，如果没有则需要返回null
	 */
	public <T> T lock(Supplier<T> supplier, String lockKey, long time) {
		return lock(supplier, lockKey, time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 加锁
	 * 
	 * @param <T>      返回结果类型
	 * @param supplier 需要被锁执行的代码块
	 * @param lockKey  与锁关联的字符串
	 * @param time     等待锁的最长时间
	 * @param unit     时间单位
	 * @return 代码块的返回结果，如果没有则需要返回null
	 */
	public <T> T lock(Supplier<T> supplier, String lockKey, long time, TimeUnit unit) {
		Lock lock = lockRegistry.obtain(lockKey);
		boolean isLock;
		try {
			isLock = lock.tryLock(time, unit);
		} catch (InterruptedException e) {
			log.error("获取锁异常", e);
			throw new ResultException(CommonResultEnum.OBTAIN_LOCK_FAILED);
		}
		if (isLock) {
			try {
				return supplier.get();
			} finally {
				lock.unlock();
			}
		} else {
			throw new ResultException(CommonResultEnum.OBTAIN_LOCK_FAILED);
		}
	}
}
