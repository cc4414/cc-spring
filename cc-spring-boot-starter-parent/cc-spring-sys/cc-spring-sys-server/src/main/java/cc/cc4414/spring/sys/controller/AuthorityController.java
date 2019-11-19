package cc.cc4414.spring.sys.controller;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
import cc.cc4414.spring.sys.entity.Authority;
import cc.cc4414.spring.sys.service.IAuthorityService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

/**
 * 权限表 前端控制器
 *
 * @author cc 2019年11月18日
 */
@Validated
@RestController
@RequestMapping("/sys/authority")
@RequiredArgsConstructor
public class AuthorityController {
	private final IAuthorityService iAuthorityService;

	/**
	 * 新增权限
	 * 
	 * @param name 名称
	 * @param code 权限代码
	 * @param url  接口地址
	 * @param type 类型
	 * @return 新增的权限
	 */
	@PostMapping("add")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public Authority add(@Pattern(regexp = RegexpConsts.AUTHORITY_NAME) @RequestParam String name,
			@Pattern(regexp = RegexpConsts.AUTHORITY_CODE) @RequestParam String code,
			@Size(max = 255) @RequestParam(required = false) String url, Integer type) {
		return iAuthorityService.add(name, code, url, type);
	}

	/**
	 * 根据id删除权限
	 * 
	 * @param id 权限id
	 */
	@PostMapping("delete")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void delete(@RequestParam String id) {
		iAuthorityService.deleteById(id);
	}

	/**
	 * 根据id批量删除权限
	 * 
	 * @param authority ids为权限id列表
	 */
	@PostMapping("deleteBatch")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void deleteBatch(@RequestBody Authority authority) {
		iAuthorityService.deleteByIds(authority.getIds());
	}

	/**
	 * 根据id启用权限
	 * 
	 * @param id 权限id
	 */
	@PostMapping("enable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void enable(@RequestParam String id) {
		iAuthorityService.setDisabled(id, 0);
	}

	/**
	 * 根据id禁用权限
	 * 
	 * @param id 权限id
	 */
	@PostMapping("disable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void disable(@RequestParam String id) {
		iAuthorityService.setDisabled(id, 1);
	}

	/**
	 * 修改权限
	 * 
	 * @param id   权限id
	 * @param name 名称
	 * @param code 权限代码
	 * @param url  接口地址
	 * @param type 类型
	 */
	@PostMapping("update")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void update(@RequestParam String id,
			@Pattern(regexp = RegexpConsts.AUTHORITY_NAME) @RequestParam(required = false) String name,
			@Pattern(regexp = RegexpConsts.AUTHORITY_CODE) @RequestParam(required = false) String code,
			@Size(max = 255) @RequestParam(required = false) String url, Integer type) {
		iAuthorityService.update(id, name, code, url, type);
	}

	/**
	 * 分页查询权限
	 * 
	 * @param current 当前页
	 * @param size    每页显示条数
	 * @return 权限分页
	 */
	@GetMapping("page")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public IPage<Authority> page(long current, long size) {
		IPage<Authority> page = new Page<>(current, size);
		return iAuthorityService.page(page, Wrappers.emptyWrapper(), true);
	}

	/**
	 * 查询所有可分配权限(type为0的权限)
	 * 
	 * @return 可分配权限列表
	 */
	@GetMapping("list")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_authority_list')")
	public List<Authority> list() {
		LambdaQueryWrapper<Authority> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Authority::getType, 0);
		return iAuthorityService.list(wrapper, false);
	}

	/**
	 * 通过用户id查找该用户所拥有的所有可用权限
	 * 
	 * @param userId 用户id
	 * @return 该用户所拥有的所有可用权限
	 */
	@GetMapping("listByUserId")
	@ResultAnnotation
	public List<Authority> listByUserId(@RequestParam String userId) {
		return iAuthorityService.listByUserId(userId);
	}

}
