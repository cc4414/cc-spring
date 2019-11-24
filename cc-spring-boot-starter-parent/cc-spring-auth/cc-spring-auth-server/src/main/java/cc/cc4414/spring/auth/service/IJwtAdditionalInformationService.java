package cc.cc4414.spring.auth.service;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * jwt额外信息服务类
 * 
 * @author cc 2019年11月24日
 */
public interface IJwtAdditionalInformationService {
	/**
	 * 在jwt中添加额外信息
	 * 
	 * @param accessToken    token
	 * @param authentication 这里面可以拿到用户信息
	 * @return 需要添加的额外信息
	 */
	Map<String, Object> additionalInformation(OAuth2AccessToken accessToken, OAuth2Authentication authentication);
}
