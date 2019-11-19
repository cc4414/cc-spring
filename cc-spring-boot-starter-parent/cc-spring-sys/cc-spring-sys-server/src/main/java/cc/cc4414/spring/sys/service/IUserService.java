package cc.cc4414.spring.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cc.cc4414.spring.mybatis.service.ICcService;
import cc.cc4414.spring.sys.entity.User;

/**
 * 用户表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface IUserService extends ICcService<User> {
	/**
	 * 新增用户
	 * 
	 * @param name     名称
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户
	 */
	User add(String name, String username, String password);

	/**
	 * 修改用户，参数为null时不修改对应项
	 * 
	 * @param id       用户id
	 * @param name     名称
	 * @param password 密码
	 */
	void update(String id, String name, String password);

	/**
	 * 添加角色到用户
	 * 
	 * @param id  用户id
	 * @param ids 角色id列表
	 */
	void addRole(String id, List<String> ids);

	/**
	 * 移除用户中的全部角色
	 * 
	 * @param id 用户id
	 */
	void deleteRole(String id);

	/**
	 * 移除用户中的角色
	 * 
	 * @param id  用户id
	 * @param ids 角色id列表
	 */
	void deleteRole(String id, List<String> ids);

	/**
	 * 全量更新用户中的角色
	 * 
	 * @param id  用户id
	 * @param ids 角色id列表
	 */
	void updateRole(String id, List<String> ids);

	/**
	 * 增量更新用户中的角色
	 * 
	 * @param id        用户id
	 * @param deleteIds 移除的角色id列表
	 * @param addIds    新增的角色id列表
	 */
	void updateRole(String id, List<String> deleteIds, List<String> addIds);

	/**
	 * 添加部门到用户
	 * 
	 * @param id  用户id
	 * @param ids 部门id列表
	 */
	void addDept(String id, List<String> ids);

	/**
	 * 移除用户中的全部部门
	 * 
	 * @param id 用户id
	 */
	void deleteDept(String id);

	/**
	 * 移除用户中的部门
	 * 
	 * @param id  用户id
	 * @param ids 部门id列表
	 */
	void deleteDept(String id, List<String> ids);

	/**
	 * 全量更新用户中的部门
	 * 
	 * @param id  用户id
	 * @param ids 部门id列表
	 */
	void updateDept(String id, List<String> ids);

	/**
	 * 增量更新用户中的部门
	 * 
	 * @param id        用户id
	 * @param deleteIds 移除的部门id列表
	 * @param addIds    新增的部门id列表
	 */
	void updateDept(String id, List<String> deleteIds, List<String> addIds);

	/**
	 * 新增用户，参数为null时不新增对应项
	 * 
	 * @param name     名称
	 * @param username 用户名
	 * @param password 密码
	 * @param roleIds  角色id列表
	 * @param deptIds  部门id列表
	 * @return 用户
	 */
	User add(String name, String username, String password, List<String> roleIds, List<String> deptIds);

	/**
	 * 修改用户，参数为null时不修改对应项
	 * 
	 * @param id       用户id
	 * @param name     名称
	 * @param password 密码
	 * @param roleIds  角色id列表
	 * @param deptIds  部门id列表
	 */
	void update(String id, String name, String password, List<String> roleIds, List<String> deptIds);

	/**
	 * 根据id查询用户
	 * 
	 * @param id       用户id
	 * @param roleList 是否查询对应角色列表
	 * @param deptList 是否查询对应部门列表
	 * @return 用户
	 */
	User get(String id, boolean roleList, boolean deptList);

	/**
	 * 查询所有用户
	 * 
	 * @param queryWrapper 实体对象封装操作类
	 * @param roleList     是否查询对应角色列表
	 * @param deptList     是否查询对应部门列表
	 * @return 用户列表
	 */
	List<User> list(Wrapper<User> queryWrapper, boolean roleList, boolean deptList);

	/**
	 * 分页查询用户
	 * 
	 * @param page         分页对象
	 * @param queryWrapper 实体对象封装操作类
	 * @param roleList     是否查询对应角色列表
	 * @param deptList     是否查询对应部门列表
	 * @return 用户分页
	 */
	IPage<User> page(IPage<User> page, Wrapper<User> queryWrapper, boolean roleList, boolean deptList);

	/**
	 * 根据角色id列表查询每个角色中的用户列表
	 * 
	 * @param ids 角色id列表
	 * @return key为角色id，value为对应角色id中的用户列表
	 */
	Map<String, List<User>> listMapByRoleIds(List<String> ids);

	/**
	 * 根据部门id列表查询每个部门中的用户列表
	 * 
	 * @param ids 部门id列表
	 * @return key为部门id，value为对应部门id中的用户列表
	 */
	Map<String, List<User>> listMapByDeptIds(List<String> ids);

	/**
	 * 修改密码
	 * 
	 * @param id          用户id
	 * @param password    新密码
	 * @param oldPassword 原密码
	 */
	void changePassword(String id, String password, String oldPassword);

}
