package cc.cc4414.spring.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.cc4414.spring.sys.entity.Role;

/**
 * 角色表 Mapper 接口
 *
 * @author cc 2019年11月12日
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据权限id列表查询每个权限中的角色列表
	 * 
	 * @param ids 权限id列表
	 * @return 角色列表
	 */
	List<Role> listByAuthorityIds(List<String> ids);

	/**
	 * 根据用户id列表查询每个用户中的角色列表
	 * 
	 * @param ids 用户id列表
	 * @return 角色列表
	 */
	List<Role> listByUserIds(List<String> ids);

}
