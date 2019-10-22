package cc.cc4414.spring.resource.core;

import com.alibaba.ttl.TransmittableThreadLocal;

import lombok.experimental.UtilityClass;

/**
 * 用户线程变量工具类
 * 
 * @author cc 2019年10月21日
 */
@UtilityClass
public class UserContextHolder {
	private final ThreadLocal<CommonUser> USER = new TransmittableThreadLocal<>();

	/**
	 * 设置当前线程的用户
	 * 
	 * @param user 用户
	 */
	public void setUser(CommonUser user) {
		USER.set(user);
	}

	/**
	 * 获取当前线程的用户。<br>
	 * 如果当前线程没有用户，则返回“系统”
	 * 
	 * @return 用户
	 */
	public CommonUser getUser() {
		CommonUser commonUser = USER.get();
		if (commonUser == null) {
			commonUser = CommonUser.getSys();
			USER.set(commonUser);
		}
		return commonUser;
	}

	/**
	 * 清除
	 */
	public void clear() {
		USER.remove();
	}
}
