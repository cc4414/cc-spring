package cc.cc4414.spring.web.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2的配置
 * 
 * @author cc 2019年10月1日
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

	private final SwaggerProperties swaggerProperties;

	@Bean
	public Docket api() {
		// 配置请求头参数可以传token
		Parameter tokenParameter = new ParameterBuilder().name(HttpHeaders.AUTHORIZATION).description("令牌")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		Parameter innerParameter = new ParameterBuilder().name("Inner").description("内部请求")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		return new Docket(DocumentationType.SWAGGER_2).enable(swaggerProperties.isEnabled())
				.globalOperationParameters(Arrays.asList(tokenParameter, innerParameter));
	}
}
