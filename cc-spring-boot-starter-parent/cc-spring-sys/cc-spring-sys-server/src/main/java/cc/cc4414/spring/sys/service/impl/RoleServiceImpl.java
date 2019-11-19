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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cc.cc4414.spring.mybatis.service.impl.CcServiceImpl;
import cc.cc4414.spring.mybatis.update.UpdateObservable;
import cc.cc4414.spring.mybatis.update.UpdateUtils;
import cc.cc4414.spring.sys.constant.SysConsts;
import cc.cc4414.spring.sys.entity.Authority;
import cc.cc4414.spring.sys.entity.Role;
import cc.cc4414.spring.sys.entity.RoleAuthority;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.entity.UserRole;
import cc.cc4414.spring.sys.mapper.RoleMapper;
import cc.cc4414.spring.sys.service.IAuthorityService;
import cc.cc4414.spring.sys.service.IRoleAuthorityService;
import cc.cc4414.spring.sys.service.IRoleService;
import cc.cc4414.spring.sys.service.IUserRoleService;
import cc.cc4414.spring.sys.service.IUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(IRoleService.class)
public class RoleServiceImpl extends CcServiceImpl<RoleMapper, Role> implements IRoleService {

	@Autowired
	private IUserRoleService iUserRoleService;

	@Autowired
	private IUserService iUserService;

	@Autowired
	private IRoleAuthorityService iRoleAuthorityService;

	@Autowired
	private IAuthorityService iAuthorityService;

	@Autowired
	private UpdateObservable updateObservable;

	@PostConstruct
	public void init() {
		updateObservable.addObserver(o -> {
			User user = UpdateUtils.getEntity(User::getName, o);
			if (user != null) {
				LambdaUpdateWrapper<Role> wrapperCreator = Wrappers.lambdaUpdate();
				wrapperCreator.eq(Role::getCreatorId, user.getId());
				wrapperCreator.set(Role::getCreatorName, user.getName());
				update(null, wrapperCreator);
				LambdaUpdateWrapper<Role> wrapperModifier = Wrappers.lambdaUpdate();
				wrapperModifier.eq(Role::getModifierId, user.getId());
				wrapperModifier.set(Role::getModifierName, user.getName());
				update(null, wrapperModifier);
				return;
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Role add(String name) {
		log.debug("新增角色: name={}", name);
		Role entity = new Role();
		entity.setName(name);
		save(entity);
		log.debug("新增成功: {}", entity);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name) {
		log.debug("修改角色名称: id={}, name={}", id, name);
		Role entity = new Role();
		entity.setId(id);
		entity.setName(name);
		updateById(entity);
		log.debug("修改成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addUser(String id, List<String> ids) {
		log.debug("添加用户到角色: id={}, ids={}", id, ids);
		if (StrUtil.isBlank(id) || CollUtil.isEmpty(ids)) {
			return;
		}
		List<UserRole> userRoles = new ArrayList<>();
		ids.forEach(i -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(i);
			userRole.setRoleId(id);
			userRoles.add(userRole);
		});
		iUserRoleService.saveBatch(userRoles);
		// 校验角色及用户是否存在以及是否被禁用
		checkAllIsEnable(Arrays.asList(id));
		iUserService.checkAllIsEnable(ids);
		log.debug("添加成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(String id) {
		log.debug("移除角色中的全部用户: id={}", id);
		LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserRole::getRoleId, id);
		iUserRoleService.remove(wrapper);
		// 校验角色是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(String id, List<String> ids) {
		log.debug("移除角色中的用户: id={}, ids={}", id, ids);
		if (CollUtil.isEmpty(ids)) {
			return;
		}
		LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(UserRole::getRoleId, id);
		wrapper.in(UserRole::getUserId, ids);
		iUserRoleService.remove(wrapper);
		// 校验角色是否存在
		checkAllIsExist(Arrays.asList(id));
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
	public void addAuthority(String id, List<String> ids) {
		log.debug("添加权限到角色: id={}, ids={}", id, ids);
		if (StrUtil.isBlank(id) || CollUtil.isEmpty(ids)) {
			return;
		}
		List<RoleAuthority> roleAuthorities = new ArrayList<>();
		ids.forEach(i -> {
			RoleAuthority roleAuthority = new RoleAuthority();
			roleAuthority.setRoleId(id);
			roleAuthority.setAuthorityId(i);
			roleAuthorities.add(roleAuthority);
		});
		iRoleAuthorityService.saveBatch(roleAuthorities);
		// 校验角色及权限是否存在以及是否被禁用
		checkAllIsEnable(Arrays.asList(id));
		iAuthorityService.checkAllIsEnable(ids);
		log.debug("添加成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAuthority(String id) {
		log.debug("移除角色中的全部权限: id={}", id);
		LambdaQueryWrapper<RoleAuthority> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(RoleAuthority::getRoleId, id);
		iRoleAuthorityService.remove(wrapper);
		// 校验角色是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAuthority(String id, List<String> ids) {
		log.debug("移除角色中的权限: id={}, ids={}", id, ids);
		if (CollUtil.isEmpty(ids)) {
			return;
		}
		LambdaQueryWrapper<RoleAuthority> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(RoleAuthority::getRoleId, id);
		wrapper.in(RoleAuthority::getAuthorityId, ids);
		iRoleAuthorityService.remove(wrapper);
		// 校验角色是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAuthority(String id, List<String> ids) {
		deleteAuthority(id);
		addAuthority(id, ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAuthority(String id, List<String> deleteIds, List<String> addIds) {
		deleteAuthority(id, deleteIds);
		addAuthority(id, addIds);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Role add(String name, List<String> userIds, List<String> authorityIds) {
		Role entity = add(name);
		addUser(entity.getId(), userIds);
		addAuthority(entity.getId(), authorityIds);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name, List<String> userIds, List<String> authorityIds) {
		if (name != null) {
			update(id, name);
		}
		if (userIds != null) {
			updateUser(id, userIds);
		}
		if (authorityIds != null) {
			updateAuthority(id, authorityIds);
		}
	}

	@Override
	public Role get(String id, boolean userList, boolean authorityList) {
		log.debug("根据id查询角色: id={}, userList={}, authorityList={}", id, userList, authorityList);
		Role entity = getById(id);
		if (entity == null) {
			log.debug("查询成功: null");
			return null;
		}
		setList(Arrays.asList(entity), userList, authorityList);
		return entity;
	}

	@Override
	public List<Role> list(Wrapper<Role> queryWrapper, boolean userList, boolean authorityList) {
		log.debug("查询所有角色: userList={}, authorityList={}", userList, authorityList);
		List<Role> list = list(queryWrapper);
		setList(list, userList, authorityList);
		return list;
	}

	@Override
	public IPage<Role> page(IPage<Role> page, Wrapper<Role> queryWrapper, boolean userList, boolean authorityList) {
		log.debug("分页查询角色: userList={}, authorityList={}", userList, authorityList);
		IPage<Role> iPage = page(page, queryWrapper);
		List<Role> list = iPage.getRecords();
		setList(list, userList, authorityList);
		return iPage;
	}

	/**
	 * 查询并设置每个角色中对应的用户及权限列表
	 * 
	 * @param list          角色列表
	 * @param userList      是否查询对应用户列表
	 * @param authorityList 是否查询对应权限列表
	 */
	private void setList(List<Role> list, boolean userList, boolean authorityList) {
		log.debug("开始查询，共{}条", list.size());
		if (CollUtil.isEmpty(list)) {
			return;
		}
		List<String> ids = list.stream().map(i -> i.getId()).collect(Collectors.toList());
		if (userList) {
			Map<String, List<User>> listMap = iUserService.listMapByRoleIds(ids);
			list.forEach(i -> i.setUserList(listMap.get(i.getId())));
		}
		if (authorityList) {
			Map<String, List<Authority>> listMap = iAuthorityService.listMapByRoleIds(ids);
			list.forEach(i -> i.setAuthorityList(listMap.get(i.getId())));
		}
		log.debug("查询并设置成功");
	}

	@Override
	public Map<String, List<Role>> listMapByAuthorityIds(List<String> ids) {
		log.debug("开始查询每个权限中的角色列表");
		Map<String, List<Role>> map = new HashMap<>(ids.size());
		ids.forEach(i -> map.put(i, new ArrayList<>()));
		List<Role> list = baseMapper.listByAuthorityIds(ids);
		list.forEach(i -> map.get(i.getAuthorityId()).add(i));
		log.debug("查询成功");
		return map;
	}

	@Override
	public Map<String, List<Role>> listMapByUserIds(List<String> ids) {
		log.debug("开始查询每个用户中的角色列表");
		Map<String, List<Role>> map = new HashMap<>(ids.size());
		ids.forEach(i -> map.put(i, new ArrayList<>()));
		List<Role> list = baseMapper.listByUserIds(ids);
		list.forEach(i -> map.get(i.getUserId()).add(i));
		log.debug("查询成功");
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Role addAdmin(String id) {
		log.debug("在当前租户下新增管理员角色: id={}", id);
		LambdaQueryWrapper<Authority> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Authority::getType, 0);
		List<Authority> list = iAuthorityService.list(wrapper);
		List<String> authorityIds = list.stream().map(Authority::getId).collect(Collectors.toList());
		Role role = add(SysConsts.ADMIN, Arrays.asList(id), authorityIds);
		log.debug("新增成功");
		return role;
	}

}
