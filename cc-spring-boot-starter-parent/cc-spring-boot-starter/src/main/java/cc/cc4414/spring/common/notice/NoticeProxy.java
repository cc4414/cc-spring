package cc.cc4414.spring.common.notice;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通知需要异步的执行，所以用这个类来代理
 * 
 * @author cc 2019年10月1日
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeProxy implements NoticeHandler {

	private final NoticeService noticeService;

	@Async
	@Override
	public void notice(String message) {
		try {
			noticeService.notice(message);
		} catch (Exception e) {
			log.error("发送通知异常", e);
		}
	}

}
