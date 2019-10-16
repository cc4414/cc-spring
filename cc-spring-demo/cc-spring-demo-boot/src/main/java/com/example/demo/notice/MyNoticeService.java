package com.example.demo.notice;

//import org.springframework.stereotype.Service;

import cc.cc4414.spring.common.notice.NoticeService;

//@Service
public class MyNoticeService implements NoticeService {
	@Override
	public void notice(String message) {
		System.out.println("发送短信: " + message);
	}
}
