package cc.cc4414.spring.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import cc.cc4414.spring.auth.model.CcUserDetails;
import cc.cc4414.spring.auth.service.ISysUserService;
import cc.cc4414.spring.sys.entity.Authority;
import cc.cc4414.spring.sys.entity.User;
import cc.cc4414.spring.sys.service.IAuthorityService;
import cc.cc4414.spring.sys.service.IUserService;
import lombok.RequiredArgsConstructor;

/**
 * 用户服务通过本地获取的默认实现类
 * 
 * @author cc 2019年11月24日
 */
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cc-spring.auth", name = "sys-user-service", havingValue = "local")
@ConditionalOnMissingBean(ISysUserService.class)
public class LocalSysUserServiceImpl implements ISysUserService {

	private final IUserService iUserService;

	private final IAuthorityService iAuthorityService;

	@Override
	public CcUserDetails getUserByUsername(String username) {
		User user = iUserService.getByUsername(username);
		if (user == null) {
			return null;
		}
		CcUserDetails userDetails = new CcUserDetails();
		BeanUtils.copyProperties(user, userDetails);
		User userInfo = iUserService.get(user.getId(), false, true);
		List<String> deptIds = userInfo.getDeptList().stream().map(i -> i.getId()).collect(Collectors.toList());
		userDetails.setDeptIds(deptIds);
		if (deptIds.size() > 0) {
			userDetails.setDeptId(deptIds.get(0));
		}
		return userDetails;
	}

	@Override
	public List<String> listAuthorityByUserId(String userId) {
		List<Authority> list = iAuthorityService.listByUserId(userId);
		return list.stream().map(i -> i.getCode()).collect(Collectors.toList());
	}

}
