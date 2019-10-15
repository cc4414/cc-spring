package com.example.demo.notice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.common.notice.NoticeAnnotation;

@RestController
@RequestMapping("notice")
public class NoticeController {
	@NoticeAnnotation
	@GetMapping("test")
	public void test() {
		System.out.println(1 / 0);
	}
}
