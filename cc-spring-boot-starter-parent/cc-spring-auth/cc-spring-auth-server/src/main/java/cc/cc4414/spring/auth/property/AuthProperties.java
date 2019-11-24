package cc.cc4414.spring.auth.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 认证服务器属性配置
 * 
 * @author cc 2019年11月24日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cc-spring.auth")
public class AuthProperties {
	/** ISysUserService 实现类 */
	private SysUserServiceEnum sysUserService = SysUserServiceEnum.DEFAULT;
}
