package com.example.demo.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulingService {
	@Scheduled(cron = "* * * * * ?")
	public void test() {
		log.info("SchedulingService");
	}
}
