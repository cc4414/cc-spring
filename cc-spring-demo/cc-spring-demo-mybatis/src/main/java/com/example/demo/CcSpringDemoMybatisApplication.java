package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.demo.mapper")
@SpringBootApplication
public class CcSpringDemoMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcSpringDemoMybatisApplication.class, args);
	}

}
