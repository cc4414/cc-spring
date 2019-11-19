package cc.cc4414.spring.sys.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 租户表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_tenant")
@ApiModel(value = "Tenant对象", description = "租户表")
public class Tenant extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	private String name;

	/** 部门id */
	@TableField(exist = false)
	private String deptId;

	/** 部门名称 */
	@TableField(exist = false)
	private String deptName;

	/** 租户id */
	@TableField(exist = false)
	private String tenantId;

	@TableField(exist = false)
	private List<String> ids;

}
