package cc.cc4414.spring.resource.core;

import com.alibaba.ttl.TransmittableThreadLocal;

import lombok.experimental.UtilityClass;

/**
 * token线程变量工具类
 * 
 * @author cc 2019年10月21日
 */
@UtilityClass
public class TokenContextHolder {
	private final ThreadLocal<String> TOKEN = new TransmittableThreadLocal<>();

	/**
	 * 设置当前线程的token
	 * 
	 * @param token 令牌
	 */
	public void setToken(String token) {
		TOKEN.set(token);
	}

	/**
	 * 获取当前线程的token
	 * 
	 * @return token
	 */
	public String getToken() {
		return TOKEN.get();
	}

	/**
	 * 清除
	 */
	public void clear() {
		TOKEN.remove();
	}
}
