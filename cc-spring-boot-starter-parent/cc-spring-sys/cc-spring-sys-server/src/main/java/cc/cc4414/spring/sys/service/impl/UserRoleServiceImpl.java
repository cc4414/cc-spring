package cc.cc4414.spring.sys.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cc.cc4414.spring.sys.entity.UserRole;
import cc.cc4414.spring.sys.mapper.UserRoleMapper;
import cc.cc4414.spring.sys.service.IUserRoleService;

/**
 * 用户角色表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@ConditionalOnMissingBean(IUserRoleService.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
