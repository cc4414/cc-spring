package cc.cc4414.spring.sys.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cc.cc4414.spring.common.feign.FeignAnnotation;
import cc.cc4414.spring.common.result.ResultVO;
import cc.cc4414.spring.resource.core.FeignRequestInterceptor;
import cc.cc4414.spring.sys.entity.Authority;
import cc.cc4414.spring.sys.entity.Dept;
import cc.cc4414.spring.sys.entity.Dict;
import cc.cc4414.spring.sys.entity.DictItem;
import cc.cc4414.spring.sys.entity.User;

/**
 * sys客户端
 * 
 * @author cc 2019年11月11日
 */
@FeignClient(name = "${cc-spring.feign.sys:sys}", configuration = FeignRequestInterceptor.class)
@FeignAnnotation
public interface SysClient {
	/**
	 * 根据用户名查询用户（校验用户所在租户是否存在）
	 * 
	 * @param username 用户名
	 * @return 用户，未查询到可用用户时返回null
	 */
	@GetMapping("/sys/user/getByUsername")
	ResultVO<User> getUserByUsername(@RequestParam("username") String username);

	/**
	 * 根据id查询用户
	 * 
	 * @param id 用户id
	 * @return 用户
	 */
	@GetMapping("/sys/user/get")
	ResultVO<User> getUser(@RequestParam("id") String id);

	/**
	 * 通过用户id查找该用户所拥有的所有权限
	 * 
	 * @param userId 用户id
	 * @return 该用户所拥有的所有权限
	 */
	@GetMapping("/sys/authority/listByUserId")
	ResultVO<List<Authority>> listAuthorityByUserId(@RequestParam("userId") String userId);

	/**
	 * 通过id获取部门名称
	 * 
	 * @param id 部门id
	 * @return 部门名称
	 */
	@GetMapping("/sys/dept/getNameById")
	ResultVO<String> getNameById(@RequestParam("id") String id);

	/**
	 * 查询所有子部门
	 * 
	 * @param id 部门id
	 * @return 子部门列表
	 */
	@GetMapping("/sys/dept/listChild")
	ResultVO<List<Dept>> listChild(@RequestParam("id") String id);

	/**
	 * 通过字典代码获取可用字典
	 * 
	 * @param code 字典代码
	 * @return 字典(包括字典内的字典项列表)
	 */
	@GetMapping("/sys/dict/get")
	ResultVO<Dict> get(@RequestParam("code") String code);

	/**
	 * 通过字典代码查询可用字典项
	 * 
	 * @param code 字典代码
	 * @return 可用字典项列表
	 */
	@GetMapping("/sys/dictItem/list")
	ResultVO<List<DictItem>> list(@RequestParam("code") String code);
}
