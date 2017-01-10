ALTER TABLE `t_mo_application` ADD COLUMN `auditing_step` TINYINT(4) NULL DEFAULT 1 COMMENT '正在待审批审批流程' AFTER `state`;
