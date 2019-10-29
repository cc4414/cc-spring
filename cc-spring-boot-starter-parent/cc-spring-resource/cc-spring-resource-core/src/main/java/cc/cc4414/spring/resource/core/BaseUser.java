package cc.cc4414.spring.resource.core;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 基础用户，一些用户的公共字段，需要用到这些字段的用户类直接继承该类
 * 
 * @author cc 2019年10月21日
 */
@Data
@Accessors(chain = true)
public class BaseUser implements Serializable {

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

}
