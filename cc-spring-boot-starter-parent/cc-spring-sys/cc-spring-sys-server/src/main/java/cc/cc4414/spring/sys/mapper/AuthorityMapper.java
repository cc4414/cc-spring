package cc.cc4414.spring.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.cc4414.spring.sys.entity.Authority;

/**
 * 权限表 Mapper 接口
 *
 * @author cc 2019年11月12日
 */
public interface AuthorityMapper extends BaseMapper<Authority> {

	/**
	 * 根据角色id列表查询拥有这些角色的权限列表
	 * 
	 * @param ids 角色id列表
	 * @return 权限列表
	 */
	List<Authority> listByRoleIds(List<String> ids);

	/**
	 * 通过用户id查找该用户所拥有的所有可用权限
	 * 
	 * @param userId 用户id
	 * @return 该用户所拥有的所有可用权限
	 */
	List<Authority> listByUserId(String userId);

}
