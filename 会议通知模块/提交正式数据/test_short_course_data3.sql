INSERT INTO `t_mo_course_semester` (`id`,`sid`,`name`,`status`,`create_time`,`update_time`) VALUES (3,2010000063180,'东圃幼儿园幼儿园16-17学年夏季学期',1,20170109150700,20170109150700);

-- 学期周数据
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (21,3,'第一周',20161226000000,1);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (22,3,'第二周',20170102000000,2);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (23,3,'第三周',20170109000000,3);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (24,3,'第四周',20170116000000,4);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (25,3,'第五周',20170123000000,5);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (26,3,'第六周',20170130000000,6);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (27,3,'第七周',20170206000000,7);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (28,3,'第八周',20170213000000,8);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (29,3,'第九周',20170223000000,9);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (30,3,'第十周',20170227000000,10);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (31,3,'第十一周',20170303000000,11);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (32,3,'第十二周',20170313000000,12);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (33,3,'第十三周',20170320000000,13);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (34,3,'第十四周',20170327000000,14);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (35,3,'第十五周',20170403000000,15);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (36,3,'第十六周',20170410000000,16);
INSERT INTO `t_mo_course_week` (`id`,`semester_id`,`name`,`week_mon_time`,`week_seq`) VALUES (37,3,'第十七周',20170417000000,17);

-- 学校课节数据
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (10,2010000063180,'7:30','8:15',1,1);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (11,2010000063180,'8:20','9:05',2,1);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (12,2010000063180,'9:10','9:55',3,1);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (13,2010000063180,'14:00','14:45',4,2);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (14,2010000063180,'14:50','15:35',5,2);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (15,2010000063180,'15:40','16:25',6,2);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (16,2010000063180,'19:30','20:15',7,3);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (17,2010000063180,'20:30','21:15',8,3);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (18,2010000063180,'21:30','22:15',9,3);
INSERT INTO `t_mo_course_school_lesson` (`id`,`sid`,`begin_time`,`end_time`,`lesson_seq`,`time_interval`) VALUES (19,2010000063180,'22:30','23:15',10,3);
