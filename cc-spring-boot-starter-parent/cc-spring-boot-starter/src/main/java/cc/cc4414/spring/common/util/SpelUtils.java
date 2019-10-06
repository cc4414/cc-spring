package cc.cc4414.spring.common.util;

import java.lang.reflect.Method;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import lombok.experimental.UtilityClass;

/**
 * SpEL表达式工具
 * 
 * @author cc 2019年10月1日
 */
@UtilityClass
public class SpelUtils {
	/**
	 * 解析SpEL表达式<br>
	 * 主要用于处理表达式中会使用到参数<br>
	 * 
	 * @param expressionString 表达式字符串
	 * @param method           方法
	 * @param args             方法中的参数
	 * @return 解析结果
	 */
	public String parse(String expressionString, Method method, Object[] args) {
		ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] names = discoverer.getParameterNames(method);
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression(expressionString);
		StandardEvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < names.length; i++) {
			context.setVariable(names[i], args[i]);
		}
		return expression.getValue(context, String.class);
	}
}
