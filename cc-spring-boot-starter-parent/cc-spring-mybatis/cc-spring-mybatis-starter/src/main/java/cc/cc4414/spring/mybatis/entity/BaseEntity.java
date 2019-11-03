package cc.cc4414.spring.mybatis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础实体，大部分的实体都需要有的字段
 * 
 * @author cc 2019年11月3日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 名称 */
	private String name;

	/** 部门id */
	@TableField(fill = FieldFill.INSERT)
	private String deptId;

	/** 部门名称 */
	@TableField(fill = FieldFill.INSERT)
	private String deptName;

	/** 逻辑删除：0为未删除，1为删除 */
	@TableField(fill = FieldFill.INSERT)
	@TableLogic
	private Integer deleted;

	/** 禁用：0为未禁用，1为禁用 */
	@TableField(fill = FieldFill.INSERT)
	private Integer disabled;

	/** 租户id */
	private String tenantId;

	/** 创建时间 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime gmtCreate;

	/** 创建者id */
	@TableField(fill = FieldFill.INSERT)
	private String creatorId;

	/** 创建者名称 */
	@TableField(fill = FieldFill.INSERT)
	private String creatorName;

	/** 修改时间 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime gmtModified;

	/** 修改者id */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String modifierId;

	/** 修改者名称 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String modifierName;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String DEPT_ID = "dept_id";

	public static final String DEPT_NAME = "dept_name";

	public static final String DELETED = "deleted";

	public static final String DISABLED = "disabled";

	public static final String TENANT_ID = "tenant_id";

	public static final String GMT_CREATE = "gmt_create";

	public static final String CREATOR_ID = "creator_id";

	public static final String CREATOR_NAME = "creator_name";

	public static final String GMT_MODIFIED = "gmt_modified";

	public static final String MODIFIER_ID = "modifier_id";

	public static final String MODIFIER_NAME = "modifier_name";

	public static final String FIELD_ID = "id";

	public static final String FIELD_NAME = "name";

	public static final String FIELD_DEPT_ID = "deptId";

	public static final String FIELD_DEPT_NAME = "deptName";

	public static final String FIELD_DELETED = "deleted";

	public static final String FIELD_DISABLED = "disabled";

	public static final String FIELD_TENANT_ID = "tenantId";

	public static final String FIELD_GMT_CREATE = "gmtCreate";

	public static final String FIELD_CREATOR_ID = "creatorId";

	public static final String FIELD_CREATOR_NAME = "creatorName";

	public static final String FIELD_GMT_MODIFIED = "gmtModified";

	public static final String FIELD_MODIFIER_ID = "modifierId";

	public static final String FIELD_MODIFIER_NAME = "modifierName";
}
