package cc.cc4414.spring.common.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

/**
 * 各种锁的 LockRegistry，按条件装配，这里暂时固定使用 redis分布式锁
 * 
 * @author cc 2019年10月1日
 */
@Configuration
public class LockConfiguration {
	@Bean
	@ConditionalOnMissingBean(LockRegistry.class)
	public LockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
		return new RedisLockRegistry(redisConnectionFactory, "lock");
	}
}
