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
 * 角色表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public interface AddRole extends Default {
	}

	@ApiModelProperty(value = "名称")
	@Pattern(regexp = RegexpConsts.ROLE_NAME)
	@NotBlank(groups = AddRole.class)
	private String name;

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
	private String authorityId;

	@TableField(exist = false)
	private List<String> authorityIds;

	@TableField(exist = false)
	private List<Authority> authorityList;

}
