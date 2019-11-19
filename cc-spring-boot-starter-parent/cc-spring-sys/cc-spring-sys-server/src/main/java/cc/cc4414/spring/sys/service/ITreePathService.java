package cc.cc4414.spring.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cc.cc4414.spring.sys.entity.TreePath;

/**
 * 闭包表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface ITreePathService extends IService<TreePath> {
	/**
	 * 在父节点parentId下增加一个子节点
	 * 
	 * @param id       节点id
	 * @param parentId 父节点id
	 * @param scope    范围
	 */
	void addNode(String id, String parentId, String scope);

	/**
	 * 删除节点及其所有子节点
	 * 
	 * @param id 节点id
	 * @return 删除的节点id列表
	 */
	List<String> deleteNode(String id);

	/**
	 * 移动子树
	 * 
	 * @param id       节点id
	 * @param parentId 移动到的父节点id
	 */
	void moveNode(String id, String parentId);

	/**
	 * 查询父节点id
	 * 
	 * @param id 节点id
	 * @return 父节点id
	 */
	String findParentNode(String id);

	/**
	 * 查询所有子节点id
	 * 
	 * @param id 节点id
	 * @return 子节点id列表
	 */
	List<String> findChildNodes(String id);

	/**
	 * 查询所有祖先节点id（包括自己）
	 * 
	 * @param id 节点id
	 * @return 祖先节点id列表
	 */
	List<String> findAncestorNodes(String id);

	/**
	 * 查询所有后代节点id（包括自己）
	 * 
	 * @param id 节点id
	 * @return 后代节点id列表
	 */
	List<String> findDescendantNodes(String id);

	/**
	 * 根据范围查找所有根节点
	 * 
	 * @param scope 范围
	 * @return 根节点id列表
	 */
	List<String> findRootNodes(String scope);
}
