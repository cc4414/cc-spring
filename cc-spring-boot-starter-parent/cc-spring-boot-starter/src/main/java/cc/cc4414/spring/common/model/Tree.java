package cc.cc4414.spring.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 树结构
 * 
 * @author cc 2019年10月1日
 */
@Data
public class Tree<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	private String id;

	/** 标签名 */
	private String label;

	/** 具体内容 */
	private T data;

	/** 子树 */
	private List<Tree<T>> children;

}
