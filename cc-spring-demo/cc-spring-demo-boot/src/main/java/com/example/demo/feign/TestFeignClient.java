package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cc.cc4414.spring.common.feign.FeignAnnotation;
import cc.cc4414.spring.common.result.ResultVO;

@FeignClient(name = "test", url = "http://127.0.0.1:8080/feign")
@FeignAnnotation
public interface TestFeignClient {
	@GetMapping("test1")
	ResultVO<String> test1(@RequestParam("s") String s);
}
