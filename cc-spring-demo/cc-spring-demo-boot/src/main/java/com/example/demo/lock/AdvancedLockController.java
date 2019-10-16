package com.example.demo.lock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.common.lock.LockAnnotation;

@RestController
@RequestMapping("advanced-lock")
public class AdvancedLockController {
	@LockAnnotation(key = "test", spelKey = "#s", time = 2000L)
	@GetMapping("test")
	public void test(String s) {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
