package cc.cc4414.spring.resource.util;

import cc.cc4414.spring.resource.core.CommonUser;
import cc.cc4414.spring.resource.core.UserContextHolder;
import lombok.experimental.UtilityClass;

/**
 * 用户工具类
 * 
 * @author cc 2019年10月21日
 */
@UtilityClass
public class UserUtils {
	/**
	 * 获取当前用户
	 * 
	 * @return 当前用户
	 */
	public CommonUser getUser() {
		return UserContextHolder.getUser();
	}
}
