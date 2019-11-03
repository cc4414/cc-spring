package cc.cc4414.spring.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cc.cc4414.spring.mybatis.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 * 
 * @author cc 2019年11月3日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cc-spring.mybatis.tenant")
public class TenantProperties {
	/** 是否启用多租户SQL解析，默认启用 */
	private boolean enabled = true;

	/** 维护租户列名称 */
	private String column = BaseEntity.TENANT_ID;

	/** 多租户的数据表集合 */
	private List<String> tables = new ArrayList<>();
}