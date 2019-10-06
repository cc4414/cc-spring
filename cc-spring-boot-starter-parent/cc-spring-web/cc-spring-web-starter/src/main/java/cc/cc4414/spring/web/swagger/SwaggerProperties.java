package cc.cc4414.spring.web.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Swagger2配置属性
 * 
 * @author cc 2019年10月1日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cc-spring.web.swagger")
public class SwaggerProperties {
	/** 是否启用swagger，生成环境建议禁用 */
	private boolean enabled = true;
}
