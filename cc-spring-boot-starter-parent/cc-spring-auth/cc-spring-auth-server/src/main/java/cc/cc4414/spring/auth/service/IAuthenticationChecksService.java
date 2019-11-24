package cc.cc4414.spring.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 认证校验
 * 
 * @author cc 2019年11月24日
 */
public interface IAuthenticationChecksService {
	/**
	 * 认证校验<br>
	 * 需要自定义登录认证逻辑的时候，可以实现该接口，并加上@Service注解
	 * 
	 * @param userDetails    通过UserDetailsService获取到的用户详情
	 * @param authentication 登录认证时传入的所有参数
	 */
	void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication);
}
