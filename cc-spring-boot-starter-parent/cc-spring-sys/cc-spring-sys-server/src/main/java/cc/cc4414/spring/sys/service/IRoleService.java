package cc.cc4414.spring.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cc.cc4414.spring.mybatis.service.ICcService;
import cc.cc4414.spring.sys.entity.Role;

/**
 * 角色表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface IRoleService extends ICcService<Role> {
	/**
	 * 新增角色
	 * 
	 * @param name 名称
	 * @return 角色
	 */
	Role add(String name);

	/**
	 * 修改角色名称
	 * 
	 * @param id   角色id
	 * @param name 名称
	 */
	void update(String id, String name);

	/**
	 * 添加用户到角色
	 * 
	 * @param id  角色id
	 * @param ids 用户id列表
	 */
	void addUser(String id, List<String> ids);

	/**
	 * 移除角色中的全部用户
	 * 
	 * @param id 角色id
	 */
	void deleteUser(String id);

	/**
	 * 移除角色中的用户
	 * 
	 * @param id  角色id
	 * @param ids 用户id列表
	 */
	void deleteUser(String id, List<String> ids);

	/**
	 * 全量更新角色中的用户
	 * 
	 * @param id  角色id
	 * @param ids 用户id列表
	 */
	void updateUser(String id, List<String> ids);

	/**
	 * 增量更新角色中的用户
	 * 
	 * @param id        角色id
	 * @param deleteIds 移除的用户id列表
	 * @param addIds    新增的用户id列表
	 */
	void updateUser(String id, List<String> deleteIds, List<String> addIds);

	/**
	 * 添加权限到角色
	 * 
	 * @param id  角色id
	 * @param ids 权限id列表
	 */
	void addAuthority(String id, List<String> ids);

	/**
	 * 移除角色中的全部权限
	 * 
	 * @param id 角色id
	 */
	void deleteAuthority(String id);

	/**
	 * 移除角色中的权限
	 * 
	 * @param id  角色id
	 * @param ids 权限id列表
	 */
	void deleteAuthority(String id, List<String> ids);

	/**
	 * 全量更新角色中的权限
	 * 
	 * @param id  角色id
	 * @param ids 权限id列表
	 */
	void updateAuthority(String id, List<String> ids);

	/**
	 * 增量更新角色中的权限
	 * 
	 * @param id        角色id
	 * @param deleteIds 移除的权限id列表
	 * @param addIds    新增的权限id列表
	 */
	void updateAuthority(String id, List<String> deleteIds, List<String> addIds);

	/**
	 * 新增角色，参数为null时不新增对应项
	 * 
	 * @param name         名称
	 * @param userIds      用户id列表
	 * @param authorityIds 权限id列表
	 * @return 角色
	 */
	Role add(String name, List<String> userIds, List<String> authorityIds);

	/**
	 * 修改角色，参数为null时不修改对应项
	 * 
	 * @param id           角色id
	 * @param name         名称
	 * @param userIds      用户id列表
	 * @param authorityIds 权限id列表
	 */
	void update(String id, String name, List<String> userIds, List<String> authorityIds);

	/**
	 * 根据id查询角色
	 * 
	 * @param id            角色id
	 * @param userList      是否查询对应用户列表
	 * @param authorityList 是否查询对应权限列表
	 * @return 角色
	 */
	Role get(String id, boolean userList, boolean authorityList);

	/**
	 * 查询所有角色
	 * 
	 * @param queryWrapper  实体对象封装操作类
	 * @param userList      是否查询对应用户列表
	 * @param authorityList 是否查询对应权限列表
	 * @return 角色列表
	 */
	List<Role> list(Wrapper<Role> queryWrapper, boolean userList, boolean authorityList);

	/**
	 * 分页查询角色
	 * 
	 * @param page          分页对象
	 * @param queryWrapper  实体对象封装操作类
	 * @param userList      是否查询对应用户列表
	 * @param authorityList 是否查询对应权限列表
	 * @return 角色分页
	 */
	IPage<Role> page(IPage<Role> page, Wrapper<Role> queryWrapper, boolean userList, boolean authorityList);

	/**
	 * 根据权限id列表查询每个权限中的角色列表
	 * 
	 * @param ids 权限id列表
	 * @return key为权限id，value为对应权限id中的角色列表
	 */
	Map<String, List<Role>> listMapByAuthorityIds(List<String> ids);

	/**
	 * 根据用户id列表查询每个用户中的角色列表
	 * 
	 * @param ids 用户id列表
	 * @return key为用户id，value为对应用户id中的角色列表
	 */
	Map<String, List<Role>> listMapByUserIds(List<String> ids);

	/**
	 * 在当前租户下新增管理员角色，并分配一个管理员用户
	 * 
	 * @param id 用户id
	 * @return 角色
	 */
	Role addAdmin(String id);

}
