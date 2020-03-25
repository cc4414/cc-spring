package cc.cc4414.spring.sys.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cc.cc4414.spring.common.model.Tree;
import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.mybatis.service.impl.CcServiceImpl;
import cc.cc4414.spring.mybatis.update.UpdateObservable;
import cc.cc4414.spring.mybatis.update.UpdateService;
import cc.cc4414.spring.mybatis.update.UpdateUtils;
import cc.cc4414.spring.sys.constant.SysConsts;
import cc.cc4414.spring.sys.constant.TreeScopeConsts;
import cc.cc4414.spring.sys.entity.Dept;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.entity.UserDept;
import cc.cc4414.spring.sys.mapper.DeptMapper;
import cc.cc4414.spring.sys.result.SysResultEnum;
import cc.cc4414.spring.sys.service.IDeptService;
import cc.cc4414.spring.sys.service.ITreePathService;
import cc.cc4414.spring.sys.service.IUserDeptService;
import cc.cc4414.spring.sys.service.IUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 部门表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(IDeptService.class)
public class DeptServiceImpl extends CcServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Autowired
	private ITreePathService iTreePathService;

	@Autowired
	private IUserDeptService iUserDeptService;

	@Autowired
	private IUserService iUserService;

	@Autowired
	private UpdateService updateService;

	@Autowired
	private UpdateObservable updateObservable;

	@PostConstruct
	public void init() {
		updateObservable.addObserver(o -> {
			User user = UpdateUtils.getEntity(User::getName, o);
			if (user != null) {
				LambdaUpdateWrapper<Dept> wrapperCreator = Wrappers.lambdaUpdate();
				wrapperCreator.eq(Dept::getCreatorId, user.getId());
				wrapperCreator.set(Dept::getCreatorName, user.getName());
				update(null, wrapperCreator);
				LambdaUpdateWrapper<Dept> wrapperModifier = Wrappers.lambdaUpdate();
				wrapperModifier.eq(Dept::getModifierId, user.getId());
				wrapperModifier.set(Dept::getModifierName, user.getName());
				update(null, wrapperModifier);
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Dept add(String name) {
		log.debug("新增部门: name={}", name);
		Dept entity = new Dept();
		entity.setName(name);
		save(entity);
		log.debug("新增成功: {}", entity);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(String id) {
		log.debug("根据id删除部门及其子部门: id={}", id);
		if (StrUtil.isBlank(id)) {
			return;
		}
		checkAllIsExist(Collections.singletonList(id));
		List<String> ids = iTreePathService.deleteNode(id);
		deleteByIds(ids);
		log.debug("删除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name) {
		log.debug("修改部门名称: id={}, name={}", id, name);
		Dept entity = new Dept();
		entity.setId(id);
		entity.setName(name);
		updateById(entity);
		if (name != null) {
			updateService.update(Dept::getName, entity);
		}
		log.debug("修改成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addUser(String id, List<String> ids) {
		log.debug("添加用户到部门: id={}, ids={}", id, ids);
		if (StrUtil.isBlank(id) || CollUtil.isEmpty(ids)) {
			return;
		}
		List<UserDept> userDepts = new ArrayList<>();
		ids.forEach(i -> {
			UserDept userDept = new UserDept();
			userDept.setUserId(i);
			userDept.setDeptId(id);
			userDepts.add(userDept);
		});
		iUserDeptService.saveBatch(userDepts);
		// 校验部门及用户是否存在以及是否被禁用
		checkAllIsEnable(Collections.singletonList(id));
		iUserService.checkAllIsEnable(ids);
		log.debug("添加成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(String id) {
		log.debug("移除部门中的全部用户: id={}", id);
		LambdaQueryWrapper<UserDept> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserDept::getDeptId, id);
		iUserDeptService.remove(wrapper);
		// 校验部门是否存在
		checkAllIsExist(Collections.singletonList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(String id, List<String> ids) {
		log.debug("移除部门中的用户: id={}, ids={}", id, ids);
		if (CollUtil.isEmpty(ids)) {
			return;
		}
		LambdaQueryWrapper<UserDept> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserDept::getDeptId, id);
		wrapper.in(UserDept::getUserId, ids);
		iUserDeptService.remove(wrapper);
		// 校验部门是否存在
		checkAllIsExist(Collections.singletonList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUser(String id, List<String> ids) {
		deleteUser(id);
		addUser(id, ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUser(String id, List<String> deleteIds, List<String> addIds) {
		deleteUser(id, deleteIds);
		addUser(id, addIds);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Dept add(String name, String parentId, List<String> userIds) {
		Dept entity = add(name);
		addNode(entity.getId(), parentId);
		addUser(entity.getId(), userIds);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name, String parentId, List<String> userIds) {
		if (name != null) {
			update(id, name);
		}
		if (parentId != null) {
			moveNode(id, parentId);
		}
		if (userIds != null) {
			updateUser(id, userIds);
		}
	}

	@Override
	public Dept get(String id, boolean userList) {
		log.debug("根据id查询部门: id={}, userList={}", id, userList);
		Dept entity = getById(id);
		if (entity == null) {
			log.debug("查询成功: null");
			return null;
		}
		setList(Collections.singletonList(entity), userList);
		return entity;
	}

	@Override
	public List<Dept> list(Wrapper<Dept> queryWrapper, boolean userList) {
		log.debug("查询所有部门: userList={}", userList);
		List<Dept> list = list(queryWrapper);
		setList(list, userList);
		return list;
	}

	@Override
	public IPage<Dept> page(IPage<Dept> page, Wrapper<Dept> queryWrapper, boolean userList) {
		log.debug("分页查询部门: userList={}", userList);
		IPage<Dept> iPage = page(page, queryWrapper);
		List<Dept> list = iPage.getRecords();
		setList(list, userList);
		return iPage;
	}

	/**
	 * 查询并设置每个部门中对应的用户列表
	 * 
	 * @param list     部门列表
	 * @param userList 是否查询对应用户列表
	 */
	private void setList(List<Dept> list, boolean userList) {
		log.debug("开始查询，共{}条", list.size());
		if (CollUtil.isEmpty(list)) {
			return;
		}
		List<String> ids = list.stream().map(BaseEntity::getId).collect(Collectors.toList());
		if (userList) {
			Map<String, List<User>> listMap = iUserService.listMapByDeptIds(ids);
			list.forEach(i -> i.setUserList(listMap.get(i.getId())));
		}
		log.debug("查询并设置成功");
	}

	@Override
	public Map<String, List<Dept>> listMapByUserIds(List<String> ids) {
		log.debug("开始查询每个用户中的部门列表");
		Map<String, List<Dept>> map = new HashMap<>(ids.size());
		ids.forEach(i -> map.put(i, new ArrayList<>()));
		List<Dept> list = baseMapper.listByUserIds(ids);
		list.forEach(i -> map.get(i.getUserId()).add(i));
		log.debug("查询成功");
		return map;
	}

	@Override
	public String getNameById(String id) {
		log.debug("通过id获取部门名称，id={}", id);
		Dept dept = getById(id);
		if (dept == null) {
			throw new ResultException(SysResultEnum.DEPT_NOT_EXIST);
		}
		String name = dept.getName();
		log.debug("查询成功: {}", name);
		return dept.getName();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addNode(String id, String parentId) {
		iTreePathService.addNode(id, parentId, TreeScopeConsts.SYS_DEPT);
		List<String> list = new ArrayList<>();
		list.add(id);
		if (!SysConsts.ID.equals(parentId)) {
			list.add(parentId);
		}
		checkAllIsExist(list);
		log.debug("新增成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void moveNode(String id, String parentId) {
		iTreePathService.moveNode(id, parentId);
		List<String> list = new ArrayList<>();
		list.add(id);
		if (!SysConsts.ID.equals(parentId)) {
			list.add(parentId);
		}
		checkAllIsExist(list);
		log.debug("移动成功");
	}

	@Override
	public List<Dept> listRootDeptNodes() {
		List<String> nodes = iTreePathService.findRootNodes(TreeScopeConsts.SYS_DEPT);
		if (CollUtil.isEmpty(nodes)) {
			return new ArrayList<>();
		}
		return baseMapper.selectBatchIds(nodes);
	}

	@Override
	public List<Dept> listChildDeptNodes(String id) {
		List<String> nodes = iTreePathService.findChildNodes(id);
		if (CollUtil.isEmpty(nodes)) {
			return new ArrayList<>();
		}
		return baseMapper.selectBatchIds(nodes);
	}

	@Override
	public List<Tree<Dept>> treeDeptNodes(String id) {
		List<Dept> list = SysConsts.ID.equals(id) ? listRootDeptNodes() : listChildDeptNodes(id);
		return list.stream().map(i -> {
			Tree<Dept> tree = new Tree<>();
			tree.setId(i.getId());
			tree.setLabel(i.getName());
			tree.setData(i);
			tree.setChildren(treeDeptNodes(i.getId()));
			return tree;
		}).collect(Collectors.toList());
	}

}
