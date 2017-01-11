CREATE TABLE `t_mo_course_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cust_id` bigint(20) NOT NULL,
  `week_id` bigint(20) NOT NULL COMMENT '学期周编号,关联t_mo_course_week',
  `week_day_num` tinyint(4) NOT NULL COMMENT '周几,1:周一 2:周二 3:周三 ...',
  `lesson_id` bigint(20) DEFAULT NULL COMMENT '学校课节编号,关联t_mo_course_school_lesson',
  `course_id` bigint(20) DEFAULT NULL COMMENT '科目编号,关联t_sys_course',
  `class_id` bigint(20) DEFAULT NULL COMMENT '班级编号,关联t_class_info',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8 COMMENT='教师课程安排明细表';

CREATE TABLE `t_mo_course_school_lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` bigint(20) NOT NULL COMMENT '学校id',
  `begin_time` varchar(5) DEFAULT NULL COMMENT '上课时间,格式 HH:mm',
  `end_time` varchar(5) DEFAULT NULL COMMENT '下课时间,格式 HH:mm',
  `lesson_seq` tinyint(4) DEFAULT NULL COMMENT '课节序号',
  `time_interval` tinyint(4) DEFAULT '1' COMMENT '时段,1:早上 2:下午 3:晚上',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='学校课节时段';

CREATE TABLE `t_mo_course_semester` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sid` bigint(20) NOT NULL COMMENT '学校id',
  `name` varchar(30) DEFAULT NULL COMMENT '学期名称',
  `status` tinyint(4) DEFAULT '0' COMMENT '1:当前学期, 0:非当前学期',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='学校学期表';

CREATE TABLE `t_mo_course_week` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `semester_id` bigint(20) NOT NULL COMMENT '学期id，关联t_mo_course_semester',
  `name` varchar(10) DEFAULT NULL,
  `week_mon_time` bigint(20) DEFAULT NULL COMMENT '本周一时间',
  `week_seq` bigint(20) DEFAULT NULL COMMENT '周顺序序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='学校学期周表';

-- 添加班级地址字段
ALTER TABLE t_class_info ADD class_address VARCHAR(64);

-- 年级表
CREATE TABLE `t_grade_info` (
  `id` double DEFAULT NULL,
  `grade_id` double DEFAULT NULL,
  `grade_name` varchar(150) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (1,0,'培训班',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (2,1,'一年级',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (3,2,'二年级',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (4,3,'三年级',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (5,4,'四年级',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (6,5,'五年级',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (7,6,'六年级',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (8,7,'初一',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (9,8,'初二',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (10,9,'初三',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (11,10,'高一',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (12,11,'高二',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (13,12,'高三',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (14,21,'小班',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (15,22,'中班',1);
INSERT INTO `t_grade_info` (`id`,`grade_id`,`grade_name`,`status`) VALUES (16,23,'大班',1);