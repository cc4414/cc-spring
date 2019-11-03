package cc.cc4414.spring.mybatis.update;

import lombok.Data;

/**
 * 冗余字段更新信息
 * 
 * @author cc 2019年11月3日
 */
@Data
public class UpdateInfo<T> {
	/** 表名（类名） */
	private String tableName;

	/** 字段名（属性名） */
	private String fieldName;

	/** 更新后的实体 */
	private T data;
}
