package cc.cc4414.spring.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cc.cc4414.spring.mybatis.service.ICcService;
import cc.cc4414.spring.sys.entity.Dict;
import cc.cc4414.spring.sys.entity.DictItem;

/**
 * 字典表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface IDictService extends ICcService<Dict> {
	/**
	 * 新增字典
	 * 
	 * @param name    名称
	 * @param code    字典代码
	 * @param remarks 备注信息
	 * @return 字典
	 */
	Dict add(String name, String code, String remarks);

	/**
	 * 修改字典，参数为null时不修改对应项
	 * 
	 * @param id      字典id
	 * @param name    名称
	 * @param code    字典代码
	 * @param remarks 备注信息
	 */
	void update(String id, String name, String code, String remarks);

	/**
	 * 新增字典，参数为null时不新增对应项
	 * 
	 * @param name      名称
	 * @param code      字典代码
	 * @param remarks   备注信息
	 * @param dictItems 字典项列表
	 * @return 字典
	 */
	Dict add(String name, String code, String remarks, List<DictItem> dictItems);

	/**
	 * 修改字典，参数为null时不修改对应项
	 * 
	 * @param id        字典id
	 * @param name      名称
	 * @param code      字典代码
	 * @param remarks   备注信息
	 * @param dictItems 字典项列表
	 */
	void update(String id, String name, String code, String remarks, List<DictItem> dictItems);

	/**
	 * 分页查询字典
	 * 
	 * @param page         分页对象
	 * @param queryWrapper 实体对象封装操作类
	 * @param dictItemList 是否查询对应字典项列表
	 * @return 字典分页
	 */
	IPage<Dict> page(IPage<Dict> page, Wrapper<Dict> queryWrapper, boolean dictItemList);

	/**
	 * 通过字典代码获取可用字典
	 * 
	 * @param code 字典代码
	 * @return 字典(包括字典内的字典项列表)
	 */
	Dict getEnabledByCode(String code);

}
