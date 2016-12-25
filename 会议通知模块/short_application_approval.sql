CREATE TABLE `t_mo_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) NOT NULL COMMENT 'ѧУ����id',
  `cust_id` bigint(20) NOT NULL COMMENT '�û�ID',
  `type_id` int(11) DEFAULT NULL COMMENT '��������Id',
  `content` varchar(1000) DEFAULT NULL COMMENT '��������',
  `mold` tinyint(4) DEFAULT NULL COMMENT 'ģ�ͣ�1:�������  2:�ɹ����� 3:������� 3����������',
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
  `disagree_reason` varchar(300) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������';

CREATE TABLE `t_mo_approval_step_mold` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) DEFAULT NULL,
  `cust_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `step` tinyint(4) DEFAULT NULL,
  `show_seq` tinyint(4) DEFAULT NULL COMMENT '��ʾ˳��',
  `mold` tinyint(4) DEFAULT NULL COMMENT 'ģ�ͣ�1:�������  2:�ɹ����� 3:������� 3����������',
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


CREATE PROCEDURE `p_sys_prefix_sno_get_inc`(IN v_prefix VARCHAR(32),IN v_increment INT,OUT re_lastno  BIGINT)
BEGIN
SELECT lastno INTO re_lastno  FROM t_sys_prefix_sno WHERE prefix=v_prefix;
IF (re_lastno IS NOT null OR re_lastno > 0) THEN
	UPDATE t_sys_prefix_sno SET lastno = lastno + v_increment WHERE prefix=v_prefix;
	COMMIT;
END IF;
END
