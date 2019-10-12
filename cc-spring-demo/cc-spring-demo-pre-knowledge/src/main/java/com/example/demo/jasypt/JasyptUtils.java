package com.example.demo.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;

import lombok.experimental.UtilityClass;

/**
 * Jasypt加解密工具
 * 
 * @author cc 2019年10月1日
 */
@UtilityClass
public class JasyptUtils {
	// 解密方法
	public String encrypt(String password, String message) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword(password);
		return encryptor.encrypt(message);
	}

	// 加密方法
	public String decrypt(String password, String message) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword(password);
		return encryptor.decrypt(message);
	}

	public static void main(String[] args) {
		System.out.println(encrypt("demo", "123456"));
	}
}
