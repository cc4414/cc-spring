package cc.cc4414.spring.auth.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import cc.cc4414.spring.auth.constant.AuthConsts;
import lombok.RequiredArgsConstructor;

/**
 * 认证服务器配置
 * 
 * @author cc 2019年11月24日
 */
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final JwtAccessTokenConverter jwtAccessTokenConverter;

	private final TokenEnhancer tokenEnhancer;

	private final TokenStore tokenStore;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 经过认证(client:secret)可访问/oauth/check_token接口获取用户信息
		security.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				// 内存中保存客户端用户，用户名为client，密码为secret（前端调用，用户名和密码公开）
				.inMemory().withClient(AuthConsts.CLIENT).secret(AuthConsts.SECRET)
				// 支持刷新token和密码模式，范围为all
				.authorizedGrantTypes(AuthConsts.REFRESH_TOKEN, AuthConsts.PASSWORD).scopes(AuthConsts.ALL);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		// tokenEnhancer配置了jwt的额外信息，jwtAccessTokenConverter配置了jwt的密钥
		enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter));
		endpoints
				// authenticationManager配置了认证管理（密码模式必须配置）
				.authenticationManager(authenticationManager)
				// userDetailsService配置了用户详情获取的方式（刷新token时必须要有，获取token时可以没有）
				.userDetailsService(userDetailsService)
				// tokenStore配置了token存储方式为jwt，如果不配置，默认存在内存中，虽然返回的是jwt
				.tokenStore(tokenStore)
				// enhancerChain配置了token生成的额外信息和jwt密钥
				.tokenEnhancer(enhancerChain);
	}
}
