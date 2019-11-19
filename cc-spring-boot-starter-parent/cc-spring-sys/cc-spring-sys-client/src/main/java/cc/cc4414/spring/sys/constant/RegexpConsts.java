package cc.cc4414.spring.sys.constant;

/**
 * 正则表达式
 * 
 * @author cc 2019年11月11日
 */
public interface RegexpConsts {

	/** 用户名称：长度为2到20位的中文、字母、数字、下划线 */
	String USER_NAME = "^[\\w\\u4e00-\\u9fa5]{2,20}$";

	/** 用户名：长度为2到20位的中文、字母、数字、下划线，并且不以数字开头 */
	String USERNAME = "^(?!\\d)[\\w\\u4e00-\\u9fa5]{2,20}$";

	/** 密码：长度为6到16位的字母、数字、下划线 */
	String PASSWORD = "^\\w{6,16}$";

	/** 手机号：首位为1、第二位为3-9的11位数字 */
	String MOBILE = "^1[3-9]\\d{9}$";

	/** 邮箱：字母数字下划线@字母数字下划线(.字母数字下划线)+ */
	String EMAIL = "^\\w+@\\w+(\\.\\w+)+$";

	/** 角色名称：长度为2到20位的中文、字母、数字、下划线 */
	String ROLE_NAME = "^[\\w\\u4e00-\\u9fa5]{2,20}$";

	/** 权限代码：长度为1到64位的字母、数字、下划线 */
	String AUTHORITY_CODE = "^\\w{1,64}$";

	/** 权限名称：长度为2到20位的中文、字母、数字、下划线 */
	String AUTHORITY_NAME = "^[\\w\\u4e00-\\u9fa5]{2,20}$";

	/** 部门名称：长度为2到20位的中文、字母、数字、下划线 */
	String DEPT_NAME = "^[\\w\\u4e00-\\u9fa5]{2,20}$";

	/** 租户名称：长度为2到20位的中文、字母、数字、下划线 */
	String TENANT_NAME = "^[\\w\\u4e00-\\u9fa5]{2,20}$";

	/** 用户名或手机号 */
	String USERNAME_OR_MOBILE = "^((?!\\d)[\\w\\u4e00-\\u9fa5]{2,20}|1[3-9]\\d{9})$";

}
