package cc.cc4414.spring.gateway.config;

import java.util.List;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import cc.cc4414.spring.resource.core.ResourceConsts;
import cc.cc4414.spring.resource.util.TokenUtils;
import lombok.RequiredArgsConstructor;

/**
 * 全局过滤器配置
 * 
 * @author cc 2019年11月24日
 */
@Component
@RequiredArgsConstructor
public class GlobalFilterConfiguration {

	private final StringRedisTemplate redisTemplate;

	@Bean
	@Order(-1)
	public GlobalFilter checkHeader() {
		// 判断请求头中的token是否失效
		return (exchange, chain) -> {
			HttpHeaders headers = exchange.getRequest().getHeaders();
			List<String> list = headers.get(HttpHeaders.AUTHORIZATION);
			if (list != null) {
				for (String value : list) {
					if (TokenUtils.isExpires(value, redisTemplate)) {
						ServerHttpResponse response = exchange.getResponse();
						response.setStatusCode(HttpStatus.UNAUTHORIZED);
						return response.setComplete();
					}
				}
			}
			return chain.filter(exchange);
		};
	}

	@Bean
	@Order(0)
	public GlobalFilter removeRequestHeader() {
		// 将用于标识是否为内部请求的请求头移除，防止伪造内部请求
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest().mutate()
					.headers(httpHeaders -> httpHeaders.remove(ResourceConsts.INNER_HEADER)).build();

			return chain.filter(exchange.mutate().request(request).build());
		};
	}
}
