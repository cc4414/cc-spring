package cc.cc4414.spring.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.RequiredArgsConstructor;

/**
 * JWT配置
 * 
 * @author cc 2019年10月21日
 */
@Configuration
@RequiredArgsConstructor
public class JwtTokenConfig {
	private final ResourceProperties resourceProperties;

	@Bean
	public TokenStore tokenStore(JwtAccessTokenConverter tokenEnhancer) {
		return new JwtTokenStore(tokenEnhancer);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(resourceProperties.getSigningKey());
		return jwtAccessTokenConverter;
	}
}
