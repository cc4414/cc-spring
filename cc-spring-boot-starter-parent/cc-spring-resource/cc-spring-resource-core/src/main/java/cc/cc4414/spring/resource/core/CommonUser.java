package cc.cc4414.spring.resource.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前用户
 * 
 * @author cc 2019年10月21日
 */
@Data
@Accessors(chain = true)
public class CommonUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 名称 */
	private String name;

	/** 部门id */
	private String deptId;

	/** 部门名称 */
	private String deptName;

	/** 逻辑删除：0为未删除，1为删除 */
	private Integer deleted;

	/** 禁用：0为未禁用，1为禁用 */
	private Integer disabled;

	/** 租户id */
	private String tenantId;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 用户类型：0为普通用户，1为第三方应用 */
	private Integer type;

	/** 部门id列表 */
	private List<String> deptIds;

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
		return sys;
	}

}
