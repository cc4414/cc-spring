package com.example.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Test;
import com.example.demo.service.ITestService;

import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mybatis/test")
@RequiredArgsConstructor
public class TestController {
	private final ITestService iTestService;

	@PreAuthorize("permitAll")
	@PostMapping("add")
	@ResultAnnotation
	public void add(String name) {
		Test test = new Test();
		test.setName(name);
		iTestService.save(test);
	}

	@PreAuthorize("permitAll")
	@GetMapping("list")
	@ResultAnnotation
	public List<Test> list() {
		return iTestService.list();
	}
}
