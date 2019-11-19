package cc.cc4414.spring.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cc.cc4414.spring.common.model.Tree;
import cc.cc4414.spring.mybatis.service.ICcService;
import cc.cc4414.spring.sys.entity.Dept;

/**
 * 部门表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface IDeptService extends ICcService<Dept> {

	/**
	 * 新增部门
	 * 
	 * @param name 名称
	 * @return 部门
	 */
	Dept add(String name);

	/**
	 * 修改部门名称
	 * 
	 * @param id   部门id
	 * @param name 名称
	 */
	void update(String id, String name);

	/**
	 * 添加用户到部门
	 * 
	 * @param id  部门id
	 * @param ids 用户id列表
	 */
	void addUser(String id, List<String> ids);

	/**
	 * 移除部门中的全部用户
	 * 
	 * @param id 部门id
	 */
	void deleteUser(String id);

	/**
	 * 移除部门中的用户
	 * 
	 * @param id  部门id
	 * @param ids 用户id列表
	 */
	void deleteUser(String id, List<String> ids);

	/**
	 * 全量更新部门中的用户
	 * 
	 * @param id  部门id
	 * @param ids 用户id列表
	 */
	void updateUser(String id, List<String> ids);

	/**
	 * 增量更新部门中的用户
	 * 
	 * @param id        部门id
	 * @param deleteIds 移除的用户id列表
	 * @param addIds    新增的用户id列表
	 */
	void updateUser(String id, List<String> deleteIds, List<String> addIds);

	/**
	 * 新增部门，参数为null时不新增对应项
	 * 
	 * @param name     名称
	 * @param parentId 父节点id
	 * @param userIds  用户id列表
	 * @return 部门
	 */
	Dept add(String name, String parentId, List<String> userIds);

	/**
	 * 修改部门，参数为null时不修改对应项
	 * 
	 * @param id       部门id
	 * @param name     名称
	 * @param parentId 父节点id
	 * @param userIds  用户id列表
	 */
	void update(String id, String name, String parentId, List<String> userIds);

	/**
	 * 根据id查询部门
	 * 
	 * @param id       部门id
	 * @param userList 是否查询对应用户列表
	 * @return 部门
	 */
	Dept get(String id, boolean userList);

	/**
	 * 查询所有部门
	 * 
	 * @param queryWrapper 实体对象封装操作类
	 * @param userList     是否查询对应用户列表
	 * @return 部门列表
	 */
	List<Dept> list(Wrapper<Dept> queryWrapper, boolean userList);

	/**
	 * 分页查询部门
	 * 
	 * @param page         分页对象
	 * @param queryWrapper 实体对象封装操作类
	 * @param userList     是否查询对应用户列表
	 * @return 部门分页
	 */
	IPage<Dept> page(IPage<Dept> page, Wrapper<Dept> queryWrapper, boolean userList);

	/**
	 * 根据用户id列表查询每个用户中的部门列表
	 * 
	 * @param ids 用户id列表
	 * @return key为用户id，value为对应用户id中的部门列表
	 */
	Map<String, List<Dept>> listMapByUserIds(List<String> ids);

	/**
	 * 通过id获取部门名称
	 * 
	 * @param id 部门id
	 * @return 部门名称
	 */
	String getNameById(String id);

	/**
	 * 在父节点parentId下新增子部门
	 * 
	 * @param id       部门id
	 * @param parentId 父节点id
	 */
	void addNode(String id, String parentId);

	/**
	 * 移动部门到父节点parentId
	 * 
	 * @param id       部门id
	 * @param parentId 父节点id
	 */
	void moveNode(String id, String parentId);

	/**
	 * 查询所有根节点部门
	 * 
	 * @return 根节点部门列表
	 */
	List<Dept> listRootDeptNodes();

	/**
	 * 查询所有子部门
	 * 
	 * @param id 部门id
	 * @return 子部门列表
	 */
	List<Dept> listChildDeptNodes(String id);

	/**
	 * 查询id下的部门树，若id为"0"，则查询完整的部门树
	 * 
	 * @param id 部门id
	 * @return 部门树
	 */
	List<Tree<Dept>> treeDeptNodes(String id);

}
