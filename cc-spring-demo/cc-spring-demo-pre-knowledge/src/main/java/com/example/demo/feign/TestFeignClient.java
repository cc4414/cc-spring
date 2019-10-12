package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "test", url = "http://127.0.0.1:8080/feign")
public interface TestFeignClient {
	@GetMapping("test1")
	String test1(@RequestParam("s") String s);
}
