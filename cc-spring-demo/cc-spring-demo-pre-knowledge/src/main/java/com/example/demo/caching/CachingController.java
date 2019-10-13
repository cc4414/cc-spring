package com.example.demo.caching;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("caching")
@Slf4j
public class CachingController {
	@GetMapping("test")
	@Cacheable("test")
	public String test(String s) {
		log.info("CachingController: {}", s);
		return s;
	}

	@GetMapping("remove")
	@CacheEvict(value = "test", allEntries = true)
	public void remove() {

	}
}
