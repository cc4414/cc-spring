package com.example.demo.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("feign")
@RequiredArgsConstructor
public class FeignController {
	private final TestFeignClient testFeignClient;

	@GetMapping("test1")
	public String test1(@RequestParam("s") String s) {
		return "test1: " + s;
	}

	@GetMapping("test2")
	public String test2() {
		return testFeignClient.test1("test2");
	}
}
