package cc.cc4414.spring.resource.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.resource.core.CommonUser;
import cc.cc4414.spring.resource.core.ResourceConsts;
import cc.cc4414.spring.resource.core.TokenContextHolder;
import cc.cc4414.spring.resource.core.UserContextHolder;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 切所有 RestController 注解
 * 
 * @author cc 2019年10月21日
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceAspect {

	private final HttpServletRequest request;

	private final PermissionService permissionService;

	private final ResourceProperties resourceProperties;

	@Around("@within(restController)")
	public Object around(ProceedingJoinPoint pjp, RestController restController) throws Throwable {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		boolean inner = permissionService.inner();
		if (!resourceProperties.isPermitAll() && !AnnotatedElementUtils.hasAnnotation(method, PreAuthorize.class)
				&& !inner) {
			// 不是允许所有人访问、没有PreAuthorize注解、不是内部请求
			throw new AccessDeniedException("不允许访问");
		}
		String value = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.debug("authorization: {}", value);
		CommonUser user = CommonUser.getSys();
		if (value != null && value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase())) {
			// 根据请求头中的jwt解析出用户信息
			String token = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
			Jwt jwt = JwtHelper.decode(token);
			String claims = jwt.getClaims();
			user = JSONUtil.toBean(claims, CommonUser.class);
		}
		if (inner) {
			// 如果是内部请求，传递租户id(用户为sys的情况)
			String tenantId = request.getHeader(ResourceConsts.TENANT_ID);
			if (tenantId != null) {
				user.setTenantId(tenantId);
			}
		}
		try {
			// 线程变量中保存token和user
			TokenContextHolder.setToken(value);
			UserContextHolder.setUser(user);
			return pjp.proceed();
		} finally {
			TokenContextHolder.clear();
			UserContextHolder.clear();
		}
	}

}
