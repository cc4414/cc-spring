package cc.cc4414.spring.common;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author cc 2019年10月1日
 */
@Configuration
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableRetry
@Import(CcAutoConfigurationImportSelector.class)
public class CcAutoConfiguration {
}
