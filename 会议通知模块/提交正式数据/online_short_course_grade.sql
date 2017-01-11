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