package cc.cc4414.spring.resource.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import cc.cc4414.spring.common.property.CommonProperties;
import cc.cc4414.spring.resource.core.ResourceConsts;
import lombok.RequiredArgsConstructor;

/**
 * 权限判断工具
 * 
 * @author cc 2019年10月21日
 */
@Component("pms")
@RequiredArgsConstructor
public class PermissionService {
	private final HttpServletRequest request;

	private final CommonProperties commonProperties;

	/**
	 * 判断是否为内部请求
	 * 
	 * @return true为内部请求
	 */
	public boolean inner() {
		return commonProperties.isMicroservice()
				&& ResourceConsts.INNER_HEADER_VALUE.equalsIgnoreCase(request.getHeader(ResourceConsts.INNER_HEADER));
	}
}
