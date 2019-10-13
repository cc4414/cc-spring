package com.example.demo.async;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("async")
@RequiredArgsConstructor
@Slf4j
public class AsyncController {
	private final AsyncService asyncService;

	@GetMapping("test")
	public void test() {
		asyncService.test();
		log.info("AsyncController");
	}
}
