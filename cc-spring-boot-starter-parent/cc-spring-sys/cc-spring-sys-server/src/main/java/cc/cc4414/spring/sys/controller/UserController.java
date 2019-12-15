package cc.cc4414.spring.sys.controller;

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cc.cc4414.spring.resource.util.UserUtils;
import cc.cc4414.spring.sys.constant.RegexpConsts;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.entity.User.ResultPassword;
import cc.cc4414.spring.sys.service.IUserService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import cc.cc4414.spring.web.core.SetNull;
import lombok.RequiredArgsConstructor;

/**
 * 用户表 前端控制器
 *
 * @author cc 2019年11月18日
 */
@Validated
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cc-spring.sys.controller", name = "user", havingValue = "true", matchIfMissing = true)
public class UserController {
	private final IUserService iUserService;

	/**
	 * 新增用户
	 * 
	 * @param name     名称
	 * @param username 用户名
	 * @param password 密码
	 * @return 新增的用户
	 */
	@PostMapping("add")
	@ResultAnnotation
	@SetNull(ResultPassword.class)
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_add')")
	public User add(@Pattern(regexp = RegexpConsts.USER_NAME) @RequestParam(required = false) String name,
			@Pattern(regexp = RegexpConsts.USERNAME) @RequestParam String username,
			@Pattern(regexp = RegexpConsts.PASSWORD) @RequestParam String password) {
		if (name == null) {
			name = username;
		}
		return iUserService.add(name, username, password);
	};

	/**
	 * 新增用户，并给用户添加角色及部门
	 * 
	 * @param user 用户
	 * @return 新增的用户
	 */
	@PostMapping("addUser")
	@ResultAnnotation
	@SetNull(ResultPassword.class)
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_add')")
	public User addUser(@RequestBody @Validated(User.AddUser.class) User user) {
		if (user.getName() == null) {
			user.setName(user.getUsername());
		}
		return iUserService.add(user.getName(), user.getUsername(), user.getPassword(), user.getRoleIds(),
				user.getDeleteIds());
	};

	/**
	 * 根据id删除用户
	 * 
	 * @param id 用户id
	 */
	@PostMapping("delete")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_delete')")
	public void delete(@RequestParam String id) {
		iUserService.deleteById(id);
	}

	/**
	 * 根据id批量删除用户
	 * 
	 * @param user ids为用户id列表
	 */
	@PostMapping("deleteBatch")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_delete')")
	public void deleteBatch(@RequestBody User user) {
		iUserService.deleteByIds(user.getIds());
	}

	/**
	 * 根据id启用用户
	 * 
	 * @param id 用户id
	 */
	@PostMapping("enable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_enable')")
	public void enable(@RequestParam String id) {
		iUserService.setDisabled(id, 0);
	}

	/**
	 * 根据id禁用用户
	 * 
	 * @param id 用户id
	 */
	@PostMapping("disable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_disable')")
	public void disable(@RequestParam String id) {
		iUserService.setDisabled(id, 1);
	}

	/**
	 * 修改用户
	 * 
	 * @param id       用户id
	 * @param name     名称
	 * @param password 密码
	 */
	@PostMapping("update")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_update')")
	public void update(@RequestParam String id,
			@Pattern(regexp = RegexpConsts.USER_NAME) @RequestParam(required = false) String name,
			@Pattern(regexp = RegexpConsts.PASSWORD) @RequestParam(required = false) String password) {
		iUserService.update(id, name, password);
	}

	/**
	 * 修改用户
	 * 
	 * @param user 用户
	 */
	@PostMapping("updateUser")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_update')")
	public void updateUser(@RequestBody @Validated User user) {
		iUserService.update(user.getId(), user.getName(), user.getPassword(), user.getRoleIds(), user.getDeleteIds());
	}

	/**
	 * 增量更新用户中的角色
	 * 
	 * @param user id为用户id，deleteIds为移除的角色id列表，addIds为新增的角色id列表
	 */
	@PostMapping("updateRole")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_update')")
	public void updateRole(@RequestBody User user) {
		iUserService.updateRole(user.getId(), user.getDeleteIds(), user.getAddIds());
	}

	/**
	 * 增量更新用户中的部门
	 * 
	 * @param user id为用户id，deleteIds为移除的部门id列表，addIds为新增的部门id列表
	 */
	@PostMapping("updateDept")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_update')")
	public void updateDept(@RequestBody User user) {
		iUserService.updateDept(user.getId(), user.getDeleteIds(), user.getAddIds());
	}

	/**
	 * 根据id查询用户
	 * 
	 * @param id 用户id
	 * @return 用户
	 */
	@GetMapping("get")
	@ResultAnnotation
	@SetNull(ResultPassword.class)
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_query')")
	public User get(@RequestParam String id) {
		return iUserService.get(id, true, true);
	}

	/**
	 * 分页查询用户
	 * 
	 * @param current 当前页
	 * @param size    每页显示条数
	 * @return 用户分页
	 */
	@GetMapping("page")
	@ResultAnnotation
	@SetNull(ResultPassword.class)
	@PreAuthorize("@pms.inner() || hasAuthority('sys_user_query')")
	public IPage<User> page(long current, long size) {
		IPage<User> page = new Page<>(current, size);
		return iUserService.page(page, Wrappers.emptyWrapper(), true, true);
	}

	/**
	 * 根据用户名查询用户（校验用户所在租户是否存在）
	 * 
	 * @param username 用户名，也可以是"用户名:租户id"
	 * @return 用户，未查询到可用用户时返回null
	 */
	@GetMapping("getByUsername")
	@ResultAnnotation
	public User getByUsername(@RequestParam String username) {
		return iUserService.getByUsername(username);
	}

	/**
	 * 修改密码
	 * 
	 * @param password    新密码
	 * @param oldPassword 原密码
	 */
	@PostMapping("changePassword")
	@ResultAnnotation
	@PreAuthorize("isAuthenticated()")
	public void changePassword(@Pattern(regexp = RegexpConsts.PASSWORD) @RequestParam String password,
			@Pattern(regexp = RegexpConsts.PASSWORD) @RequestParam String oldPassword) {
		iUserService.changePassword(UserUtils.getUser().getId(), password, oldPassword);
	}

	/**
	 * 修改名称
	 * 
	 * @param name 名称
	 */
	@PostMapping("changeName")
	@ResultAnnotation
	@PreAuthorize("isAuthenticated()")
	public void changeName(@Pattern(regexp = RegexpConsts.USER_NAME) @RequestParam String name) {
		iUserService.update(UserUtils.getUser().getId(), name, null);
	}

}
