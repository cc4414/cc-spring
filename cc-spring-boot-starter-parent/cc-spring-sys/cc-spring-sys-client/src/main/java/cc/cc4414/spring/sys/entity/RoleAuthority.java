package cc.cc4414.spring.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色权限表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_authority")
@ApiModel(value = "RoleAuthority对象", description = "角色权限表")
public class RoleAuthority implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "角色id")
	private String roleId;

	@ApiModelProperty(value = "权限id")
	private String authorityId;

}
