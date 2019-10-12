package com.example.demo.lombok;

import org.springframework.stereotype.Service;

@Service
public class Lombok6 {
	private final LombokService lombokService;

	public Lombok6(LombokService lombokService) {
		this.lombokService = lombokService;
	}

	public void test() {
		lombokService.test();
	}
}
