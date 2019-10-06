package cc.cc4414.spring.common.util;

import org.jasypt.util.text.BasicTextEncryptor;

import lombok.experimental.UtilityClass;

/**
 * Jasypt加解密工具
 * 
 * @author cc 2019年10月1日
 */
@UtilityClass
public class JasyptUtils {
	public String encrypt(String password, String message) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword(password);
		return encryptor.encrypt(message);
	}

	public String decrypt(String password, String message) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword(password);
		return encryptor.decrypt(message);
	}
}
