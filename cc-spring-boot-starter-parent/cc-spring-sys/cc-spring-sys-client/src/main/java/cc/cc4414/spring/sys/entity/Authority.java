package cc.cc4414.spring.sys.entity;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import cc.cc4414.spring.sys.constant.RegexpConsts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 权限表
 *
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_authority")
@ApiModel(value = "Authority对象", description = "权限表")
public class Authority extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public interface AddAuthority extends Default {
	}

	@ApiModelProperty(value = "名称")
	@Pattern(regexp = RegexpConsts.AUTHORITY_NAME)
	@NotBlank(groups = AddAuthority.class)
	private String name;

	@ApiModelProperty(value = "权限代码")
	@Pattern(regexp = RegexpConsts.AUTHORITY_CODE)
	@NotBlank(groups = AddAuthority.class)
	private String code;

	@ApiModelProperty(value = "接口地址")
	@Size(max = 255)
	private String url;

	@ApiModelProperty(value = "类型：0为有该权限可以访问，1为已认证可以访问，2为所有人都可以访问，3为所有人都不能访问，4为只有超级管理员才能访问")
	private Integer type;

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

	@TableField(exist = false)
	private List<String> deleteIds;

	@TableField(exist = false)
	private List<String> addIds;

	@TableField(exist = false)
	private String roleId;

	@TableField(exist = false)
	private List<String> roleIds;

	@TableField(exist = false)
	private List<Role> roleList;
}
