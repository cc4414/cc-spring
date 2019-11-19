package cc.cc4414.spring.sys.service;

import java.util.List;
import java.util.Map;

import cc.cc4414.spring.mybatis.service.ICcService;
import cc.cc4414.spring.sys.entity.DictItem;

/**
 * 字典项表 服务类
 *
 * @author cc 2019年11月15日
 */
public interface IDictItemService extends ICcService<DictItem> {
	/**
	 * 新增字典项
	 * 
	 * @param dictItem 字典项，必传字段: name, dictId, value, label, sort; 可选字段remarks
	 * @return 字典项
	 */
	DictItem add(DictItem dictItem);

	/**
	 * 批量新增字典项
	 * 
	 * @param dictItems 字典项列表
	 */
	void addBatch(List<DictItem> dictItems);

	/**
	 * 修改字典项，参数为null时不修改对应项
	 * 
	 * @param dictItem 字典项，必传字段: id; 可选字段name, value, label, sort, remarks
	 */
	void update(DictItem dictItem);

	/**
	 * 通过字典代码查询可用字典项
	 * 
	 * @param code 字典代码
	 * @return 可用字典项列表
	 */
	List<DictItem> listEnabledByDictCode(String code);

	/**
	 * 根据字典id列表查询每个字典中的字典项列表
	 * 
	 * @param ids 字典id列表
	 * @return key为字典id，value为对应字典id中的字典项列表
	 */
	Map<String, List<DictItem>> listMapByDictIds(List<String> ids);
}
