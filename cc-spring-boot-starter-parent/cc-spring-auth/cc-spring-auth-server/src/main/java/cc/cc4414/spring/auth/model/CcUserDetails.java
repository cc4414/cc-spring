package cc.cc4414.spring.auth.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cc.cc4414.spring.resource.core.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户详细信息类
 * 
 * @author cc 2019年11月24日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CcUserDetails extends BaseUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static CcUserDetails getAdmin() {
		CcUserDetails user = new CcUserDetails();
		user.setId("admin");
		user.setName("管理员");
		user.setDeptId("0");
		user.setDeptName("系统");
		user.setDeleted(0);
		user.setDisabled(0);
		user.setTenantId("0");
		user.setUsername("admin");
		user.setPassword("{noop}admin");
		user.setType(0);
		user.setDeptIds(new ArrayList<>());
		return user;
	}

	public static CcUserDetails getUser() {
		CcUserDetails user = new CcUserDetails();
		user.setId("user");
		user.setName("普通用户");
		user.setDeptId("0");
		user.setDeptName("系统");
		user.setDeleted(0);
		user.setDisabled(0);
		user.setTenantId("0");
		user.setUsername("user");
		user.setPassword("{noop}user");
		user.setType(0);
		user.setDeptIds(new ArrayList<>());
		return user;
	}

}
