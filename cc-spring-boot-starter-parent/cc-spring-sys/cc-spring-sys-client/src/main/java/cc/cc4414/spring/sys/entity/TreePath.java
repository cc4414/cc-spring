package cc.cc4414.spring.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 闭包表
 * 
 * @author cc 2019年11月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_tree_path")
@ApiModel(value = "TreePath对象", description = "闭包表")
public class TreePath implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "祖先")
	private String ancestor;

	@ApiModelProperty(value = "后代")
	private String descendant;

	@ApiModelProperty(value = "距离")
	private Integer distance;

	@ApiModelProperty(value = "租户id")
	private String tenantId;

	@ApiModelProperty(value = "范围")
	private String scope;

}
