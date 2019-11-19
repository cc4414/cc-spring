package cc.cc4414.spring.sys.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cc.cc4414.spring.sys.entity.UserDept;
import cc.cc4414.spring.sys.mapper.UserDeptMapper;
import cc.cc4414.spring.sys.service.IUserDeptService;

/**
 * 用户部门表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@ConditionalOnMissingBean(IUserDeptService.class)
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements IUserDeptService {

}
