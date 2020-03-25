package cc.cc4414.spring.sys.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cc.cc4414.spring.mybatis.service.impl.CcServiceImpl;
import cc.cc4414.spring.mybatis.update.UpdateObservable;
import cc.cc4414.spring.mybatis.update.UpdateUtils;
import cc.cc4414.spring.sys.entity.Dict;
import cc.cc4414.spring.sys.entity.DictItem;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.mapper.DictItemMapper;
import cc.cc4414.spring.sys.service.IDictItemService;
import cc.cc4414.spring.sys.service.IDictService;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典项表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(IDictItemService.class)
public class DictItemServiceImpl extends CcServiceImpl<DictItemMapper, DictItem> implements IDictItemService {

	@Autowired
	private IDictService iDictService;

	@Autowired
	private UpdateObservable updateObservable;

	@PostConstruct
	public void init() {
		updateObservable.addObserver(o -> {
			User user = UpdateUtils.getEntity(User::getName, o);
			if (user != null) {
				LambdaUpdateWrapper<DictItem> wrapperCreator = Wrappers.lambdaUpdate();
				wrapperCreator.eq(DictItem::getCreatorId, user.getId());
				wrapperCreator.set(DictItem::getCreatorName, user.getName());
				update(null, wrapperCreator);
				LambdaUpdateWrapper<DictItem> wrapperModifier = Wrappers.lambdaUpdate();
				wrapperModifier.eq(DictItem::getModifierId, user.getId());
				wrapperModifier.set(DictItem::getModifierName, user.getName());
				update(null, wrapperModifier);
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "dict", allEntries = true)
	public DictItem add(DictItem dictItem) {
		log.debug("新增字典项: dictItem={}", dictItem);
		DictItem entity = convertAdd(dictItem);
		save(entity);
		// 校验字典是否存在
		iDictService.checkAllIsExist(Collections.singletonList(entity.getDictId()));
		log.debug("新增成功: {}", entity);
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "dict", allEntries = true)
	public void addBatch(List<DictItem> dictItems) {
		log.debug("批量新增字典项: dictItems={}", dictItems);
		if (CollUtil.isEmpty(dictItems)) {
			return;
		}
		List<DictItem> entityList = dictItems.stream().map(this::convertAdd).collect(Collectors.toList());
		saveBatch(entityList);
		// 校验字典是否存在
		iDictService.checkAllIsExist(entityList.stream().map(DictItem::getDictId).collect(Collectors.toList()));
		log.debug("新增成功");
	}

	@Override
	@CacheEvict(value = "dict", allEntries = true)
	public void deleteById(String id) {
		super.deleteById(id);
	}

	@Override
	@CacheEvict(value = "dict", allEntries = true)
	public void deleteByIds(List<String> ids) {
		super.deleteByIds(ids);
	}

	@Override
	@CacheEvict(value = "dict", allEntries = true)
	public void setDisabled(String id, Integer disabled) {
		super.setDisabled(id, disabled);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "dict", allEntries = true)
	public void update(DictItem dictItem) {
		log.debug("修改字典项: dictItem={}", dictItem);
		DictItem entity = convertUpdate(dictItem);
		updateById(entity);
		log.debug("修改成功");
	}

	/**
	 * 获取新增时所需要的字段（避免将不该赋值的字段赋值）
	 * 
	 * @param dictItem 字典项
	 * @return 字典项
	 */
	private DictItem convertAdd(DictItem dictItem) {
		DictItem entity = new DictItem();
		entity.setName(dictItem.getName());
		entity.setDictId(dictItem.getDictId());
		entity.setValue(dictItem.getValue());
		entity.setLabel(dictItem.getLabel());
		entity.setSort(dictItem.getSort());
		entity.setRemarks(dictItem.getRemarks());
		return entity;
	}

	/**
	 * 获取修改时所需要的字段（避免将不该赋值的字段赋值）
	 * 
	 * @param dictItem 字典项
	 * @return 字典项
	 */
	private DictItem convertUpdate(DictItem dictItem) {
		DictItem entity = new DictItem();
		entity.setId(dictItem.getId());
		entity.setName(dictItem.getName());
		entity.setValue(dictItem.getValue());
		entity.setLabel(dictItem.getLabel());
		entity.setSort(dictItem.getSort());
		entity.setRemarks(dictItem.getRemarks());
		return entity;
	}

	@Override
	public List<DictItem> listEnabledByDictCode(String code) {
		Dict dict = iDictService.getEnabledByCode(code);
		return dict.getDictItemList();
	}

	@Override
	public Map<String, List<DictItem>> listMapByDictIds(List<String> ids) {
		log.debug("开始查询每个字典中的字典项列表");
		Map<String, List<DictItem>> map = new HashMap<>(ids.size());
		ids.forEach(i -> map.put(i, new ArrayList<>()));
		LambdaQueryWrapper<DictItem> wrapper = Wrappers.lambdaQuery();
		wrapper.in(DictItem::getDictId, ids);
		List<DictItem> list = list(wrapper);
		list.forEach(i -> map.get(i.getDictId()).add(i));
		log.debug("查询成功");
		return map;
	}

}
