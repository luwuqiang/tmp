CREATE TABLE `t_mo_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) NOT NULL COMMENT '学校机构id',
  `cust_id` bigint(20) NOT NULL COMMENT '用户ID',
  `type_id` int(11) DEFAULT NULL COMMENT '申请类型Id',
  `content` varchar(1000) DEFAULT NULL COMMENT '申请理由',
  `mold` tinyint(4) DEFAULT NULL COMMENT '模型：1:请假申请  2:采购申请 3:外出申请 3：报销申请',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '审批状态，0:待审批 1:不同意 2:已同意',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17010000000000 DEFAULT CHARSET=utf8 COMMENT='移动办公申请表';


CREATE TABLE `t_mo_application_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applic_id` bigint(20) NOT NULL COMMENT '关联t_mo_application ',
  `leave_days` tinyint(4) DEFAULT NULL COMMENT '请假天数',
  `begin_time` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假申请表';


CREATE TABLE `t_mo_application_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mold` tinyint(4) NOT NULL COMMENT '模型：1:请假申请  2:采购申请 3:外出申请 3：报销申请',
  `name` varchar(50) DEFAULT NULL COMMENT '申请类型名称',
  `seq` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请类型';



CREATE TABLE `t_mo_approval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) NOT NULL COMMENT '机构学校ID',
  `step` tinyint(4) DEFAULT '1' COMMENT '使用1.2.3 ...来标记流程',
  `cust_id` bigint(20) NOT NULL COMMENT '审核人',
  `applic_id` bigint(20) NOT NULL COMMENT '关联t_mo_application',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批表';

CREATE TABLE `t_mo_approval_step_mold` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) DEFAULT NULL,
  `cust_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `step` tinyint(4) DEFAULT NULL,
  `show_seq` tinyint(4) DEFAULT NULL COMMENT '显示顺序',
  `mold` tinyint(4) DEFAULT NULL COMMENT '模型：1:请假申请  2:采购申请 3:外出申请 3：报销申请',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批流程模型表';
