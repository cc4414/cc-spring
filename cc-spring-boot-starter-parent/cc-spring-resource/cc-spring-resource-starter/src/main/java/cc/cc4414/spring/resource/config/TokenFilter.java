package cc.cc4414.spring.resource.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import cc.cc4414.spring.common.property.CommonProperties;
import cc.cc4414.spring.resource.util.TokenUtils;
import lombok.RequiredArgsConstructor;

/**
 * token过滤器，判断token是否失效
 * 
 * @author cc 2019年10月21日
 */
@WebFilter
@RequiredArgsConstructor
public class TokenFilter implements Filter {
	private final StringRedisTemplate redisTemplate;

	private final CommonProperties commonProperties;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!commonProperties.isMicroservice()) {
			String value = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
			if (TokenUtils.isExpires(value, redisTemplate)) {
				((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
		}
		chain.doFilter(request, response);
	}
}
