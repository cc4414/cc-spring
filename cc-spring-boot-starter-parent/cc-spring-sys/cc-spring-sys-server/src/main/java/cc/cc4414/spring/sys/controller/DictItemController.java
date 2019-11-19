package cc.cc4414.spring.sys.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cc.cc4414.spring.sys.entity.DictItem;
import cc.cc4414.spring.sys.service.IDictItemService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

/**
 * 字典项表 前端控制器
 *
 * @author cc 2019年11月18日
 */
@Validated
@RestController
@RequestMapping("/sys/dictItem")
@RequiredArgsConstructor
public class DictItemController {
	private final IDictItemService iDictItemService;

	/**
	 * 新增字典项
	 * 
	 * @param dictItem 字典项
	 * @return 新增的字典项
	 */
	@PostMapping("addDictItem")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public DictItem addDictItem(@RequestBody @Validated(DictItem.AddDictItem.class) DictItem dictItem) {
		return iDictItemService.add(dictItem);
	};

	/**
	 * 根据id删除字典项
	 * 
	 * @param id 字典项id
	 */
	@PostMapping("delete")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void delete(@RequestParam String id) {
		iDictItemService.deleteById(id);
	}

	/**
	 * 根据id批量删除字典项
	 * 
	 * @param dictItem ids为字典项id列表
	 */
	@PostMapping("deleteBatch")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void deleteBatch(@RequestBody DictItem dictItem) {
		iDictItemService.deleteByIds(dictItem.getIds());
	}

	/**
	 * 根据id启用字典项
	 * 
	 * @param id 字典项id
	 */
	@PostMapping("enable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void enable(@RequestParam String id) {
		iDictItemService.setDisabled(id, 0);
	}

	/**
	 * 根据id禁用字典项
	 * 
	 * @param id 字典项id
	 */
	@PostMapping("disable")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void disable(@RequestParam String id) {
		iDictItemService.setDisabled(id, 1);
	}

	/**
	 * 修改字典项
	 * 
	 * @param dictItem 字典项
	 */
	@PostMapping("updateDictItem")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public void updateDictItem(@RequestBody @Validated DictItem dictItem) {
		iDictItemService.update(dictItem);
	}

	/**
	 * 通过字典id查询字典项
	 * 
	 * @param dictId 字典id
	 * @return 字典项列表
	 */
	@GetMapping("listByDictId")
	@ResultAnnotation
	@PreAuthorize("@pms.inner() || hasAuthority('admin')")
	public List<DictItem> listByDictId(@RequestParam String dictId) {
		LambdaQueryWrapper<DictItem> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(DictItem::getDictId, dictId);
		return iDictItemService.list(wrapper);
	}

	/**
	 * 通过字典代码查询可用字典项
	 * 
	 * @param code 字典代码
	 * @return 可用字典项列表
	 */
	@GetMapping("list")
	@ResultAnnotation
	@PreAuthorize("permitAll")
	public List<DictItem> list(@RequestParam String code) {
		return iDictItemService.listEnabledByDictCode(code);
	}
}
