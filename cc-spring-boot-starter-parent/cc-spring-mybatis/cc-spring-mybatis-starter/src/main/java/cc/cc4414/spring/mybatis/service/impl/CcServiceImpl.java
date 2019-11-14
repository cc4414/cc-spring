package cc.cc4414.spring.mybatis.service.impl;

import java.lang.reflect.Type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.mybatis.entity.BaseEntity;
import cc.cc4414.spring.mybatis.result.MybatisResultEnum;
import cc.cc4414.spring.mybatis.service.ICcService;
import cn.hutool.core.util.TypeUtil;

/**
 * ICcService 实现类
 * 
 * @author cc 2019年11月12日
 */
public class CcServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T>
		implements ICcService<T> {
	@SuppressWarnings("unchecked")
	@Override
	public T newT() {
		try {
			Type type = TypeUtil.getTypeArgument(getClass(), 1);
			Class<?> clazz = TypeUtil.getClass(type);
			return (T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResultException(MybatisResultEnum.NEW_INSTANCE_ERROR);
		}
	}
}
