package cc.cc4414.spring.sys.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cc.cc4414.spring.common.model.Tree;
import cc.cc4414.spring.sys.constant.RegexpConsts;
import cc.cc4414.spring.sys.constant.SysConsts;
import cc.cc4414.spring.sys.entity.Dept;
import cc.cc4414.spring.sys.service.IDeptService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

/**
 * 部门表 前端控制器
 *
 * @author cc 2019年11月18日
 */
@Validated
@RestController
@RequestMapping("/sys/dept")
@RequiredArgsConstructor
public class DeptController {
	private final IDeptService iDeptService;

	/**
	 * 新增部门，并给部门添加父节点
	 * 
	 * @param name     名称
	 * @param parentId 父节点id
	 * @return 新增的部门
	 */
	@PostMapping("addDept")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_add')")
	public Dept addDept(@Pattern(regexp = RegexpConsts.DEPT_NAME) @RequestParam String name,
			@RequestParam String parentId) {
		return iDeptService.add(name, parentId, null);
	};

	/**
	 * 根据id删除部门及其子部门
	 * 
	 * @param id 部门id
	 */
	@PostMapping("delete")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_delete')")
	public void delete(@RequestParam String id) {
		iDeptService.deleteById(id);
	}

	/**
	 * 根据id启用部门
	 * 
	 * @param id 部门id
	 */
	@PostMapping("enable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_enable')")
	public void enable(@RequestParam String id) {
		iDeptService.setDisabled(id, 0);
	}

	/**
	 * 根据id禁用部门
	 * 
	 * @param id 部门id
	 */
	@PostMapping("disable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_disable')")
	public void disable(@RequestParam String id) {
		iDeptService.setDisabled(id, 1);
	}

	/**
	 * 修改部门名称
	 * 
	 * @param id   部门id
	 * @param name 名称
	 */
	@PostMapping("update")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_update')")
	public void update(@RequestParam String id, @Pattern(regexp = RegexpConsts.DEPT_NAME) @RequestParam String name) {
		iDeptService.update(id, name);
	}

	/**
	 * 修改部门
	 * 
	 * @param id       部门id
	 * @param name     名称
	 * @param parentId 父节点id
	 */
	@PostMapping("updateDept")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_update')")
	public void updateDept(@RequestParam String id, @Pattern(regexp = RegexpConsts.DEPT_NAME) @RequestParam String name,
			@RequestParam String parentId) {
		iDeptService.update(id, name, parentId, null);
	}

	/**
	 * 查询所有部门
	 * 
	 * @return 部门列表
	 */
	@GetMapping("list")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_query')")
	public List<Dept> list() {
		return iDeptService.list(Wrappers.emptyWrapper(), true);
	}

	/**
	 * 分页查询部门
	 * 
	 * @param current 当前页
	 * @param size    每页显示条数
	 * @return 部门分页
	 */
	@GetMapping("page")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_query')")
	public IPage<Dept> page(long current, long size) {
		IPage<Dept> page = new Page<>(current, size);
		return iDeptService.page(page, Wrappers.emptyWrapper(), true);
	}

	/**
	 * 查询所有可用部门
	 * 
	 * @return 可用部门列表
	 */
	@GetMapping("listEnabled")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_query')")
	public List<Dept> listEnabled() {
		LambdaQueryWrapper<Dept> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Dept::getDisabled, 0);
		return iDeptService.list(wrapper, false);
	}

	/**
	 * 通过id获取部门名称
	 * 
	 * @param id 部门id
	 * @return 部门名称
	 */
	@GetMapping("getNameById")
	@ResultAnnotation
	public String getNameById(@RequestParam String id) {
		return iDeptService.getNameById(id);
	}

	/**
	 * 查询所有根节点部门
	 * 
	 * @return 根节点部门列表
	 */
	@GetMapping("listRoot")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_query')")
	public List<Dept> listRoot() {
		return iDeptService.listRootDeptNodes();
	}

	/**
	 * 查询所有子部门
	 * 
	 * @param id 部门id
	 * @return 子部门列表
	 */
	@GetMapping("listChild")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_query')")
	public List<Dept> listChild(@RequestParam String id) {
		return iDeptService.listChildDeptNodes(id);
	}

	/**
	 * 查询id下的部门树，若id为"0"或者null，则查询完整的部门树
	 * 
	 * @param id 部门id
	 * @return 部门树
	 */
	@GetMapping("tree")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('sys_dept_query')")
	public List<Tree<Dept>> tree(String id) {
		return iDeptService.treeDeptNodes(id == null ? SysConsts.ID : id);
	}

}
