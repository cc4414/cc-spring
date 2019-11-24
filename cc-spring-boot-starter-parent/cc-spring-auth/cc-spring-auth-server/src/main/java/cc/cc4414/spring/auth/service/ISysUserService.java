package cc.cc4414.spring.auth.service;

import java.util.List;

import cc.cc4414.spring.auth.model.CcUserDetails;

/**
 * 用户服务类
 * 
 * @author cc 2019年11月24日
 */
public interface ISysUserService {
	/**
	 * 通过用户名获取用户详情(注意用户部门为用户所在部门，如果用户在多个部门，则默认第一个)
	 * 
	 * @param username 用户名
	 * @return 用户详情
	 */
	CcUserDetails getUserByUsername(String username);

	/**
	 * 通过用户id获取用户拥有的所有权限
	 * 
	 * @param userId 用户id
	 * @return 用户拥有的所有权限
	 */
	List<String> listAuthorityByUserId(String userId);
}
