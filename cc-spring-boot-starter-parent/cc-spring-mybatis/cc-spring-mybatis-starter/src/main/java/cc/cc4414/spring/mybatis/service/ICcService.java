package cc.cc4414.spring.mybatis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;

import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.mybatis.entity.BaseEntity;
import cc.cc4414.spring.mybatis.result.MybatisResultEnum;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * IService的基础上增加BaseEntity相关方法
 * 
 * @author cc 2019年11月12日
 */
public interface ICcService<T extends BaseEntity> extends IService<T> {
	/**
	 * 创建实体对象
	 * 
	 * @return 实体对象
	 */
	T newT();

	/**
	 * 根据id删除(逻辑删除时自动填充，并且deleted设置为null)
	 * 
	 * @param id 主键id
	 */
	@Transactional(rollbackFor = Exception.class)
	default void deleteById(String id) {
		if (StrUtil.isBlank(id)) {
			return;
		}
		UpdateWrapper<T> wrapper = Wrappers.update();
		wrapper.eq(BaseEntity.ID, id);
		wrapper.set(BaseEntity.DELETED, null);
		update(newT(), wrapper);
	}

	/**
	 * 根据id批量删除(逻辑删除时自动填充，并且deleted设置为null)
	 * 
	 * @param ids 主键id列表
	 */
	@Transactional(rollbackFor = Exception.class)
	default void deleteByIds(List<String> ids) {
		if (CollUtil.isEmpty(ids)) {
			return;
		}
		UpdateWrapper<T> wrapper = Wrappers.update();
		wrapper.in(BaseEntity.ID, ids);
		wrapper.set(BaseEntity.DELETED, null);
		update(newT(), wrapper);
	}

	/**
	 * 根据id设置启用禁用
	 * 
	 * @param id       主键id
	 * @param disabled 是否禁用：0为未禁用，1为禁用
	 */
	@Transactional(rollbackFor = Exception.class)
	default void setDisabled(String id, Integer disabled) {
		T t = newT();
		t.setId(id);
		t.setDisabled(disabled);
		updateById(t);
	}

	/**
	 * 检查是否全部存在，有不存在的抛出异常
	 * 
	 * @param ids 主键id列表
	 * @return 实体列表
	 */
	default List<T> checkAllIsExist(List<String> ids) {
		if (ids == null) {
			return new ArrayList<>();
		}
		List<T> list = (List<T>) listByIds(ids);
		if (list.size() != ids.size()) {
			throw new ResultException(MybatisResultEnum.DATA_NOT_EXIST);
		}
		return list;
	}

	/**
	 * 检查是否全部可用，有不可用的抛出异常，包含了检测是否全部存在
	 * 
	 * @param ids 主键id列表
	 * @return 实体列表
	 */
	default List<T> checkAllIsEnable(List<String> ids) {
		List<T> list = checkAllIsExist(ids);
		list.forEach(i -> {
			if (i.getDisabled() == 1) {
				throw new ResultException(MybatisResultEnum.DATA_DISABLED);
			}
		});
		return list;
	}
}
