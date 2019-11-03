package cc.cc4414.spring.mybatis.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.dynamic.datasource.plugin.MasterSlaveAutoRoutingPlugin;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;

/**
 * MybatisPlus配置类
 * 
 * @author cc 2019年11月3日
 */
@Configuration
public class MybatisPlusConfig {
	/**
	 * 多租户属于 SQL 解析部分，依赖 MP 分页插件
	 */
	@Bean
	@ConditionalOnProperty(prefix = "cc-spring.mybatis.tenant", name = "enabled", havingValue = "true", matchIfMissing = true)
	public PaginationInterceptor paginationInterceptor(TenantHandler tenantHandler) {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		List<ISqlParser> sqlParserList = new ArrayList<>();
		TenantSqlParser tenantSqlParser = new TenantSqlParser();
		tenantSqlParser.setTenantHandler(tenantHandler);
		sqlParserList.add(tenantSqlParser);
		paginationInterceptor.setSqlParserList(sqlParserList);
		return paginationInterceptor;
	}

	/**
	 * 读写分离
	 */
	@Bean
	public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin() {
		return new MasterSlaveAutoRoutingPlugin();
	}
}
