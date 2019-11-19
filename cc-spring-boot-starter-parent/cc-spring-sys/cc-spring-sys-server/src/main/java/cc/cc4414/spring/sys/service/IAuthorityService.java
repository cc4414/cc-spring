package cc.cc4414.spring.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cc.cc4414.spring.mybatis.service.ICcService;
import cc.cc4414.spring.sys.entity.Authority;

/**
 * 权限表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface IAuthorityService extends ICcService<Authority> {
	/**
	 * 新增权限
	 * 
	 * @param name 名称
	 * @param code 权限代码
	 * @param url  接口地址
	 * @param type 类型
	 * @return 权限
	 */
	Authority add(String name, String code, String url, Integer type);

	/**
	 * 修改权限，参数为null时不修改对应项
	 * 
	 * @param id   权限id
	 * @param name 名称
	 * @param code 权限代码
	 * @param url  接口地址
	 * @param type 类型
	 */
	void update(String id, String name, String code, String url, Integer type);

	/**
	 * 添加角色到权限
	 * 
	 * @param id  权限id
	 * @param ids 角色id列表
	 */
	void addRole(String id, List<String> ids);

	/**
	 * 移除权限中的全部角色
	 * 
	 * @param id 权限id
	 */
	void deleteRole(String id);

	/**
	 * 移除权限中的角色
	 * 
	 * @param id  权限id
	 * @param ids 角色id列表
	 */
	void deleteRole(String id, List<String> ids);

	/**
	 * 全量更新权限中的角色
	 * 
	 * @param id  权限id
	 * @param ids 角色id列表
	 */
	void updateRole(String id, List<String> ids);

	/**
	 * 增量更新权限中的角色
	 * 
	 * @param id        权限id
	 * @param deleteIds 移除的角色id列表
	 * @param addIds    新增的角色id列表
	 */
	void updateRole(String id, List<String> deleteIds, List<String> addIds);

	/**
	 * 新增权限，参数为null时不新增对应项
	 * 
	 * @param name    名称
	 * @param code    权限代码
	 * @param url     接口地址
	 * @param type    类型
	 * @param roleIds 角色id列表
	 * @return 权限
	 */
	Authority add(String name, String code, String url, Integer type, List<String> roleIds);

	/**
	 * 修改权限，参数为null时不修改对应项
	 * 
	 * @param id      权限id
	 * @param name    名称
	 * @param code    权限代码
	 * @param url     接口地址
	 * @param type    类型
	 * @param roleIds 角色id列表
	 */
	void update(String id, String name, String code, String url, Integer type, List<String> roleIds);

	/**
	 * 根据id查询权限
	 * 
	 * @param id       权限id
	 * @param roleList 是否查询对应角色列表
	 * @return 权限
	 */
	Authority get(String id, boolean roleList);

	/**
	 * 查询所有权限
	 * 
	 * @param queryWrapper 实体对象封装操作类
	 * @param roleList     是否查询对应角色列表
	 * @return 权限列表
	 */
	List<Authority> list(Wrapper<Authority> queryWrapper, boolean roleList);

	/**
	 * 分页查询权限
	 * 
	 * @param page         分页对象
	 * @param queryWrapper 实体对象封装操作类
	 * @param roleList     是否查询对应角色列表
	 * @return 权限分页
	 */
	IPage<Authority> page(IPage<Authority> page, Wrapper<Authority> queryWrapper, boolean roleList);

	/**
	 * 根据角色id列表查询每个角色中的权限列表
	 * 
	 * @param ids 角色id列表
	 * @return key为角色id，value为对应角色id中的权限列表
	 */
	Map<String, List<Authority>> listMapByRoleIds(List<String> ids);

	/**
	 * 通过用户id查找该用户所拥有的所有可用权限
	 * 
	 * @param userId 用户id
	 * @return 该用户所拥有的所有可用权限
	 */
	List<Authority> listByUserId(String userId);
}
