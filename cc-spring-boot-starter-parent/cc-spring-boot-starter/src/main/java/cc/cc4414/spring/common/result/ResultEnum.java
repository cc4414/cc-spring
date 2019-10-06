package cc.cc4414.spring.common.result;

/**
 * 结果枚举接口，自定义的结果枚举需要实现该接口<br>
 * 可以参考 {@link CommonResultEnum}<br>
 * 
 * @author cc 2019年10月1日
 */
public interface ResultEnum {

	/**
	 * 获取错误码
	 * 
	 * @return 错误码
	 */
	String getCode();

	/**
	 * 获取提示信息
	 * 
	 * @return 提示信息
	 */
	String getMessage();

	/**
	 * 通过code来比较枚举值是否相同
	 * 
	 * @param code 错误码
	 * @return 如果给定字符串与此枚举项的code相同，则为true；否则为false
	 */
	default boolean equalsCode(String code) {
		return getCode().equals(code);
	}

}
