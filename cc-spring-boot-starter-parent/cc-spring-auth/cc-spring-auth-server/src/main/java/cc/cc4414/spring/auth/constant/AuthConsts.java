package cc.cc4414.spring.auth.constant;

/**
 * 认证服务器常量
 * 
 * @author cc 2019年11月24日
 */
public interface AuthConsts {
	/** 客户端用户名 */
	String CLIENT = "client";

	/** 客户端密码 */
	String SECRET = "{noop}secret";

	/** 授权类型 */
	String GRANT_TYPE = "grant_type";

	/** 刷新token */
	String REFRESH_TOKEN = "refresh_token";

	/** 密码模式 */
	String PASSWORD = "password";

	/** 范围全部 */
	String ALL = "all";
}
