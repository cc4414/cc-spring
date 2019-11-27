SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority` (
  `id` char(19) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `code` varchar(64) NOT NULL COMMENT '权限代码',
  `url` varchar(255) DEFAULT NULL COMMENT '接口地址',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '类型：0为有该权限可以访问，1为已认证可以访问，2为所有人都可以访问，3为所有人都不能访问，4为只有超级管理员才能访问',
  `deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '逻辑删除：0为未删除，null为删除',
  `disabled` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '禁用：0为未禁用，1为禁用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator_id` char(19) NOT NULL COMMENT '创建者id',
  `creator_name` varchar(32) NOT NULL COMMENT '创建者名称',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `modifier_id` char(19) NOT NULL COMMENT '修改者id',
  `modifier_name` varchar(32) NOT NULL COMMENT '修改者名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`,`deleted`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`,`deleted`) USING BTREE,
  UNIQUE KEY `uk_url` (`url`,`deleted`) USING BTREE,
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE,
  KEY `idx_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` char(19) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '逻辑删除：0为未删除，null为删除',
  `disabled` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '禁用：0为未禁用，1为禁用',
  `tenant_id` char(19) NOT NULL COMMENT '租户id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator_id` char(19) NOT NULL COMMENT '创建者id',
  `creator_name` varchar(32) NOT NULL COMMENT '创建者名称',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `modifier_id` char(19) NOT NULL COMMENT '修改者id',
  `modifier_name` varchar(32) NOT NULL COMMENT '修改者名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`,`tenant_id`,`deleted`) USING BTREE,
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` char(19) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `code` varchar(64) NOT NULL COMMENT '字典代码',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '逻辑删除：0为未删除，null为删除',
  `disabled` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '禁用：0为未禁用，1为禁用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator_id` char(19) NOT NULL COMMENT '创建者id',
  `creator_name` varchar(32) NOT NULL COMMENT '创建者名称',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `modifier_id` char(19) NOT NULL COMMENT '修改者id',
  `modifier_name` varchar(32) NOT NULL COMMENT '修改者名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`,`deleted`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`,`deleted`) USING BTREE,
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` char(19) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `dict_id` char(19) NOT NULL COMMENT '字典id',
  `value` varchar(32) NOT NULL COMMENT '数据值',
  `label` varchar(32) NOT NULL COMMENT '标签名',
  `sort` int(10) unsigned NOT NULL COMMENT '排序（升序）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '逻辑删除：0为未删除，null为删除',
  `disabled` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '禁用：0为未禁用，1为禁用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator_id` char(19) NOT NULL COMMENT '创建者id',
  `creator_name` varchar(32) NOT NULL COMMENT '创建者名称',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `modifier_id` char(19) NOT NULL COMMENT '修改者id',
  `modifier_name` varchar(32) NOT NULL COMMENT '修改者名称',
  PRIMARY KEY (`id`),
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE,
  KEY `idx_dict_id` (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` char(19) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `dept_id` char(19) NOT NULL COMMENT '部门id',
  `dept_name` varchar(32) NOT NULL COMMENT '部门名称',
  `deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '逻辑删除：0为未删除，null为删除',
  `disabled` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '禁用：0为未禁用，1为禁用',
  `tenant_id` char(19) NOT NULL COMMENT '租户id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator_id` char(19) NOT NULL COMMENT '创建者id',
  `creator_name` varchar(32) NOT NULL COMMENT '创建者名称',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `modifier_id` char(19) NOT NULL COMMENT '修改者id',
  `modifier_name` varchar(32) NOT NULL COMMENT '修改者名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`,`tenant_id`,`deleted`) USING BTREE,
  KEY `idx_dept_id` (`dept_id`) USING BTREE,
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority` (
  `id` char(19) NOT NULL COMMENT '主键',
  `role_id` char(19) NOT NULL COMMENT '角色id',
  `authority_id` char(19) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_id_authority_id` (`role_id`,`authority_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE,
  KEY `idx_authority_id` (`authority_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` char(19) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '逻辑删除：0为未删除，null为删除',
  `disabled` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '禁用：0为未禁用，1为禁用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator_id` char(19) NOT NULL COMMENT '创建者id',
  `creator_name` varchar(32) NOT NULL COMMENT '创建者名称',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `modifier_id` char(19) NOT NULL COMMENT '修改者id',
  `modifier_name` varchar(32) NOT NULL COMMENT '修改者名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`,`deleted`) USING BTREE,
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- ----------------------------
-- Table structure for sys_tree_path
-- ----------------------------
DROP TABLE IF EXISTS `sys_tree_path`;
CREATE TABLE `sys_tree_path` (
  `id` char(19) NOT NULL COMMENT '主键',
  `ancestor` char(19) NOT NULL COMMENT '祖先',
  `descendant` char(19) NOT NULL COMMENT '后代',
  `distance` int(10) unsigned NOT NULL COMMENT '距离',
  `tenant_id` char(19) NOT NULL COMMENT '租户id',
  `scope` varchar(32) NOT NULL COMMENT '范围',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ancestor_descendant` (`ancestor`,`descendant`) USING BTREE,
  KEY `idx_ancestor` (`ancestor`) USING BTREE,
  KEY `idx_descendant` (`descendant`) USING BTREE,
  KEY `idx_distance` (`distance`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_scope` (`scope`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='闭包表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` char(19) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` char(68) NOT NULL COMMENT '密码',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户类型：0为普通用户，1为第三方应用',
  `dept_id` char(19) NOT NULL COMMENT '部门id',
  `dept_name` varchar(32) NOT NULL COMMENT '部门名称',
  `deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '逻辑删除：0为未删除，null为删除',
  `disabled` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '禁用：0为未禁用，1为禁用',
  `tenant_id` char(19) NOT NULL COMMENT '租户id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator_id` char(19) NOT NULL COMMENT '创建者id',
  `creator_name` varchar(32) NOT NULL COMMENT '创建者名称',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `modifier_id` char(19) NOT NULL COMMENT '修改者id',
  `modifier_name` varchar(32) NOT NULL COMMENT '修改者名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`,`tenant_id`,`deleted`) USING BTREE,
  KEY `idx_dept_id` (`dept_id`) USING BTREE,
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
  `id` char(19) NOT NULL COMMENT '主键',
  `user_id` char(19) NOT NULL COMMENT '用户id',
  `dept_id` char(19) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_dept_id` (`user_id`,`dept_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_dept_id` (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户部门表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` char(19) NOT NULL COMMENT '主键',
  `user_id` char(19) NOT NULL COMMENT '用户id',
  `role_id` char(19) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_role_id` (`user_id`,`role_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

INSERT INTO `sys_dict` VALUES ('1', '权限类型', 'authority_type', null, '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_dict_item` VALUES ('1', '该权限可访问', '1', '0', '该权限可访问', '0', '有该权限可以访问', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_dict_item` VALUES ('2', '认证可访问', '1', '1', '认证可访问', '1', '已认证可以访问', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_dict_item` VALUES ('3', '所有人可访问', '1', '2', '所有人可访问', '2', '所有人都可以访问', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_dict_item` VALUES ('4', '都不可访问', '1', '3', '都不可访问', '3', '所有人都不能访问', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_dict_item` VALUES ('5', 'admin可访问', '1', '4', 'admin可访问', '4', '只有超级管理员才能访问', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_user` VALUES ('1', '超级管理员', 'admin', '{noop}123456', '0', '0', '系统', '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_role` VALUES ('1', 'admin', '0', '系统', '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_role_authority` VALUES ('1', '1', '1');
INSERT INTO `sys_authority` VALUES ('1', '超级管理员', 'admin', null, '4', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('100', '用户管理', 'sys_user', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('101', '新增用户', 'sys_user_add', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('102', '删除用户', 'sys_user_delete', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('103', '启用用户', 'sys_user_enable', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('104', '禁用用户', 'sys_user_disable', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('105', '修改用户', 'sys_user_update', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('106', '查询用户', 'sys_user_query', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('200', '角色管理', 'sys_role', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('201', '新增角色', 'sys_role_add', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('202', '删除角色', 'sys_role_delete', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('203', '启用角色', 'sys_role_enable', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('204', '禁用角色', 'sys_role_disable', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('205', '修改角色', 'sys_role_update', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('206', '查询角色', 'sys_role_query', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('300', '部门管理', 'sys_dept', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('301', '新增部门', 'sys_dept_add', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('302', '删除部门', 'sys_dept_delete', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('303', '启用部门', 'sys_dept_enable', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('304', '禁用部门', 'sys_dept_disable', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('305', '修改部门', 'sys_dept_update', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('306', '查询部门', 'sys_dept_query', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');
INSERT INTO `sys_authority` VALUES ('401', '查询可分配权限', 'sys_authority_list', null, '0', '0', '0', NOW(), '0', '系统', NOW(), '0', '系统');