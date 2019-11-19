package cc.cc4414.spring.sys.service;

import cc.cc4414.spring.mybatis.service.ICcService;
import cc.cc4414.spring.sys.entity.Tenant;

/**
 * 租户表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface ITenantService extends ICcService<Tenant> {
	/**
	 * 新增租户
	 * 
	 * @param name 名称
	 * @return 租户
	 */
	Tenant add(String name);

	/**
	 * 修改租户名称
	 * 
	 * @param id   租户id
	 * @param name 名称
	 */
	void update(String id, String name);

	/**
	 * 新增租户，并添加一个管理员账号
	 * 
	 * @param name     名称
	 * @param password 管理员密码
	 * @return 租户
	 */
	Tenant add(String name, String password);
}
