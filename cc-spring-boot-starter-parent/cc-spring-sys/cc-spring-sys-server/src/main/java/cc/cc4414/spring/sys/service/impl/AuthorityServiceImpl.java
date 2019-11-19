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

import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.mybatis.service.impl.CcServiceImpl;
import cc.cc4414.spring.mybatis.update.UpdateObservable;
import cc.cc4414.spring.mybatis.update.UpdateUtils;
import cc.cc4414.spring.sys.constant.DictCodeConsts;
import cc.cc4414.spring.sys.entity.Authority;
import cc.cc4414.spring.sys.entity.Dict;
import cc.cc4414.spring.sys.entity.DictItem;
import cc.cc4414.spring.sys.entity.Role;
import cc.cc4414.spring.sys.entity.RoleAuthority;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.mapper.AuthorityMapper;
import cc.cc4414.spring.sys.result.SysResultEnum;
import cc.cc4414.spring.sys.service.IAuthorityService;
import cc.cc4414.spring.sys.service.IDictService;
import cc.cc4414.spring.sys.service.IRoleAuthorityService;
import cc.cc4414.spring.sys.service.IRoleService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 权限表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(IAuthorityService.class)
public class AuthorityServiceImpl extends CcServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

	@Autowired
	private IRoleAuthorityService iRoleAuthorityService;

	@Autowired
	private IRoleService iRoleService;

	@Autowired
	private IDictService iDictService;

	@Autowired
	private UpdateObservable updateObservable;

	@PostConstruct
	public void init() {
		updateObservable.addObserver(o -> {
			User user = UpdateUtils.getEntity(User::getName, o);
			if (user != null) {
				LambdaUpdateWrapper<Authority> wrapperCreator = Wrappers.lambdaUpdate();
				wrapperCreator.eq(Authority::getCreatorId, user.getId());
				wrapperCreator.set(Authority::getCreatorName, user.getName());
				update(null, wrapperCreator);
				LambdaUpdateWrapper<Authority> wrapperModifier = Wrappers.lambdaUpdate();
				wrapperModifier.eq(Authority::getModifierId, user.getId());
				wrapperModifier.set(Authority::getModifierName, user.getName());
				update(null, wrapperModifier);
				return;
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Authority add(String name, String code, String url, Integer type) {
		log.debug("新增权限: name={}, code={}, url={}, type={}", name, code, url, type);
		checkAuthorityType(type);
		Authority entity = new Authority();
		entity.setName(name);
		entity.setCode(code);
		entity.setUrl(url);
		entity.setType(type);
		save(entity);
		log.debug("新增成功: {}", entity);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name, String code, String url, Integer type) {
		log.debug("修改权限: id={}, name={}, code={}, url={}, type={}", id, name, code, url, type);
		checkAuthorityType(type);
		Authority entity = new Authority();
		entity.setId(id);
		entity.setName(name);
		entity.setCode(code);
		entity.setUrl(url);
		entity.setType(type);
		updateById(entity);
		log.debug("修改成功");
	}

	/**
	 * 检查权限类型是否存在，不存在则抛出异常
	 * 
	 * @param type 权限类型
	 */
	private void checkAuthorityType(Integer type) {
		Dict dict = iDictService.getEnabledByCode(DictCodeConsts.AUTHORITY_TYPE);
		log.debug("权限类型字典: {}", dict);
		List<DictItem> list = dict.getDictItemList();
		for (DictItem dictItem : list) {
			if (String.valueOf(type).equals(dictItem.getValue())) {
				return;
			}
		}
		throw new ResultException(SysResultEnum.AUTHORITY_TYPE_NOT_EXIST);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addRole(String id, List<String> ids) {
		log.debug("添加角色到权限: id={}, ids={}", id, ids);
		if (StrUtil.isBlank(id) || CollUtil.isEmpty(ids)) {
			return;
		}
		List<RoleAuthority> roleAuthorities = new ArrayList<>();
		ids.forEach(i -> {
			RoleAuthority roleAuthority = new RoleAuthority();
			roleAuthority.setRoleId(i);
			roleAuthority.setAuthorityId(id);
			roleAuthorities.add(roleAuthority);
		});
		iRoleAuthorityService.saveBatch(roleAuthorities);
		// 校验权限及角色是否存在以及是否被禁用
		checkAllIsEnable(Arrays.asList(id));
		iRoleService.checkAllIsEnable(ids);
		log.debug("添加成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(String id) {
		log.debug("移除权限中的全部角色: id={}", id);
		LambdaQueryWrapper<RoleAuthority> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(RoleAuthority::getAuthorityId, id);
		iRoleAuthorityService.remove(wrapper);
		// 校验权限是否存在
		checkAllIsExist(Arrays.asList(id));
		log.debug("移除成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(String id, List<String> ids) {
		log.debug("移除权限中的角色: id={}, ids={}", id, ids);
		if (CollUtil.isEmpty(ids)) {
			return;
		}
		LambdaQueryWrapper<RoleAuthority> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(RoleAuthority::getAuthorityId, id);
		wrapper.in(RoleAuthority::getRoleId, ids);
		iRoleAuthorityService.remove(wrapper);
		// 校验权限是否存在
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
	public Authority add(String name, String code, String url, Integer type, List<String> roleIds) {
		Authority entity = add(name, code, url, type);
		addRole(entity.getId(), roleIds);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name, String code, String url, Integer type, List<String> roleIds) {
		if (name != null || code != null || url != null || type != null) {
			update(id, name, code, url, type);
		}
		if (roleIds != null) {
			updateRole(id, roleIds);
		}
	}

	@Override
	public Authority get(String id, boolean roleList) {
		log.debug("根据id查询权限: id={}, roleList={}", id, roleList);
		Authority entity = getById(id);
		if (entity == null) {
			log.debug("查询成功: null");
			return null;
		}
		setList(Arrays.asList(entity), roleList);
		return entity;
	}

	@Override
	public List<Authority> list(Wrapper<Authority> queryWrapper, boolean roleList) {
		log.debug("查询所有权限: roleList={}", roleList);
		List<Authority> list = list(queryWrapper);
		setList(list, roleList);
		return list;
	}

	@Override
	public IPage<Authority> page(IPage<Authority> page, Wrapper<Authority> queryWrapper, boolean roleList) {
		log.debug("分页查询权限: roleList={}", roleList);
		IPage<Authority> iPage = page(page, queryWrapper);
		List<Authority> list = iPage.getRecords();
		setList(list, roleList);
		return iPage;
	}

	/**
	 * 查询并设置每个权限中对应的角色列表
	 * 
	 * @param list     权限列表
	 * @param roleList 是否查询对应角色列表
	 */
	private void setList(List<Authority> list, boolean roleList) {
		log.debug("开始查询，共{}条", list.size());
		if (CollUtil.isEmpty(list)) {
			return;
		}
		List<String> ids = list.stream().map(i -> i.getId()).collect(Collectors.toList());
		if (roleList) {
			Map<String, List<Role>> listMap = iRoleService.listMapByAuthorityIds(ids);
			list.forEach(i -> i.setRoleList(listMap.get(i.getId())));
		}
		log.debug("查询并设置成功");
	}

	@Override
	public Map<String, List<Authority>> listMapByRoleIds(List<String> ids) {
		log.debug("开始查询每个角色中的权限列表");
		Map<String, List<Authority>> map = new HashMap<>(ids.size());
		ids.forEach(i -> map.put(i, new ArrayList<>()));
		List<Authority> list = baseMapper.listByRoleIds(ids);
		list.forEach(i -> map.get(i.getRoleId()).add(i));
		log.debug("查询成功");
		return map;
	}

	@Override
	public List<Authority> listByUserId(String userId) {
		log.debug("通过用户id查找该用户所拥有的所有可用权限: userId={}", userId);
		List<Authority> list = baseMapper.listByUserId(userId);
		log.debug("查询成功");
		return list;
	}

}
