package cc.cc4414.spring.auth.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.auth.model.CcUserDetails;
import cc.cc4414.spring.auth.service.ISysUserService;
import lombok.RequiredArgsConstructor;

/**
 * UserDetailsService的默认实现类
 * 
 * @author cc 2019年11月24日
 */
@Service
@ConditionalOnMissingBean(UserDetailsService.class)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final ISysUserService iSysUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CcUserDetails user = iSysUserService.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在！");
		}
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		List<String> authorityList = iSysUserService.listAuthorityByUserId(user.getId());
		if (authorityList != null) {
			authorityList.forEach(i -> {
				authorities.add(new SimpleGrantedAuthority(i));
			});
		}
		user.setAuthorities(authorities);
		return user;
	}

}
