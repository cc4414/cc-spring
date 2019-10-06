package cc.cc4414.spring.common.notice;

/**
 * 这里不直接实现NoticeHandler接口，而是增加一个此接口<br>
 * 是为了默认实现类可以直接加注解{@code @ConditionalOnMissingBean(NoticeService.class)}来选择装配
 * 
 * @author cc 2019年10月1日
 */
public interface NoticeService extends NoticeHandler {

}
