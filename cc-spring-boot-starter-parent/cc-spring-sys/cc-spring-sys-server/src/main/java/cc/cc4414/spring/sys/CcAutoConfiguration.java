package cc.cc4414.spring.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author cc 2019年11月11日
 */
@Configuration
@MapperScan("cc.cc4414.spring.sys.mapper")
@Import(CcAutoConfigurationImportSelector.class)
public class CcAutoConfiguration {
}
