INSERT INTO `t_sys_prefix_sno` (`lastno`, `note_id`, `opertype`, `prefix`) VALUES (17010000000000, 1, 1, 17);

INSERT INTO `t_sys_schoolroleuser_ref` (`role_id`, `user_id`) VALUES (15010000000000, 4010000005067);
INSERT INTO `t_sys_schoolroleuser_ref` (`role_id`, `user_id`) VALUES (15010000000000, 4010000005031);
INSERT INTO `t_sys_schoolroleuser_ref` (`role_id`, `user_id`) VALUES (15010000000000, 4010000005057);
INSERT INTO `t_sys_schoolorgrole_ref` (`org_id`, `role_id`) VALUES ('10001', '15010000000000');

INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (1,'1110000000001', '请假半天', '1', '1', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (2,'1110000000001', '请假1天以上(含)', '2', '1', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (3,'1110000000001', '请假3天以上(含)', '3', '1', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (4,'1110000000001', '采购审批流程1', '1', '2', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (5,'1110000000001', '采购审批流程2', '2', '2', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (6,'1110000000001', '采购审批流程3', '3', '2', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (7,'1110000000001', '外出审批流程1', '1', '3', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (8,'1110000000001', '外出审批流程2', '2', '3', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (9,'1110000000001', '外出审批流程3', '3', '3', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (10,'1110000000001', '报销审批流程1', '1', '4', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (11,'1110000000001', '报销审批流程2', '2', '4', '10001');
INSERT INTO `t_mo_approval_step_mold` (`id`, `enterprise_id`, `name`, `show_seq`, `mold`, `orgl_id`) VALUES (12,'1110000000001', '报销审批流程3', '3', '4', '10001');


INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('1', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('2', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('2', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('2', '4010000005031', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('3', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('3', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('3', '4010000005031', '3');

INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('4', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('5', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('5', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('5', '4010000005031', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('6', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('6', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('6', '4010000005031', '3');

INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('7', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('8', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('8', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('8', '4010000005031', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('9', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('9', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('9', '4010000005031', '3');

INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('10', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('11', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('11', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('11', '4010000005031', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('12', '4010000005057', '1');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('12', '4010000005067', '2');
INSERT INTO `t_mo_approval_step_mold_item` (`step_mold_id`, `cust_id`, `step`) VALUES ('12', '4010000005031', '3');