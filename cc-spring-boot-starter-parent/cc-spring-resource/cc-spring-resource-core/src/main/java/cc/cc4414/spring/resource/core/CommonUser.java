package cc.cc4414.spring.resource.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 当前用户
 * 
 * @author cc 2019年10月21日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonUser extends BaseUser {
	private static final long serialVersionUID = 1L;

	private Collection<String> authorities;

	/**
	 * 有些操作是系统自动执行的，如定时任务，这时候当前用户就是系统
	 * 
	 * @return 当前用户
	 */
	public static CommonUser getSys() {
		CommonUser sys = new CommonUser();
		sys.setId("0");
		sys.setName("系统");
		sys.setDeptId("0");
		sys.setDeptName("系统");
		sys.setDeleted(0);
		sys.setDisabled(0);
		sys.setTenantId("0");
		sys.setUsername("SYS");
		sys.setPassword("");
		sys.setType(-1);
		sys.setDeptIds(new ArrayList<>());
		List<String> authorities = new ArrayList<>();
		authorities.add("sys");
		sys.setAuthorities(authorities);
		return sys;
	}
}
