package cc.cc4414.spring.sys.entity;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
 * 部门表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dept")
@ApiModel(value = "Dept对象", description = "部门表")
public class Dept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public interface AddDept extends Default {
	}

	@ApiModelProperty(value = "名称")
	@Pattern(regexp = RegexpConsts.DEPT_NAME)
	@NotBlank(groups = AddDept.class)
	private String name;

	/** 部门id */
	@TableField(exist = false)
	private String deptId;

	/** 部门名称 */
	@TableField(exist = false)
	private String deptName;

	@TableField(exist = false)
	private List<String> ids;

	@TableField(exist = false)
	private List<String> deleteIds;

	@TableField(exist = false)
	private List<String> addIds;

	@TableField(exist = false)
	private String userId;

	@TableField(exist = false)
	private List<String> userIds;

	@TableField(exist = false)
	private List<User> userList;

	@TableField(exist = false)
	private String parentId;

}
