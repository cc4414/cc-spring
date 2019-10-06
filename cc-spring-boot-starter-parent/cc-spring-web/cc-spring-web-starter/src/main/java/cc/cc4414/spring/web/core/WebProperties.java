package cc.cc4414.spring.web.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * web属性配置
 * 
 * @author cc 2019年10月1日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cc-spring.web")
public class WebProperties {
	/** 启用默认切面 */
	private boolean enableDefaultAspect = true;

	/** 启用默认统一异常处理 */
	private boolean enableDefaultAdvice = true;
}
