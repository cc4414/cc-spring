package cc.cc4414.spring.sys.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cc.cc4414.spring.sys.entity.RoleAuthority;
import cc.cc4414.spring.sys.mapper.RoleAuthorityMapper;
import cc.cc4414.spring.sys.service.IRoleAuthorityService;

/**
 * 角色权限表 服务实现类
 *
 * @author cc 2019年11月15日
 */
@Service
@ConditionalOnMissingBean(IRoleAuthorityService.class)
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority>
		implements IRoleAuthorityService {

}
