package com.example.demo.lock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.common.lock.LockAnnotation;

@RestController
@RequestMapping("lock")
public class LockController {
	@LockAnnotation
	@GetMapping("test")
	public void test() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
