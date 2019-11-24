package cc.cc4414.spring.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.auth.model.CcUserDetails;
import cc.cc4414.spring.auth.service.IJwtAdditionalInformationService;
import cn.hutool.core.bean.BeanUtil;

/**
 * jwt额外信息服务默认实现类
 * 
 * @author cc 2019年11月24日
 */
@Service
@ConditionalOnMissingBean(IJwtAdditionalInformationService.class)
public class JwtAdditionalInformationServiceImpl implements IJwtAdditionalInformationService {

	@Override
	public Map<String, Object> additionalInformation(OAuth2AccessToken accessToken,
			OAuth2Authentication authentication) {
		Map<String, Object> map = new HashMap<>(16);
		Object principal = authentication.getPrincipal();
		if (principal instanceof CcUserDetails) {
			// 将用户详情除了密码和权限以外，都添加至额外信息
			Map<String, Object> userMap = BeanUtil.beanToMap(principal);
			map.putAll(userMap);
			map.remove("password");
			map.remove("authorities");
		}
		return map;
	}

}
