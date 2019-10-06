package cc.cc4414.spring.common.notice;

/**
 * 通知处理器
 * 
 * @author cc 2019年10月1日
 */
public interface NoticeHandler {
	/**
	 * 发送通知给开发者
	 * 
	 * @param message 消息内容
	 */
	void notice(String message);
}
