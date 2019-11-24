package cc.cc4414.spring.auth.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.auth.service.IAuthenticationChecksService;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证校验默认实现类<br>
 * 复制 DaoAuthenticationProvider 的方法<br>
 * 
 * @author cc 2019年11月24日
 */
@Service
@ConditionalOnMissingBean(IAuthenticationChecksService.class)
@Slf4j
public class AuthenticationChecksServiceImpl implements IAuthenticationChecksService {
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Override
	public void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() == null) {
			log.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (!PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(presentedPassword,
				userDetails.getPassword())) {
			log.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

}
