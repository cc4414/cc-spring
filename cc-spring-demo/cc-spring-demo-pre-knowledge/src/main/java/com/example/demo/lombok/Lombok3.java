package com.example.demo.lombok;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lombok3 {
	private static final Logger log = LoggerFactory.getLogger(Lombok3.class);

	public void test() {
		log.info("message");
	}
}
