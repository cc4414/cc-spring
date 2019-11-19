package cc.cc4414.spring.sys.controller;

import javax.validation.constraints.Pattern;

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

import cc.cc4414.spring.sys.constant.RegexpConsts;
import cc.cc4414.spring.sys.entity.Tenant;
import cc.cc4414.spring.sys.service.ITenantService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

/**
 * 租户表 前端控制器
 *
 * @author cc 2019年11月18日
 */
@Validated
@RestController
@RequestMapping("/sys/tenant")
@RequiredArgsConstructor
public class TenantController {
	private final ITenantService iTenantService;

	/**
	 * 根据id删除租户
	 * 
	 * @param id 租户id
	 */
	@PostMapping("delete")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void delete(@RequestParam String id) {
		iTenantService.deleteById(id);
	}

	/**
	 * 根据id批量删除租户
	 * 
	 * @param tenant ids为租户id列表
	 */
	@PostMapping("deleteBatch")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void deleteBatch(@RequestBody Tenant tenant) {
		iTenantService.deleteByIds(tenant.getIds());
	}

	/**
	 * 根据id启用租户
	 * 
	 * @param id 租户id
	 */
	@PostMapping("enable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void enable(@RequestParam String id) {
		iTenantService.setDisabled(id, 0);
	}

	/**
	 * 根据id禁用租户
	 * 
	 * @param id 租户id
	 */
	@PostMapping("disable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void disable(@RequestParam String id) {
		iTenantService.setDisabled(id, 1);
	}

	/**
	 * 修改租户名称
	 * 
	 * @param id   租户id
	 * @param name 名称
	 */
	@PostMapping("update")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void update(@RequestParam String id, @Pattern(regexp = RegexpConsts.TENANT_NAME) @RequestParam String name) {
		iTenantService.update(id, name);
	}

	/**
	 * 分页查询租户
	 * 
	 * @param current 当前页
	 * @param size    每页显示条数
	 * @return 租户分页
	 */
	@GetMapping("page")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public IPage<Tenant> page(long current, long size) {
		IPage<Tenant> page = new Page<>(current, size);
		return iTenantService.page(page, Wrappers.emptyWrapper());
	}

	/**
	 * 新增租户，并添加一个管理员账号
	 * 
	 * @param name     名称
	 * @param password 管理员账号的密码，建议由前端随机生成
	 * @return 新增的租户
	 */
	@PostMapping("addTenant")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public Tenant addTenant(@Pattern(regexp = RegexpConsts.TENANT_NAME) @RequestParam String name,
			@Pattern(regexp = RegexpConsts.PASSWORD) String password) {
		return iTenantService.add(name, password);
	}

}
