package cc.cc4414.spring.auth.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import cc.cc4414.spring.auth.service.IAuthenticationChecksService;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * spring-security的认证逻辑在AuthenticationProvider中的additionalAuthenticationChecks方法<br>
 * 这里为了方便自定义认证逻辑，而又不需要去关心spring security那复杂的配置方式，所以提供了这个类<br>
 * 需要自定义认证逻辑的时候只需要实现IAuthenticationChecksService即可<br>
 * 
 * @author cc 2019年11月24日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CcDaoAuthenticationProvider extends DaoAuthenticationProvider {
	private IAuthenticationChecksService iAuthenticationChecksService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// 使用自定义的认证逻辑校验
		iAuthenticationChecksService.additionalAuthenticationChecks(userDetails, authentication);
	}
}
