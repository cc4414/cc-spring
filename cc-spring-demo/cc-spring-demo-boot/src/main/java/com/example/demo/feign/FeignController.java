package com.example.demo.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.common.result.ResultVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("feign")
@RequiredArgsConstructor
public class FeignController {
	private final TestFeignClient testFeignClient;

	@GetMapping("test1")
	public ResultVO<String> test1(@RequestParam("s") String s) {
		// 方法中ResultVO的用法只是为了方便该样例，正确用法参照web模块
		if ("1".equals(s)) {
			return new ResultVO<>("1", "错误");
		} else {
			return new ResultVO<String>(s);
		}
	}

	@GetMapping("test2")
	public String test2(String s) {
		return testFeignClient.test1(s).getData();
	}
}
