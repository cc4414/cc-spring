package cc.cc4414.spring.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器
 * 
 * @author cc 2019年10月21日
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 仅通过注解的方式进行权限控制，这里全部放过
		http.authorizeRequests().anyRequest().permitAll();
	}
}
