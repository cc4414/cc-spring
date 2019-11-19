package cc.cc4414.spring.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.cc4414.spring.sys.entity.Dept;

/**
 * 部门表 Mapper 接口
 *
 * @author cc 2019年11月12日
 */
public interface DeptMapper extends BaseMapper<Dept> {
	/**
	 * 根据用户id列表查询每个用户中的部门列表
	 * 
	 * @param ids 用户id列表
	 * @return 部门列表
	 */
	List<Dept> listByUserIds(List<String> ids);
}
