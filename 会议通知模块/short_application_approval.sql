CREATE TABLE `t_mo_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) NOT NULL COMMENT 'ѧУ����id',
  `cust_id` bigint(20) NOT NULL COMMENT '�û�ID',
  `type_id` int(11) DEFAULT NULL COMMENT '��������Id',
  `content` varchar(1000) DEFAULT NULL COMMENT '��������',
  `mold` tinyint(4) DEFAULT NULL COMMENT 'ģ�ͣ�1:�������  2:�ɹ����� 3:������� 4����������',
  `create_time` bigint(20) DEFAULT NULL COMMENT '����ʱ��',
  `update_time` bigint(20) DEFAULT NULL COMMENT '����ʱ��',
  `status` tinyint(4) DEFAULT '0' COMMENT '����״̬��0:������ 1:������ 2:��ͬ�� 3:��ͬ��',
  `photo_group_id` bigint(20) DEFAULT '0' COMMENT 'ͼ��id,����t_sys_file_info��obj_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='�ƶ��칫�����';

CREATE TABLE `t_mo_application_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applic_id` bigint(20) NOT NULL COMMENT '����t_mo_application ',
  `leave_days` tinyint(4) DEFAULT NULL COMMENT '�������',
  `begin_time` bigint(20) DEFAULT NULL COMMENT '��ʼʱ��',
  `end_time` bigint(20) DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='��������';


CREATE TABLE `t_mo_application_account` (
  `id` bigint(20) NOT NULL,
  `applic_id` bigint(20) DEFAULT NULL,
  `amount` double DEFAULT '0' COMMENT '�ܽ��',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='���������';

CREATE TABLE `t_mo_application_account_item` (
  `id` int(11) NOT NULL,
  `applic_id` bigint(20) NOT NULL,
  `amount` double DEFAULT '0',
  `detail_desc` varchar(300) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������ϸ��';


CREATE TABLE `t_mo_application_purchase` (
  `id` bigint(20) NOT NULL,
  `applic_id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL COMMENT '�ܽ��',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='�ɹ������';

CREATE TABLE `t_mo_application_purchase_item` (
  `id` bigint(20) NOT NULL,
  `applic_id` bigint(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '��Ʒ����',
  `amount` int(11) DEFAULT '0' COMMENT '������',
  `ref_price` double DEFAULT '0' COMMENT '�ο��۸�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ɹ���ϸ';

CREATE TABLE `t_mo_application_out` (
  `id` bigint(20) NOT NULL,
  `applic_id` bigint(20) DEFAULT NULL,
  `begin_time` bigint(20) DEFAULT NULL COMMENT '��ʼʱ��',
  `end_time` bigint(20) DEFAULT NULL COMMENT '����ʱ��',
  `take_hours` double DEFAULT NULL COMMENT '�������ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='��������';


CREATE TABLE `t_mo_approval_step_mold` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '����ģ������',
  `show_seq` tinyint(4) DEFAULT NULL COMMENT '��ʾ˳��',
  `mold` tinyint(4) DEFAULT NULL COMMENT 'ģ�ͣ�1:�������  2:�ɹ����� 3:������� 3����������',
  `orgl_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='��������ģ�ͱ�';

CREATE TABLE `t_mo_approval_step_mold_item` (
  `id` bigint(20) NOT NULL,
  `step_mold_id` bigint(20) DEFAULT NULL,
  `cust_id` bigint(20) DEFAULT NULL COMMENT '������ID',
  `step` tinyint(4) DEFAULT NULL COMMENT 'ʹ��1.2.3 ...���������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��������ģ����ϸ��';

CREATE TABLE `t_mo_application_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mold` tinyint(4) NOT NULL COMMENT 'ģ�ͣ�1:�������  2:�ɹ����� 3:������� 3����������',
  `name` varchar(50) DEFAULT NULL COMMENT '������������',
  `seq` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='��������';


CREATE TABLE `t_mo_approval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) NOT NULL COMMENT '����ѧУID',
  `step` tinyint(4) DEFAULT '1' COMMENT 'ʹ��1.2.3 ...���������',
  `cust_id` bigint(20) NOT NULL COMMENT '�����',
  `applic_id` bigint(20) NOT NULL COMMENT '����t_mo_application',
  `status` tinyint(4) DEFAULT '0' COMMENT '�û�������� 0:����� 1:��ͬ�� 2:ͬ��',
  `approval_time` bigint(20) DEFAULT NULL COMMENT '���ʱ��',
  `reason` varchar(300) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='������';

CREATE TABLE `t_mo_approval_step_mold` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '����ģ������',
  `show_seq` tinyint(4) DEFAULT NULL COMMENT '��ʾ˳��',
  `mold` tinyint(4) DEFAULT NULL COMMENT 'ģ�ͣ�1:�������  2:�ɹ����� 3:������� 3����������',
  `orgl_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='��������ģ�ͱ�';


INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (1,1,'�¼�',1);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (2,1,'����',2);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (3,1,'���',3);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (4,1,'����',4);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (5,1,'���',5);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (6,1,'����',6);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (7,1,'�����',7);
INSERT INTO `t_mo_application_type` (`id`,`mold`,`name`,`seq`) VALUES (8,1,'����',8);


DELIMITER $$
CREATE PROCEDURE `p_getAndIncrementSysPrefixSno`(
IN v_prefix VARCHAR(32),
IN v_increment INT,
OUT re_lastno  BIGINT
)
    COMMENT '���ڷ���������ţ�������һ������'
BEGIN
SELECT lastno INTO re_lastno  FROM t_sys_prefix_sno WHERE prefix=v_prefix;
IF (re_lastno IS NOT null OR re_lastno > 0) THEN
	UPDATE t_sys_prefix_sno SET lastno = lastno + v_increment WHERE prefix=v_prefix;
	COMMIT;
END IF;
END$$
DELIMITER ;

