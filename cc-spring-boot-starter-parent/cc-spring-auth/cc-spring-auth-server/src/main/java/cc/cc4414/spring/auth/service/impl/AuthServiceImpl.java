package cc.cc4414.spring.auth.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.auth.service.IAuthService;
import cc.cc4414.spring.resource.core.CommonUser;
import cc.cc4414.spring.resource.core.ResourceConsts;
import cc.cc4414.spring.resource.util.UserUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证服务服务实现类
 * 
 * @author cc 2019年11月24日
 */
@Service
@ConditionalOnMissingBean(IAuthService.class)
@Slf4j
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public void logout() {
		CommonUser user = UserUtils.getUser();
		String id = user.getId();
		log.debug("用户{}退出登录", id);
		redisTemplate.opsForValue().set(ResourceConsts.EXPIRES + id, String.valueOf(System.currentTimeMillis()), 30L,
				TimeUnit.DAYS);
	}
}
