CREATE TABLE `t_mo_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) NOT NULL COMMENT '学校机构id',
  `cust_id` bigint(20) NOT NULL COMMENT '用户ID',
  `type_id` int(11) DEFAULT NULL COMMENT '申请类型Id',
  `content` varchar(1000) DEFAULT NULL COMMENT '申请理由',
  `mold` tinyint(4) DEFAULT NULL COMMENT '模型：1:请假申请  2:采购申请 3:外出申请 3：报销申请',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '审批状态，0:待审批 1:审批中 2:不同意 3:已同意',
  `photo_group_id` bigint(20) DEFAULT '0' COMMENT '图组id,关联t_sys_file_info的obj_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='移动办公申请表';

CREATE TABLE `t_mo_application_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applic_id` bigint(20) NOT NULL COMMENT '关联t_mo_application ',
  `leave_days` tinyint(4) DEFAULT NULL COMMENT '请假天数',
  `begin_time` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='请假申请表';

CREATE TABLE `t_mo_application_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mold` tinyint(4) NOT NULL COMMENT '模型：1:请假申请  2:采购申请 3:外出申请 3：报销申请',
  `name` varchar(50) DEFAULT NULL COMMENT '申请类型名称',
  `seq` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='申请类型';

CREATE TABLE `t_mo_approval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) NOT NULL COMMENT '机构学校ID',
  `step` tinyint(4) DEFAULT '1' COMMENT '使用1.2.3 ...来标记流程',
  `cust_id` bigint(20) NOT NULL COMMENT '审核人',
  `applic_id` bigint(20) NOT NULL COMMENT '关联t_mo_application',
  `status` tinyint(4) DEFAULT '0' COMMENT '用户审批结果 0:待审核 1:不同意 2:同意',
  `approval_time` bigint(20) DEFAULT NULL COMMENT '审核时间',
  `disagree_reason` varchar(300) DEFAULT NULL COMMENT '理由',
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='审批流程模型表';


INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (1,1,'事假',1);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (2,1,'病假',2);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (3,1,'年假',3);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (4,1,'调休',4);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (5,1,'婚假',5);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (6,1,'产假',6);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (7,1,'陪产假',7);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (8,1,'其他',8);


CREATE PROCEDURE `p_sys_prefix_sno_get_inc`(IN v_prefix VARCHAR(32),IN v_increment INT,OUT re_lastno  BIGINT)
BEGIN
SELECT lastno INTO re_lastno  FROM t_sys_prefix_sno WHERE prefix=v_prefix;
IF (re_lastno IS NOT null OR re_lastno > 0) THEN
	UPDATE t_sys_prefix_sno SET lastno = lastno + v_increment WHERE prefix=v_prefix;
	COMMIT;
END IF;
END
