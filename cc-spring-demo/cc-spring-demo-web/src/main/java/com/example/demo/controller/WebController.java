package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.web.core.ResultAnnotation;

@RestController
public class WebController {
	@GetMapping("test")
	@ResultAnnotation
	public Integer test(int i) {
		return i;
	}
}
