package cc.cc4414.spring.auth.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.auth.model.CcUserDetails;
import cc.cc4414.spring.auth.service.ISysUserService;

/**
 * 用户服务通过静态方法获取的默认实现类
 * 
 * @author cc 2019年11月24日
 */
@Service
@ConditionalOnProperty(prefix = "cc-spring.auth", name = "sys-user-service", havingValue = "default", matchIfMissing = true)
@ConditionalOnMissingBean(ISysUserService.class)
public class SysUserServiceImpl implements ISysUserService {

	@Override
	public CcUserDetails getUserByUsername(String username) {
		String admin = "admin";
		if (admin.equals(username)) {
			return CcUserDetails.getAdmin();
		}
		String user = "user";
		if (user.equals(username)) {
			return CcUserDetails.getUser();
		}
		return null;
	}

	@Override
	public List<String> listAuthorityByUserId(String userId) {
		return Collections.singletonList(userId);
	}

}
