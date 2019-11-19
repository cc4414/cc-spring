package cc.cc4414.spring.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户部门表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_dept")
@ApiModel(value = "UserDept对象", description = "用户部门表")
public class UserDept implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "部门id")
	private String deptId;

}
