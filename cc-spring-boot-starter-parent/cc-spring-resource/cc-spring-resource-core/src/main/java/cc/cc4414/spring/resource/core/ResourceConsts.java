package cc.cc4414.spring.resource.core;

/**
 * 资源服务器常量
 * 
 * @author cc 2019年10月21日
 */
public interface ResourceConsts {
	/** 用于标识是否是内部请求的请求头字段名 */
	String INNER_HEADER = "Inner";

	/** 当请求头INNER_HEADER的值为INNER_HEADER_VALUE的时候，说明是内部请求 */
	String INNER_HEADER_VALUE = "1";

	/** 租户id请求头字段名 */
	String TENANT_ID = "Tenant-Id";

	/** token_type为bearer */
	String BEARER_TYPE = "bearer";

	/** 用户id */
	String ID = "id";

	/** jwt创建时间 */
	String IAT = "iat";

	/** redis的key前缀，权限相关 */
	String AUTH = "auth:";

	/** redis的key前缀，token过期 */
	String EXPIRES = AUTH + "expires:";
}
