package cc.cc4414.spring.auth.property;

/**
 * ISysUserService 默认实现枚举类
 * 
 * @author cc 2019年11月24日
 */
public enum SysUserServiceEnum {

	/** 默认用户，静态方法创建的用户 */
	DEFAULT,

	/** 当前应用通过sys模块获取用户 */
	LOCAL,

	/** 通过远端sys服务获取用户 */
	REMOTE,

	;

}
