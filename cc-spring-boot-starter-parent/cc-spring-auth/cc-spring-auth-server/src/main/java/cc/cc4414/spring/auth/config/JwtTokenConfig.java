package cc.cc4414.spring.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import cc.cc4414.spring.auth.service.IJwtAdditionalInformationService;
import cc.cc4414.spring.resource.core.ResourceConsts;
import lombok.RequiredArgsConstructor;

/**
 * JWT配置
 * 
 * @author cc 2019年11月24日
 */
@Configuration
@RequiredArgsConstructor
public class JwtTokenConfig {
	private final IJwtAdditionalInformationService iJwtAdditionalInformationService;

	@Bean
	public TokenEnhancer tokenEnhancer() {
		// jwt额外信息的配置
		return (accessToken, authentication) -> {
			Map<String, Object> info = new HashMap<>(1);
			// jwt的生成时间
			info.put(ResourceConsts.IAT, System.currentTimeMillis());
			// 自定义的其他额外信息
			Map<String, Object> map = iJwtAdditionalInformationService.additionalInformation(accessToken,
					authentication);
			info.putAll(map);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
			return accessToken;
		};
	}
}
