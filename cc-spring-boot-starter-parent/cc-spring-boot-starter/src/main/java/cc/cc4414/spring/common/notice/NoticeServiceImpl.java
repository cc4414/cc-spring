package cc.cc4414.spring.common.notice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认通知方式<br>
 * 直接输出DefaultNoticeException日志<br>
 * 然后可以监控日志中的关键字发送通知<br>
 * 
 * @author cc 2019年10月1日
 */
@Service
@ConditionalOnMissingBean(NoticeService.class)
@Slf4j
public class NoticeServiceImpl implements NoticeService {

	@Override
	public void notice(String message) {
		log.error("DefaultNoticeException: {}", message);
	}

}
