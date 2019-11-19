package cc.cc4414.spring.sys.entity;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
 * 字典项表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict_item")
@ApiModel(value = "DictItem对象", description = "字典项表")
public class DictItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public interface AddDictItem extends Default {
	}

	@ApiModelProperty(value = "名称")
	@Size(max = 32)
	@NotBlank(groups = AddDictItem.class)
	private String name;

	@ApiModelProperty(value = "字典id")
	@NotBlank(groups = AddDictItem.class)
	private String dictId;

	@ApiModelProperty(value = "数据值")
	@Size(max = 32)
	@NotBlank(groups = AddDictItem.class)
	private String value;

	@ApiModelProperty(value = "标签名")
	@Size(max = 32)
	@NotBlank(groups = AddDictItem.class)
	private String label;

	@ApiModelProperty(value = "排序（升序）")
	@NotNull(groups = AddDictItem.class)
	private Integer sort;

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

}
