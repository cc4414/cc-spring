package cc.cc4414.spring.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.cc4414.spring.sys.entity.User;

/**
 * 用户表 Mapper 接口
 *
 * @author cc 2019年11月12日
 */
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 根据角色id列表查询拥有这些角色的用户列表
	 * 
	 * @param ids 角色id列表
	 * @return 用户列表
	 */
	List<User> listByRoleIds(List<String> ids);

	/**
	 * 根据部门id列表查询拥有这些部门的用户列表
	 * 
	 * @param ids 部门id列表
	 * @return 用户列表
	 */
	List<User> listByDeptIds(List<String> ids);
}
