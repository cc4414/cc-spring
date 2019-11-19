package cc.cc4414.spring.sys.result;

import cc.cc4414.spring.common.result.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统管理结果枚举类
 * 
 * @author cc 2019年11月11日
 */
@AllArgsConstructor
@Getter
public enum SysResultEnum implements ResultEnum {

	/** 用户不存在 */
	USER_NOT_EXIST("10101", "用户不存在"),

	/** 用户已存在 */
	USER_EXISTED("10102", "用户已存在"),

	/** 用户被禁用 */
	USER_DISABLED("10103", "用户被禁用"),

	/** 用户名称已存在 */
	USER_NAME_EXISTED("10104", "用户名称已存在"),

	/** 用户名已存在 */
	USERNAME_EXISTED("10105", "用户名已存在"),

	/** 密码错误 */
	PASSWORD_ERROR("10106", "密码错误"),

	/** 角色不存在 */
	ROLE_NOT_EXIST("10201", "角色不存在"),

	/** 角色已存在 */
	ROLE_EXISTED("10202", "角色已存在"),

	/** 角色被禁用 */
	ROLE_DISABLED("10203", "角色被禁用"),

	/** 角色名称已存在 */
	ROLE_NAME_EXISTED("10204", "角色名称已存在"),

	/** 部门不存在 */
	DEPT_NOT_EXIST("10301", "部门不存在"),

	/** 部门已存在 */
	DEPT_EXISTED("10302", "部门已存在"),

	/** 部门被禁用 */
	DEPT_DISABLED("10303", "部门被禁用"),

	/** 部门名称已存在 */
	DEPT_NAME_EXISTED("10304", "部门名称已存在"),

	/** 权限不存在 */
	AUTHORITY_NOT_EXIST("10401", "权限不存在"),

	/** 权限已存在 */
	AUTHORITY_EXISTED("10402", "权限已存在"),

	/** 权限被禁用 */
	AUTHORITY_DISABLED("10403", "权限被禁用"),

	/** 权限名称已存在 */
	AUTHORITY_NAME_EXISTED("10404", "权限名称已存在"),

	/** 权限代码已存在 */
	AUTHORITY_CODE_EXISTED("10405", "权限代码已存在"),

	/** 权限接口地址已存在 */
	AUTHORITY_URL_EXISTED("10406", "权限接口地址已存在"),

	/** 权限类型不存在 */
	AUTHORITY_TYPE_NOT_EXIST("10407", "权限类型不存在"),

	/** 租户不存在 */
	TENANT_NOT_EXIST("10501", "租户不存在"),

	/** 租户已存在 */
	TENANT_EXISTED("10502", "租户已存在"),

	/** 租户被禁用 */
	TENANT_DISABLED("10503", "租户被禁用"),

	/** 租户名称已存在 */
	TENANT_NAME_EXISTED("10504", "租户名称已存在"),

	/** 字典不存在 */
	DICT_NOT_EXIST("10601", "字典不存在"),

	/** 字典已存在 */
	DICT_EXISTED("10602", "字典已存在"),

	/** 字典被禁用 */
	DICT_DISABLED("10603", "字典被禁用"),

	/** 字典名称已存在 */
	DICT_NAME_EXISTED("10604", "字典名称已存在"),

	/** 字典代码已存在 */
	DICT_CODE_EXISTED("10605", "字典代码已存在"),

	/** 字典项不存在 */
	DICT_ITEM_NOT_EXIST("10701", "字典项不存在"),

	/** 字典项已存在 */
	DICT_ITEM_EXISTED("10702", "字典项已存在"),

	/** 字典项被禁用 */
	DICT_ITEM_DISABLED("10703", "字典项被禁用"),

	/** 不能让自己的子节点成为父节点 */
	TREE_MOVE_ERROR("10801", "不能让自己的子节点成为父节点"),

	;

	/** 错误码. */
	private String code;

	/** 提示信息. */
	private String message;

}
