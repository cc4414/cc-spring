package cc.cc4414.spring.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.scripting.support.StandardScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;

import lombok.experimental.UtilityClass;

/**
 * 其他工具包
 * 
 * @author cc 2019年10月1日
 */
@UtilityClass
public class OtherUtils {
	/**
	 * 分批处理
	 * 
	 * @param fun  处理的方法
	 * @param list 需要处理的列表
	 * @param size 每批次的数量
	 * @return 处理结果
	 */
	public <T, R> List<R> batch(Function<List<T>, List<R>> fun, List<T> list, int size) {
		List<R> result = new ArrayList<>();
		int s = list.size();
		for (int i = 0; i < s; i += size) {
			result.addAll(fun.apply(list.subList(i, Math.min(i + size, s))));
		}
		return result;
	}

	/**
	 * 执行js脚本
	 * 
	 * @param script           js脚本
	 * @param argumentBindings 脚本中的变量的值
	 * @return 脚本执行结果
	 */
	public String evaluateJavaScript(String script, Map<String, Object> argumentBindings) {
		StandardScriptEvaluator evaluator = new StandardScriptEvaluator();
		evaluator.setLanguage("javascript");
		return evaluator.evaluate(new StaticScriptSource(script), argumentBindings).toString();
	}
}
