package com.example.demo.lombok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Lombok5 {
	@Autowired
	private LombokService lombokService;

	public void test() {
		lombokService.test();
	}
}
