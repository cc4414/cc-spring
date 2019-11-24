package cc.cc4414.spring.auth;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author cc 2019年11月23日
 */
@Configuration
@EnableAuthorizationServer
@ServletComponentScan
@Import(CcAutoConfigurationImportSelector.class)
public class CcAutoConfiguration {
}
