CREATE TABLE `t_meeting_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) DEFAULT NULL COMMENT '学校/机构ID 外键关联：t_acct_enterprise_info',
  `orgl_id` bigint(20) DEFAULT NULL COMMENT '学校下机构外键关联t_sys_schoolOrg',
  `publish_username` varchar(255) DEFAULT NULL COMMENT '会议发布人',
  `publish_time` bigint(20) NOT NULL,
  `title` varchar(50) DEFAULT NULL COMMENT '会议标题',
  `begin_time` bigint(20) DEFAULT NULL COMMENT '会议开始时间',
  `take_time` int(11) DEFAULT NULL COMMENT '会议时长(分钟)',
  `content` varchar(1000) DEFAULT NULL COMMENT '会议内容',
  `location_id` bigint(20) NOT NULL COMMENT '会议地点Id',
  `sign_scope` int(11) DEFAULT '500' COMMENT '签到/签退地点最大范围',
  `sign_amount` int(11) DEFAULT '0' COMMENT '签到人数',
  `signout_amount` int(11) DEFAULT '0' COMMENT '签退人数',
  `del` tinyint(4) DEFAULT '0' COMMENT '是否删除（0：否，1：是）',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:未开始或进行中，1:已结束',
  `publish_status` tinyint(4) DEFAULT '0' COMMENT '发布状态（0:草稿,1:待发布,2:已发布,3:已撤退）',
  `create_time` bigint(20) DEFAULT NULL,
  `modify_user` bigint(20) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `modify_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15010000000000 DEFAULT CHARSET=utf8 COMMENT='会议通知模块';

CREATE TABLE `t_meeting_personal_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cust_id` bigint(20) NOT NULL COMMENT '客户号',
  `meeting_id` bigint(20) NOT NULL COMMENT '会议信息ID',
  `sign` tinyint(4) DEFAULT '0' COMMENT '签到（0：否，1：是）',
  `sign_time` bigint(20) DEFAULT NULL COMMENT '签到时间',
  `sign_address` varchar(255) DEFAULT NULL COMMENT '签到地址',
  `sign_out` tinyint(4) DEFAULT '0' COMMENT '签退（0：否，1：是）',
  `signout_time` bigint(20) DEFAULT NULL COMMENT '签退时间',
  `signout_address` varchar(255) DEFAULT NULL COMMENT '签退地址',
  `del` tinyint(4) DEFAULT '0' COMMENT '是否删除（0：否，1：是）',
  `create_time` bigint(20) DEFAULT NULL,
  `modify_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='会议个人信息表';

INSERT INTO `t_sys_prefix_sno` (`lastno`, `note_id`, `opertype`, `prefix`) VALUES (15010000000000, 1, 1, 15);

