package com.example.demo.retry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("advanced-retry")
@RequiredArgsConstructor
public class AdvancedRetryController {
	private final AdvancedRetryClient advancedRetryClient;

	@GetMapping("test")
	public String test(String s) {
		return advancedRetryClient.test();
	}
}
