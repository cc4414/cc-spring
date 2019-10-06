package cc.cc4414.spring.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 通用属性配置
 * 
 * @author cc 2019年10月1日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cc-spring.common")
public class CommonProperties {
	/** 是否为微服务 */
	private boolean microservice = true;
}
