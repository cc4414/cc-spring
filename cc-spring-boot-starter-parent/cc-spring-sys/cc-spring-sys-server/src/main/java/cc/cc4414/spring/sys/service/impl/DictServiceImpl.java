package cc.cc4414.spring.sys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import cc.cc4414.spring.sys.entity.Dict;
import cc.cc4414.spring.sys.entity.DictItem;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.mapper.DictMapper;
import cc.cc4414.spring.sys.result.SysResultEnum;
import cc.cc4414.spring.sys.service.IDictItemService;
import cc.cc4414.spring.sys.service.IDictService;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@Slf4j
@ConditionalOnMissingBean(IDictService.class)
public class DictServiceImpl extends CcServiceImpl<DictMapper, Dict> implements IDictService {

	@Autowired
	private IDictItemService iDictItemService;

	@Autowired
	private UpdateObservable updateObservable;

	@PostConstruct
	public void init() {
		updateObservable.addObserver(o -> {
			User user = UpdateUtils.getEntity(User::getName, o);
			if (user != null) {
				LambdaUpdateWrapper<Dict> wrapperCreator = Wrappers.lambdaUpdate();
				wrapperCreator.eq(Dict::getCreatorId, user.getId());
				wrapperCreator.set(Dict::getCreatorName, user.getName());
				update(null, wrapperCreator);
				LambdaUpdateWrapper<Dict> wrapperModifier = Wrappers.lambdaUpdate();
				wrapperModifier.eq(Dict::getModifierId, user.getId());
				wrapperModifier.set(Dict::getModifierName, user.getName());
				update(null, wrapperModifier);
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Dict add(String name, String code, String remarks) {
		log.debug("新增字典: name={}, code={}, remarks={}", name, code, remarks);
		Dict entity = new Dict();
		entity.setName(name);
		entity.setCode(code);
		entity.setRemarks(remarks);
		save(entity);
		log.debug("新增成功: {}", entity);
		return entity;
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
	public void update(String id, String name, String code, String remarks) {
		log.debug("修改字典: id={}, name={}, code={}, remarks={}", id, name, code, remarks);
		Dict entity = new Dict();
		entity.setId(id);
		entity.setName(name);
		entity.setCode(code);
		entity.setRemarks(remarks);
		updateById(entity);
		log.debug("修改成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Dict add(String name, String code, String remarks, List<DictItem> dictItems) {
		Dict entity = add(name, code, remarks);
		if (dictItems != null) {
			dictItems.forEach(i -> i.setDictId(entity.getId()));
			iDictItemService.addBatch(dictItems);
		}
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String name, String code, String remarks, List<DictItem> dictItems) {
		update(id, name, code, remarks);
		LambdaQueryWrapper<DictItem> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(DictItem::getDictId, id);
		List<DictItem> list = iDictItemService.list(wrapper);
		iDictItemService.deleteByIds(list.stream().map(BaseEntity::getId).collect(Collectors.toList()));
		iDictItemService.addBatch(dictItems);
	}

	@Override
	public IPage<Dict> page(IPage<Dict> page, Wrapper<Dict> queryWrapper, boolean dictItemList) {
		log.debug("分页查询字典: dictItemList={}", dictItemList);
		IPage<Dict> iPage = page(page, queryWrapper);
		List<Dict> list = iPage.getRecords();
		setList(list, dictItemList);
		return iPage;
	}

	/**
	 * 查询并设置每个字典中对应的字典项列表
	 * 
	 * @param list         字典列表
	 * @param dictItemList 是否查询对应字典项列表
	 */
	private void setList(List<Dict> list, boolean dictItemList) {
		log.debug("开始查询，共{}条", list.size());
		if (CollUtil.isEmpty(list)) {
			return;
		}
		List<String> ids = list.stream().map(BaseEntity::getId).collect(Collectors.toList());
		if (dictItemList) {
			Map<String, List<DictItem>> listMap = iDictItemService.listMapByDictIds(ids);
			list.forEach(i -> i.setDictItemList(listMap.get(i.getId())));
		}
		log.debug("查询并设置成功");
	}

	@Override
	@Cacheable("dict")
	public Dict getEnabledByCode(String code) {
		log.debug("通过字典代码获取可用字典: code={}", code);
		// 根据字典代码查询字典
		LambdaQueryWrapper<Dict> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Dict::getCode, code);
		wrapper.eq(Dict::getDisabled, 0);
		Dict dict = getOne(wrapper);
		if (dict == null) {
			throw new ResultException(SysResultEnum.DICT_DISABLED);
		}
		// 查询可用字典项
		LambdaQueryWrapper<DictItem> dictItemWrapper = Wrappers.lambdaQuery();
		dictItemWrapper.eq(DictItem::getDictId, dict.getId());
		dictItemWrapper.eq(DictItem::getDisabled, 0);
		List<DictItem> dictItemList = iDictItemService.list(dictItemWrapper);
		dict.setDictItemList(dictItemList);
		log.debug("查询成功: {}", dict);
		return dict;
	}

}
