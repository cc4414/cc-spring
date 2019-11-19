package cc.cc4414.spring.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cc.cc4414.spring.common.lock.LockAnnotation;
import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.sys.constant.SysConsts;
import cc.cc4414.spring.sys.entity.TreePath;
import cc.cc4414.spring.sys.mapper.TreePathMapper;
import cc.cc4414.spring.sys.result.SysResultEnum;
import cc.cc4414.spring.sys.service.ITreePathService;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 闭包表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(ITreePathService.class)
public class TreePathServiceImpl extends ServiceImpl<TreePathMapper, TreePath> implements ITreePathService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	@LockAnnotation("tree")
	public void addNode(String id, String parentId, String scope) {
		log.debug("在父节点parentId下增加一个子节点: id={}, parentId={}, scope={}", id, parentId, scope);
		// 若parentId为"0"，则查询出的list为空列表
		List<TreePath> list = findAncestorList(parentId);
		List<TreePath> entityList = list.stream().map(i -> {
			TreePath treePath = new TreePath();
			treePath.setAncestor(i.getAncestor());
			treePath.setDescendant(id);
			treePath.setDistance(i.getDistance() + 1);
			treePath.setScope(scope);
			return treePath;
		}).collect(Collectors.toList());
		// 若list为空列表，则上面的遍历不执行，只会新增下面一条数据
		TreePath treePath = new TreePath();
		treePath.setAncestor(id);
		treePath.setDescendant(id);
		treePath.setDistance(0);
		treePath.setScope(scope);
		entityList.add(treePath);
		// 若id在表中已存在，则因为数据库的联合唯一索引抛出异常
		saveBatch(entityList);
		log.debug("新增成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@LockAnnotation("tree")
	public List<String> deleteNode(String id) {
		log.debug("删除节点及其所有子节点: id={}", id);
		List<String> ids = findDescendantNodes(id);
		LambdaQueryWrapper<TreePath> wrapper = Wrappers.lambdaQuery();
		wrapper.in(TreePath::getDescendant, ids);
		remove(wrapper);
		log.debug("删除成功");
		return ids;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@LockAnnotation("tree")
	public void moveNode(String id, String parentId) {
		log.debug("移动子树: id={}, parentId={}", id, parentId);
		// ----------------分离子树开始----------------
		// TreePath在嫁接时会用到，所以这里没有使用findDescendantNodes方法
		List<TreePath> descendantList = findDescendantList(id);
		List<String> descendantNodes = descendantList.stream().map(TreePath::getDescendant)
				.collect(Collectors.toList());
		// 父id不能为自己的后代
		if (descendantNodes.contains(parentId)) {
			throw new ResultException(SysResultEnum.TREE_MOVE_ERROR);
		}
		List<String> ancestorNodes = findAncestorNodes(id);
		// 分离
		LambdaQueryWrapper<TreePath> wrapper = Wrappers.lambdaQuery();
		wrapper.ne(TreePath::getAncestor, id);
		wrapper.in(TreePath::getAncestor, ancestorNodes);
		wrapper.in(TreePath::getDescendant, descendantNodes);
		remove(wrapper);
		// ----------------分离子树结束----------------
		log.debug("分离成功");
		// ----------------嫁接子树到parentId开始----------------
		List<TreePath> entityList = new ArrayList<>();
		List<TreePath> ancestorList = findAncestorList(parentId);
		// 嫁接
		ancestorList.forEach(i -> {
			descendantList.forEach(j -> {
				TreePath treePath = new TreePath();
				treePath.setAncestor(i.getAncestor());
				treePath.setDescendant(j.getDescendant());
				treePath.setDistance(i.getDistance() + j.getDistance() + 1);
				treePath.setScope(i.getScope());
				entityList.add(treePath);
			});
		});
		if (CollUtil.isNotEmpty(entityList)) {
			saveBatch(entityList);
		}
		// ----------------嫁接子树到parentId结束----------------
		log.debug("移动成功");
	}

	@Override
	public String findParentNode(String id) {
		log.debug("查询父节点id: id={}", id);
		LambdaQueryWrapper<TreePath> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(TreePath::getDescendant, id);
		wrapper.eq(TreePath::getDistance, 1);
		TreePath treePath = getOne(wrapper);
		log.debug("查询成功: {}", treePath);
		if (treePath == null) {
			return null;
		}
		return treePath.getAncestor();
	}

	@Override
	public List<String> findChildNodes(String id) {
		log.debug("查询所有子节点id: id={}", id);
		LambdaQueryWrapper<TreePath> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(TreePath::getAncestor, id);
		wrapper.eq(TreePath::getDistance, 1);
		List<TreePath> list = list(wrapper);
		List<String> ids = list.stream().map(TreePath::getDescendant).collect(Collectors.toList());
		log.debug("查询成功: {}", ids);
		return ids;
	}

	@Override
	public List<String> findAncestorNodes(String id) {
		log.debug("查询所有祖先节点id: id={}", id);
		if (SysConsts.ID.equals(id)) {
			return new ArrayList<>();
		}
		List<TreePath> list = findAncestorList(id);
		List<String> ids = list.stream().map(TreePath::getAncestor).collect(Collectors.toList());
		log.debug("查询成功: {}", ids);
		return ids;
	}

	@Override
	public List<String> findDescendantNodes(String id) {
		log.debug("查询所有后代节点id: id={}", id);
		List<TreePath> list = findDescendantList(id);
		List<String> ids = list.stream().map(TreePath::getDescendant).collect(Collectors.toList());
		log.debug("查询成功: {}", ids);
		return ids;
	}

	/**
	 * 查询所有祖先节点TreePath（包括自己）
	 * 
	 * @param id 节点id
	 * @return 祖先节点TreePath列表
	 */
	private List<TreePath> findAncestorList(String id) {
		LambdaQueryWrapper<TreePath> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(TreePath::getDescendant, id);
		List<TreePath> list = list(wrapper);
		return list;
	}

	/**
	 * 查询所有后代节点TreePath（包括自己）
	 * 
	 * @param id 节点id
	 * @return 后代节点TreePath列表
	 */
	private List<TreePath> findDescendantList(String id) {
		LambdaQueryWrapper<TreePath> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(TreePath::getAncestor, id);
		List<TreePath> list = list(wrapper);
		return list;
	}

	@Override
	public List<String> findRootNodes(String scope) {
		log.debug("根据范围查找所有根节点: scope={}", scope);
		List<String> ids = baseMapper.listRootNodesByScope(scope);
		log.debug("查询成功: {}", ids);
		return ids;
	}

}
