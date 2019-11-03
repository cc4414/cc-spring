package cc.cc4414.spring.mybatis.config;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import cc.cc4414.spring.resource.core.CommonUser;
import cc.cc4414.spring.resource.util.UserUtils;

/**
 * 自动填充处理器
 * 
 * @author cc 2019年11月3日
 */
@Component
@ConditionalOnMissingBean(MetaObjectHandler.class)
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		CommonUser user = UserUtils.getUser();
		// CommonUser中的部门id和部门名称在用户登录时会使用用户所在部门替换（数据库中为创建该用户者所在部门）
		this.setInsertFieldValByName(BaseEntity.FIELD_DEPT_ID, user.getDeptId(), metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_DEPT_NAME, user.getDeptName(), metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_DELETED, 0, metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_DISABLED, 0, metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_GMT_CREATE, LocalDateTime.now(), metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_CREATOR_ID, user.getId(), metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_CREATOR_NAME, user.getName(), metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_GMT_MODIFIED, LocalDateTime.now(), metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_MODIFIER_ID, user.getId(), metaObject);
		this.setInsertFieldValByName(BaseEntity.FIELD_MODIFIER_NAME, user.getName(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		CommonUser user = UserUtils.getUser();
		this.setUpdateFieldValByName(BaseEntity.FIELD_GMT_MODIFIED, LocalDateTime.now(), metaObject);
		this.setUpdateFieldValByName(BaseEntity.FIELD_MODIFIER_ID, user.getId(), metaObject);
		this.setUpdateFieldValByName(BaseEntity.FIELD_MODIFIER_NAME, user.getName(), metaObject);
	}

	@Override
	public MetaObjectHandler setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject,
			FieldFill fieldFill) {
		if (getFieldValByName(fieldName, metaObject) != null) {
			// 需要自动填充的字段中如果有值，则不自动填充
			return null;
		}
		return MetaObjectHandler.super.setFieldValByName(fieldName, fieldVal, metaObject, fieldFill);
	}
}
