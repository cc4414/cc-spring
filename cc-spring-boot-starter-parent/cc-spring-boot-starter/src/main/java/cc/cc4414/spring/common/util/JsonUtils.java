package cc.cc4414.spring.common.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * json工具(不推荐使用)
 * 
 * @author cc 2019年10月1日
 */
public class JsonUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		MAPPER.registerModule(javaTimeModule);
	}

	/**
	 * 将json字符串解析为对象
	 */
	public static <T> T parse(String json, Class<T> clazz) {
		try {
			return MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			throw new IllegalArgumentException("解析异常", e);
		}
	}

	/**
	 * 将json字符串解析为带泛型的对象
	 */
	public static <T> T parse(String json, TypeReference<T> valueTypeRef) {
		try {
			return MAPPER.readValue(json, valueTypeRef);
		} catch (IOException e) {
			throw new IllegalArgumentException("解析异常", e);
		}
	}

	/**
	 * 将对象格式化为json字符串
	 */
	public static String format(Object obj) {
		try {
			return MAPPER.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException("格式化异常", e);
		}
	}

}
