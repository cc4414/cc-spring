package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class CcSpringDemoPreKnowledgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcSpringDemoPreKnowledgeApplication.class, args);
	}

}
