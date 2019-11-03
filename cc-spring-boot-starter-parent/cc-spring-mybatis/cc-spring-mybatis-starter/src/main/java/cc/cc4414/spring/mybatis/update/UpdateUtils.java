package cc.cc4414.spring.mybatis.update;

import org.apache.ibatis.reflection.property.PropertyNamer;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;

import cc.cc4414.spring.common.observer.MessagePayload;
import lombok.experimental.UtilityClass;

/**
 * 冗余字段更新工具类
 * 
 * @author cc 2019年11月3日
 */
@UtilityClass
public class UpdateUtils {
	/**
	 * 从接收到的数据中获取指定表名字段名对应的数据，如果表名字段名不对应，则返回null
	 * 
	 * @param func 方法引用
	 * @param o    观察者接收到的数据
	 * @return 更新后的实体
	 */
	@SuppressWarnings("unchecked")
	public <T> T getEntity(SFunction<T, ?> func, Object o) {
		// 类型转换不做校验，如果有异常则抛出
		UpdateInfo<?> info = ((MessagePayload<UpdateInfo<?>>) o).getData();
		UpdateInfo<T> updateInfo = getUpdateInfo(func);
		if (updateInfo.getTableName().equals(info.getTableName())
				&& updateInfo.getFieldName().equals(info.getFieldName())) {
			// 校验表名和字段名
			return (T) info.getData();
		}
		return null;
	}

	/**
	 * 从方法引用中获取类名全路径和字段名
	 * 
	 * @param func 方法引用
	 * @return 冗余字段更新信息的类名和字段名
	 */
	public <T> UpdateInfo<T> getUpdateInfo(SFunction<T, ?> func) {
		SerializedLambda lambda = LambdaUtils.resolve(func);
		UpdateInfo<T> data = new UpdateInfo<>();
		data.setTableName(lambda.getImplClassName());
		data.setFieldName(PropertyNamer.methodToProperty(lambda.getImplMethodName()));
		return data;
	}
}
