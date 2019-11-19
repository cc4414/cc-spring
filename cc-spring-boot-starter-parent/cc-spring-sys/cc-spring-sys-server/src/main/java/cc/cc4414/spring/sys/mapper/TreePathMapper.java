package cc.cc4414.spring.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.cc4414.spring.sys.entity.TreePath;

/**
 * 闭包表 Mapper 接口
 *
 * @author cc 2019年11月12日
 */
public interface TreePathMapper extends BaseMapper<TreePath> {

	/**
	 * 根据范围查找所有根节点
	 * 
	 * @param scope 范围
	 * @return 根节点id列表
	 */
	List<String> listRootNodesByScope(String scope);

}
