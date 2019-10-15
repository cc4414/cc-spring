package com.example.demo.retry;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cc.cc4414.spring.common.retry.RetryAnnotation;

@FeignClient(name = "retry", url = "http://127.0.0.1:8080/")
public interface RetryClient {
	@RetryAnnotation(2)
	@GetMapping("xxx")
	String test();
}
