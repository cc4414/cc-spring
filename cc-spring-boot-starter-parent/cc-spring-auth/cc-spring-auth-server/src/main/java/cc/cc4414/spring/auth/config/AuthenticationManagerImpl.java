package cc.cc4414.spring.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.auth.service.IAuthenticationChecksService;
import lombok.RequiredArgsConstructor;

/**
 * 认证管理
 * 
 * @author cc 2019年11月24日
 */
@Service
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

	private final UserDetailsService userDetailsService;

	private final IAuthenticationChecksService iAuthenticationChecksService;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		CcDaoAuthenticationProvider ccDaoAuthenticationProvider = new CcDaoAuthenticationProvider();
		// userDetailsService必须设置
		ccDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
		// 设置自定义认证逻辑
		ccDaoAuthenticationProvider.setIAuthenticationChecksService(iAuthenticationChecksService);
		return ccDaoAuthenticationProvider;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 使用自定义AuthenticationProvider认证
		return authenticationProvider.authenticate(authentication);
	}

}
