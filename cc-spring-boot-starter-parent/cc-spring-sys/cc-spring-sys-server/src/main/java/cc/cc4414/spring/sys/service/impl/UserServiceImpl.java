package cc.cc4414.spring.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.mybatis.service.impl.CcServiceImpl;
import cc.cc4414.spring.mybatis.update.UpdateObservable;
import cc.cc4414.spring.mybatis.update.UpdateService;
import cc.cc4414.spring.mybatis.update.UpdateUtils;
import cc.cc4414.spring.sys.entity.Dept;
import cc.cc4414.spring.sys.entity.Role;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.entity.UserDept;
import cc.cc4414.spring.sys.entity.UserRole;
import cc.cc4414.spring.sys.mapper.UserMapper;
import cc.cc4414.spring.sys.result.SysResultEnum;
import cc.cc4414.spring.sys.service.IDeptService;
import cc.cc4414.spring.sys.service.IRoleService;
import cc.cc4414.spring.sys.service.IUserDeptService;
import cc.cc4414.spring.sys.service.IUserRoleService;
import cc.cc4414.spring.sys.service.IUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(IUserService.class)
public class UserServiceImpl extends CcServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private IUserRoleService iUserRoleService;

	@Autowired
	private IRoleService iRoleService;

	@Autowired
	private IUserDeptService iUserDeptService;

	@Autowired
	private IDeptService iDeptService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UpdateService updateService;

	@Autowired
	private UpdateObservable updateObservable;

	@PostConstruct
	public void init() {
		updateObservable.addObserver(o -> {
			User user = UpdateUtils.getEntity(User::getName, o);
			if (user != null) {
				LambdaUpdateWrapper<User> wrapperCreator = Wrappers.lambdaUpdate();
				wrapperCreator.eq(User::getCreatorId, user.getId());
				wrapperCreator.set(User::getCreatorName, user.getName());
				update(null, wrapperCreator);
				LambdaUpdateWrapper<User> wrapperModifier = Wrappers.lambdaUpdate();
				wrapperModifier.eq(User::getModifierId, user.getId());
				wrapperModifier.set(User::getModifierName, user.getName());
				update(null, wrapperModifier);
				return;
			}
			Dept dept = UpdateUtils.getEntity(Dept::getName, o);
			if (dept != null) {
				LambdaUpdateWrapper<User> wrapperDept = Wrappers.lambdaUpdate();
				wrapperDept.eq(User::getDeptId, dept.getId());
				wrapperDept.set(User::getDeptName, dept.getName());
				update(null, wrapperDept);
				return;
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User add(String name, String username, String password) {
		log.debug("新增用户: name={}, username={}", name, username);
		User entity = new User();
		entity.setName(name);
		entity.setUsername(username);
		entity.setPassword(passwordEncoder.encode(password));
		save(entity);
		log.debug("新增成功: {}", entity);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name, String password) {
		log.debug("修改用户: id={}, name={}", id, name);
		User entity = new User();
		entity.setId(id);
		entity.setName(name);
		if (StrUtil.isNotBlank(password)) {
			entity.setPassword(passwordEncoder.encode(password));
		}
		updateById(entity);
		if (name != null) {
			updateService.update(User::getName, entity);
		}
		log.debug("修改成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addRole(String id, List<String> ids) {
		log.debug("添加角色到用户: id={}, ids={}", id, ids);
		if (StrUtil.isBlank(id) || CollUtil.isEmpty(ids)) {
			return;
		}
		List<UserRole> userRoles = new ArrayList<>();
		ids.forEach(i -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(id);
			userRole.setRoleId(i);
			userRoles.add(userRole);
		});
		iUserRoleService.saveBatch(userRoles);
		// 校验用户及角色是否存在以及是否被禁用
		checkAllIsEnable(Arrays.asList(id));
		iRoleService.checkAllIsEnable(ids);
		log.debug("添加成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(String id) {
		log.debug("移除用户中的全部角色: id={}", id);
		LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserRole::getUserId, id);
		iUserRoleService.remove(wrapper);
		// 校验用户是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(String id, List<String> ids) {
		log.debug("移除用户中的角色: id={}, ids={}", id, ids);
		if (CollUtil.isEmpty(ids)) {
			return;
		}
		LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserRole::getUserId, id);
		wrapper.in(UserRole::getRoleId, ids);
		iUserRoleService.remove(wrapper);
		// 校验用户是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRole(String id, List<String> ids) {
		deleteRole(id);
		addRole(id, ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRole(String id, List<String> deleteIds, List<String> addIds) {
		deleteRole(id, deleteIds);
		addRole(id, addIds);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addDept(String id, List<String> ids) {
		log.debug("添加部门到用户: id={}, ids={}", id, ids);
		if (StrUtil.isBlank(id) || CollUtil.isEmpty(ids)) {
			return;
		}
		List<UserDept> userDepts = new ArrayList<>();
		ids.forEach(i -> {
			UserDept userDept = new UserDept();
			userDept.setUserId(id);
			userDept.setDeptId(i);
			userDepts.add(userDept);
		});
		iUserDeptService.saveBatch(userDepts);
		// 校验用户及部门是否存在以及是否被禁用
		checkAllIsEnable(Arrays.asList(id));
		iDeptService.checkAllIsEnable(ids);
		log.debug("添加成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDept(String id) {
		log.debug("移除用户中的全部部门: id={}", id);
		LambdaQueryWrapper<UserDept> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserDept::getUserId, id);
		iUserDeptService.remove(wrapper);
		// 校验用户是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDept(String id, List<String> ids) {
		log.debug("移除用户中的部门: id={}, ids={}", id, ids);
		if (CollUtil.isEmpty(ids)) {
			return;
		}
		LambdaQueryWrapper<UserDept> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserDept::getUserId, id);
		wrapper.in(UserDept::getDeptId, ids);
		iUserDeptService.remove(wrapper);
		// 校验用户是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDept(String id, List<String> ids) {
		deleteDept(id);
		addDept(id, ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDept(String id, List<String> deleteIds, List<String> addIds) {
		deleteDept(id, deleteIds);
		addDept(id, addIds);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User add(String name, String username, String password, List<String> roleIds, List<String> deptIds) {
		User entity = add(name, username, password);
		addRole(entity.getId(), roleIds);
		addDept(entity.getId(), deptIds);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name, String password, List<String> roleIds, List<String> deptIds) {
		if (name != null || password != null) {
			update(id, name, password);
		}
		if (roleIds != null) {
			updateRole(id, roleIds);
		}
		if (deptIds != null) {
			updateDept(id, deptIds);
		}
	}

	@Override
	public User get(String id, boolean roleList, boolean deptList) {
		log.debug("根据id查询用户: id={}, roleList={}, deptList={}", id, roleList, deptList);
		User entity = getById(id);
		if (entity == null) {
			log.debug("查询成功: null");
			return null;
		}
		setList(Arrays.asList(entity), roleList, deptList);
		return entity;
	}

	@Override
	public List<User> list(Wrapper<User> queryWrapper, boolean roleList, boolean deptList) {
		log.debug("查询所有用户: roleList={}, deptList={}", roleList, deptList);
		List<User> list = list(queryWrapper);
		setList(list, roleList, deptList);
		return list;
	}

	@Override
	public IPage<User> page(IPage<User> page, Wrapper<User> queryWrapper, boolean roleList, boolean deptList) {
		log.debug("分页查询用户: roleList={}, deptList={}", roleList, deptList);
		IPage<User> iPage = page(page, queryWrapper);
		List<User> list = iPage.getRecords();
		setList(list, roleList, deptList);
		return iPage;
	}

	/**
	 * 查询并设置每个用户中对应的角色及部门列表
	 * 
	 * @param list          用户列表
	 * @param userList      是否查询对应角色列表
	 * @param authorityList 是否查询对应部门列表
	 */
	private void setList(List<User> list, boolean roleList, boolean deptList) {
		log.debug("开始查询，共{}条", list.size());
		if (CollUtil.isEmpty(list)) {
			return;
		}
		List<String> ids = list.stream().map(i -> i.getId()).collect(Collectors.toList());
		if (roleList) {
			Map<String, List<Role>> listMap = iRoleService.listMapByUserIds(ids);
			list.forEach(i -> i.setRoleList(listMap.get(i.getId())));
		}
		if (deptList) {
			Map<String, List<Dept>> listMap = iDeptService.listMapByUserIds(ids);
			list.forEach(i -> i.setDeptList(listMap.get(i.getId())));
		}
		log.debug("查询并设置成功");
	}

	@Override
	public Map<String, List<User>> listMapByRoleIds(List<String> ids) {
		log.debug("开始查询每个角色中的用户列表");
		Map<String, List<User>> map = new HashMap<>(ids.size());
		ids.forEach(i -> map.put(i, new ArrayList<>()));
		List<User> list = baseMapper.listByRoleIds(ids);
		list.forEach(i -> map.get(i.getRoleId()).add(i));
		log.debug("查询成功");
		return map;
	}

	@Override
	public Map<String, List<User>> listMapByDeptIds(List<String> ids) {
		log.debug("开始查询每个部门中的用户列表");
		Map<String, List<User>> map = new HashMap<>(ids.size());
		ids.forEach(i -> map.put(i, new ArrayList<>()));
		List<User> list = baseMapper.listByDeptIds(ids);
		list.forEach(i -> map.get(i.getDeptId()).add(i));
		log.debug("查询成功");
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changePassword(String id, String password, String oldPassword) {
		User user = get(id, false, false);
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new ResultException(SysResultEnum.PASSWORD_ERROR);
		}
		update(id, null, password);
	}

}
