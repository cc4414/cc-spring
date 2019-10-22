package cc.cc4414.spring.resource.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

/**
 * 资源服务器属性配置
 * 
 * @author cc 2019年10月21日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cc-spring.resource")
public class ResourceProperties {
	/** JWT签名密钥 */
	private String signingKey = new RandomValueStringGenerator().generate();

	/** 没有设置访问权限的接口是否允许所有人访问，仅建议开发环境中设置为true */
	private boolean permitAll = false;
}
