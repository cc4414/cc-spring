package cc.cc4414.spring.sys.controller;

import javax.validation.constraints.Size;

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

import cc.cc4414.spring.sys.entity.Dict;
import cc.cc4414.spring.sys.service.IDictService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

/**
 * 字典表 前端控制器
 *
 * @author cc 2019年11月18日
 */
@Validated
@RestController
@RequestMapping("/sys/dict")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cc-spring.sys.controller", name = "dict", havingValue = "true", matchIfMissing = true)
public class DictController {
	private final IDictService iDictService;

	/**
	 * 新增字典
	 * 
	 * @param name    名称
	 * @param code    字典代码
	 * @param remarks 备注信息
	 * @return 新增的字典
	 */
	@PostMapping("add")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public Dict add(@Size(max = 32) @RequestParam String name, @Size(max = 64) @RequestParam String code,
			@Size(max = 255) @RequestParam(required = false) String remarks) {
		return iDictService.add(name, code, remarks);
	}

	/**
	 * 新增字典，并给字典添加字典项
	 * 
	 * @param dict 字典
	 * @return 新增的字典
	 */
	@PostMapping("addDict")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public Dict addDict(@RequestBody @Validated(Dict.AddDict.class) Dict dict) {
		// dictItemList的参数未进行非空校验，若为空，则抛出异常
		return iDictService.add(dict.getName(), dict.getCode(), dict.getRemarks(), dict.getDictItemList());
	}

	/**
	 * 根据id删除字典
	 * 
	 * @param id 字典id
	 */
	@PostMapping("delete")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void delete(@RequestParam String id) {
		iDictService.deleteById(id);
	}

	/**
	 * 根据id批量删除字典
	 * 
	 * @param dict ids为字典id列表
	 */
	@PostMapping("deleteBatch")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void deleteBatch(@RequestBody Dict dict) {
		iDictService.deleteByIds(dict.getIds());
	}

	/**
	 * 根据id启用字典
	 * 
	 * @param id 字典id
	 */
	@PostMapping("enable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void enable(@RequestParam String id) {
		iDictService.setDisabled(id, 0);
	}

	/**
	 * 根据id禁用字典
	 * 
	 * @param id 字典id
	 */
	@PostMapping("disable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void disable(@RequestParam String id) {
		iDictService.setDisabled(id, 1);
	}

	/**
	 * 修改字典
	 * 
	 * @param id      字典id
	 * @param name    名称
	 * @param code    字典代码
	 * @param remarks 备注信息
	 */
	@PostMapping("update")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void update(@RequestParam String id, @Size(max = 32) @RequestParam(required = false) String name,
			@Size(max = 64) @RequestParam(required = false) String code,
			@Size(max = 255) @RequestParam(required = false) String remarks) {
		iDictService.update(id, name, code, remarks);
	}

	/**
	 * 修改字典
	 * 
	 * @param dict 字典
	 */
	@PostMapping("updateDict")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void updateDict(@RequestBody @Validated Dict dict) {
		iDictService.update(dict.getId(), dict.getName(), dict.getCode(), dict.getRemarks(), dict.getDictItemList());
	}

	/**
	 * 分页查询字典
	 * 
	 * @param current 当前页
	 * @param size    每页显示条数
	 * @return 字典分页
	 */
	@GetMapping("page")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public IPage<Dict> page(long current, long size) {
		IPage<Dict> page = new Page<>(current, size);
		return iDictService.page(page, Wrappers.emptyWrapper(), true);
	}

	/**
	 * 通过字典代码获取可用字典
	 * 
	 * @param code 字典代码
	 * @return 字典(包括字典内的字典项列表)
	 */
	@GetMapping("get")
	@ResultAnnotation
	@PreAuthorize("permitAll")
	public Dict get(@RequestParam String code) {
		return iDictService.getEnabledByCode(code);
	}
}
