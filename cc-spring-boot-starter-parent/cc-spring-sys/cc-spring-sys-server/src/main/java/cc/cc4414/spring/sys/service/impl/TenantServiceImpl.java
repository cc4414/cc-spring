package cc.cc4414.spring.sys.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cc.cc4414.spring.mybatis.service.impl.CcServiceImpl;
import cc.cc4414.spring.mybatis.update.UpdateObservable;
import cc.cc4414.spring.mybatis.update.UpdateUtils;
import cc.cc4414.spring.resource.util.UserUtils;
import cc.cc4414.spring.sys.constant.SysConsts;
import cc.cc4414.spring.sys.entity.Tenant;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.mapper.TenantMapper;
import cc.cc4414.spring.sys.service.IRoleService;
import cc.cc4414.spring.sys.service.ITenantService;
import cc.cc4414.spring.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * 租户表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(ITenantService.class)
public class TenantServiceImpl extends CcServiceImpl<TenantMapper, Tenant> implements ITenantService {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private IRoleService iRoleService;

	@Autowired
	private UpdateObservable updateObservable;

	@PostConstruct
	public void init() {
		updateObservable.addObserver(o -> {
			User user = UpdateUtils.getEntity(User::getName, o);
			if (user != null) {
				LambdaUpdateWrapper<Tenant> wrapperCreator = Wrappers.lambdaUpdate();
				wrapperCreator.eq(Tenant::getCreatorId, user.getId());
				wrapperCreator.set(Tenant::getCreatorName, user.getName());
				update(null, wrapperCreator);
				LambdaUpdateWrapper<Tenant> wrapperModifier = Wrappers.lambdaUpdate();
				wrapperModifier.eq(Tenant::getModifierId, user.getId());
				wrapperModifier.set(Tenant::getModifierName, user.getName());
				update(null, wrapperModifier);
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Tenant add(String name) {
		log.debug("新增租户: name={}", name);
		Tenant entity = new Tenant();
		entity.setName(name);
		save(entity);
		log.debug("新增成功: {}", entity);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name) {
		log.debug("修改租户名称: id={}, name={}", id, name);
		Tenant entity = new Tenant();
		entity.setId(id);
		entity.setName(name);
		updateById(entity);
		log.debug("修改成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Tenant add(String name, String password) {
		Tenant tenant = add(name);
		UserUtils.getUser().setTenantId(tenant.getId());
		User user = iUserService.add(SysConsts.ADMIN_NAME, SysConsts.ADMIN, password);
		iRoleService.addAdmin(user.getId());
		return tenant;
	}

}
