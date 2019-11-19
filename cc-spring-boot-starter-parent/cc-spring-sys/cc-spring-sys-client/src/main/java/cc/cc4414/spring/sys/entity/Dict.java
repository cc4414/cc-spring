package cc.cc4414.spring.sys.entity;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict")
@ApiModel(value = "Dict对象", description = "字典表")
public class Dict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public interface AddDict extends Default {
	}

	@ApiModelProperty(value = "名称")
	@Size(max = 32)
	@NotBlank(groups = AddDict.class)
	private String name;

	@ApiModelProperty(value = "字典代码")
	@Size(max = 64)
	@NotBlank(groups = AddDict.class)
	private String code;

	@ApiModelProperty(value = "备注信息")
	@Size(max = 255)
	private String remarks;

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
	@Valid
	private List<DictItem> dictItemList;

}
