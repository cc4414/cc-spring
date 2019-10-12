package com.example.demo.lombok;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Lombok7 {
	private final LombokService lombokService;

	public void test() {
		lombokService.test();
	}
}
