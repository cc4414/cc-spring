package com.example.demo.retry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("retry")
@RequiredArgsConstructor
public class RetryController {
	private final RetryClient retryClient;

	@GetMapping("test")
	public String test() {
		return retryClient.test();
	}
}
