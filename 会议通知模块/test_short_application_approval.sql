INSERT INTO `t_sys_prefix_sno` (`lastno`, `note_id`, `opertype`, `prefix`) VALUES (17010000000000, 1, 1, 17);

INSERT INTO `t_sys_schoolorg` (`id`,`cust_id`,`orgmodel_id`,`isshow`,`user_id`,`company_code`,`full_name`,`short_name`,`contactor_sn`,`business_code`,`remark`,`photokey`,`type`,`order_num`,`parent_id`,`leader`,`region_code`,`create_time`,`creator_person`,`update_time`,`updater`,`isValid`,`max_assign_members`) VALUES (10002,NULL,2010000063157,NULL,NULL,NULL,'教务处','教务处',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO `t_sys_schoolroleuser_ref` (`role_id`, `user_id`) VALUES (15010000000000, 4010000005402);
INSERT INTO `t_sys_schoolroleuser_ref` (`role_id`, `user_id`) VALUES (15010000000000, 4010000005168);
INSERT INTO `t_sys_schoolroleuser_ref` (`role_id`, `user_id`) VALUES (15010000000000, 4010000005413);
INSERT INTO `t_sys_schoolroleuser_ref` (`role_id`, `user_id`) VALUES (15010000000000, 4010000005409);
INSERT INTO `t_sys_schoolorgrole_ref` (`org_id`, `role_id`) VALUES ('10002', '15010000000000');

INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (1,'2010000063157', '请假半天', '1', '1', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (2,'2010000063157', '请假1天以上(含)', '2', '1', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (3,'2010000063157', '请假3天以上(含)', '3', '1', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (4,'2010000063157', '审批一', '1', '2', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (5,'2010000063157', '审批二', '2', '2', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (6,'2010000063157', '审批三', '3', '2', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (7,'2010000063157', '审批四', '1', '3', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (8,'2010000063157', '审批五', '2', '3', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (9,'2010000063157', '审批六', '3', '3', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (10,'2010000063157', '审批七', '1', '4', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (11,'2010000063157', '审批八', '2', '4', '10002');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (12,'2010000063157', '审批九', '3', '4', '10002');


INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('1', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('2', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('2', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('2', '4010000005168', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('3', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('3', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('3', '4010000005168', '3');

INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('4', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('5', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('5', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('5', '4010000005168', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('6', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('6', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('6', '4010000005168', '3');

INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('7', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('8', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('8', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('8', '4010000005168', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('9', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('9', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('9', '4010000005168', '3');

INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('10', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('11', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('11', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('11', '4010000005168', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('12', '4010000005413', '1');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('12', '4010000005402', '2');
INSERT INTO `chattest`.`t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('12', '4010000005168', '3');