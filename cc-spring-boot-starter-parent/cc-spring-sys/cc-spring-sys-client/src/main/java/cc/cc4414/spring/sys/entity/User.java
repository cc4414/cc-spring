package cc.cc4414.spring.sys.entity;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import cc.cc4414.spring.sys.constant.RegexpConsts;
import cc.cc4414.spring.web.core.SetNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value = "User对象", description = "用户表")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public interface AddUser extends Default {
	}

	public interface ResultPassword {
	}

	@ApiModelProperty(value = "名称")
	@Pattern(regexp = RegexpConsts.USER_NAME)
	private String name;

	@ApiModelProperty(value = "用户名")
	@Pattern(regexp = RegexpConsts.USERNAME)
	@NotBlank(groups = AddUser.class)
	private String username;

	@ApiModelProperty(value = "密码")
	@Pattern(regexp = RegexpConsts.PASSWORD)
	@NotBlank(groups = AddUser.class)
	@SetNull(ResultPassword.class)
	private String password;

	@ApiModelProperty(value = "用户类型：0为普通用户，1为第三方应用")
	private Integer type;

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

	@TableField(exist = false)
	private String departmentId;

	@TableField(exist = false)
	private List<String> deptIds;

	@TableField(exist = false)
	private List<Dept> deptList;

}
