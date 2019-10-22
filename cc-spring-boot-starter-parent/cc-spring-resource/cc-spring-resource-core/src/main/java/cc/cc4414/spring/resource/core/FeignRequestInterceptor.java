package cc.cc4414.spring.resource.core;

import org.springframework.http.HttpHeaders;

import cc.cc4414.spring.resource.util.UserUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 服务间的请求需要传递用户信息、租户信息、是否是服务间请求等<br>
 * 可以将 @FeignClient 注解中的 configuration 设置为 FeignRequestInterceptor.class<br>
 * 例如: @FeignClient(name = "xxx", configuration = FeignRequestInterceptor.class)
 * 
 * @author cc 2019年10月21日
 */
public class FeignRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		String value = TokenContextHolder.getToken();
		if (value != null && value.toLowerCase().startsWith(ResourceConsts.BEARER_TYPE)) {
			// 传递token，获取用户信息
			template.header(HttpHeaders.AUTHORIZATION, value);
		}
		// 告知下个服务，这是服务间请求。对于外部请求，网关会将这个请求头清空
		template.header(ResourceConsts.INNER_HEADER, ResourceConsts.INNER_HEADER_VALUE);
		// 有时候用户为“系统”，但也需要租户id，所以租户id单独传递
		template.header(ResourceConsts.TENANT_ID, UserUtils.getUser().getTenantId());
	}

}
