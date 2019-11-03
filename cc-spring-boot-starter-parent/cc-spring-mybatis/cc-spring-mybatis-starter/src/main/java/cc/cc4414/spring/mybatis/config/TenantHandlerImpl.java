package cc.cc4414.spring.mybatis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;

import cc.cc4414.spring.resource.util.UserUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * 租户处理器
 * 
 * @author cc 2019年11月3日
 */
@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(TenantHandler.class)
public class TenantHandlerImpl implements TenantHandler {

	private final TenantProperties properties;

	@Override
	public Expression getTenantId(boolean where) {
		// 根据当前用户获取租户id(当前用户可以为SYS)
		return new StringValue(UserUtils.getUser().getTenantId());
	}

	@Override
	public String getTenantIdColumn() {
		return properties.getColumn();
	}

	@Override
	public boolean doTableFilter(String tableName) {
		return !properties.getTables().contains(tableName);
	}
}
