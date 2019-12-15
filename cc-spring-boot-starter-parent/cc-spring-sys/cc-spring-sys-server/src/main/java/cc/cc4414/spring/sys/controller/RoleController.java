package cc.cc4414.spring.sys.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cc.cc4414.spring.sys.constant.RegexpConsts;
import cc.cc4414.spring.sys.entity.Role;
import cc.cc4414.spring.sys.service.IRoleService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

/**
 * 角色表 前端控制器
 *
 * @author cc 2019年11月18日
 */
@Validated
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cc-spring.sys.controller", name = "role", havingValue = "true", matchIfMissing = true)
public class RoleController {
	private final IRoleService iRoleService;

	/**
	 * 新增角色
	 * 
	 * @param name 名称
	 * @return 新增的角色
	 */
	@PostMapping("add")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_add')")
	public Role add(@Pattern(regexp = RegexpConsts.ROLE_NAME) @RequestParam String name) {
		return iRoleService.add(name);
	};

	/**
	 * 新增角色，并给角色添加用户及权限
	 * 
	 * @param role 角色
	 * @return 新增的角色
	 */
	@PostMapping("addRole")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_add')")
	public Role addRole(@RequestBody @Validated(Role.AddRole.class) Role role) {
		return iRoleService.add(role.getName(), role.getUserIds(), role.getAuthorityIds());
	};

	/**
	 * 根据id删除角色
	 * 
	 * @param id 角色id
	 */
	@PostMapping("delete")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_delete')")
	public void delete(@RequestParam String id) {
		iRoleService.deleteById(id);
	}

	/**
	 * 根据id批量删除角色
	 * 
	 * @param role ids为角色id列表
	 */
	@PostMapping("deleteBatch")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_delete')")
	public void deleteBatch(@RequestBody Role role) {
		iRoleService.deleteByIds(role.getIds());
	}

	/**
	 * 根据id启用角色
	 * 
	 * @param id 角色id
	 */
	@PostMapping("enable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_enable')")
	public void enable(@RequestParam String id) {
		iRoleService.setDisabled(id, 0);
	}

	/**
	 * 根据id禁用角色
	 * 
	 * @param id 角色id
	 */
	@PostMapping("disable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_disable')")
	public void disable(@RequestParam String id) {
		iRoleService.setDisabled(id, 1);
	}

	/**
	 * 修改角色名称
	 * 
	 * @param id   角色id
	 * @param name 名称
	 */
	@PostMapping("update")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_update')")
	public void update(@RequestParam String id, @Pattern(regexp = RegexpConsts.ROLE_NAME) @RequestParam String name) {
		iRoleService.update(id, name);
	}

	/**
	 * 修改角色
	 * 
	 * @param role 角色
	 */
	@PostMapping("updateRole")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_update')")
	public void updateRole(@RequestBody @Validated Role role) {
		iRoleService.update(role.getId(), role.getName(), role.getUserIds(), role.getAuthorityIds());
	}

	/**
	 * 增量更新角色中的用户
	 * 
	 * @param role id为角色id，deleteIds为移除的用户id列表， addIds为新增的用户id列表
	 */
	@PostMapping("updateUser")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_update')")
	public void updateUser(@RequestBody Role role) {
		iRoleService.updateUser(role.getId(), role.getDeleteIds(), role.getAddIds());
	}

	/**
	 * 增量更新角色中的权限
	 * 
	 * @param role id为角色id，deleteIds为移除的权限id列表，addIds为新增的权限id列表
	 */
	@PostMapping("updateAuthority")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_update')")
	public void updateAuthority(@RequestBody Role role) {
		iRoleService.updateAuthority(role.getId(), role.getDeleteIds(), role.getAddIds());
	}

	/**
	 * 根据id查询角色
	 * 
	 * @param id 角色id
	 * @return 角色
	 */
	@GetMapping("get")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_query')")
	public Role get(@RequestParam String id) {
		return iRoleService.get(id, true, true);
	}

	/**
	 * 查询所有角色
	 * 
	 * @return 角色列表
	 */
	@GetMapping("list")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_query')")
	public List<Role> list() {
		return iRoleService.list(Wrappers.emptyWrapper(), true, true);
	}

	/**
	 * 分页查询角色
	 * 
	 * @param current 当前页
	 * @param size    每页显示条数
	 * @return 角色分页
	 */
	@GetMapping("page")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_query')")
	public IPage<Role> page(long current, long size) {
		IPage<Role> page = new Page<>(current, size);
		return iRoleService.page(page, Wrappers.emptyWrapper(), true, true);
	}

	/**
	 * 查询所有可用角色
	 * 
	 * @return 可用角色列表
	 */
	@GetMapping("listEnabled")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_role_query')")
	public List<Role> listEnabled() {
		LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Role::getDisabled, 0);
		return iRoleService.list(wrapper, false, false);
	}

}
