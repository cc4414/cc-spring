SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mybatis_test
-- ----------------------------
DROP TABLE IF EXISTS `mybatis_test`;
CREATE TABLE `mybatis_test` (
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
  UNIQUE KEY `uk_name` (`name`,`deleted`) USING BTREE,
  KEY `idx_dept_id` (`dept_id`) USING BTREE,
  KEY `idx_deleted` (`deleted`) USING BTREE,
  KEY `idx_disabled` (`disabled`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试表';
