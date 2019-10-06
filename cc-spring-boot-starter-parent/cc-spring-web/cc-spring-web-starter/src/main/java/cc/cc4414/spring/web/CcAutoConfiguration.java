package cc.cc4414.spring.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author cc 2019年10月1日
 */
@Configuration
@EnableSwagger2
@Import(CcAutoConfigurationImportSelector.class)
public class CcAutoConfiguration {
}
