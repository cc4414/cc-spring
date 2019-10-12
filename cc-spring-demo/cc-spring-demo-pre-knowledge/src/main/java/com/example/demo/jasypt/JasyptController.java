package com.example.demo.jasypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jasypt")
public class JasyptController {
	@Value("${test}")
	private String test;

	@GetMapping("test")
	public String test() {
		return test;
	}
}
