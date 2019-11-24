package cc.cc4414.spring.auth.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;

import cc.cc4414.spring.auth.constant.AuthConsts;
import cc.cc4414.spring.resource.core.ResourceConsts;
import cc.cc4414.spring.resource.util.TokenUtils;
import lombok.RequiredArgsConstructor;

/**
 * 刷新token过滤器，刷新token前校验refresh_token是否失效
 * 
 * @author cc 2019年11月24日
 */
@WebFilter("/oauth/token")
@RequiredArgsConstructor
public class AuthFilter implements Filter {
	private final StringRedisTemplate redisTemplate;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String grantType = request.getParameter(AuthConsts.GRANT_TYPE);
		if (AuthConsts.REFRESH_TOKEN.equalsIgnoreCase(grantType)) {
			String token = request.getParameter(AuthConsts.REFRESH_TOKEN);
			String value = ResourceConsts.BEARER_TYPE + " " + token;
			if (TokenUtils.isExpires(value, redisTemplate)) {
				((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
		}
		chain.doFilter(request, response);
	}
}
