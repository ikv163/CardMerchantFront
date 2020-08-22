USE typayv2;

ALTER TABLE `t_bankcard`
    ADD COLUMN `surplier_type` INT(1) NOT NULL DEFAULT 0 COMMENT '卡类型（0为天下卡，1为卡商卡，默认0）' AFTER `updateBy`;

ALTER TABLE `t_paychannel`
    ADD COLUMN `surplier_type` INT(1) NOT NULL DEFAULT 0 COMMENT '卡类型（0为天下渠道，1为卡商渠道，默认0）' AFTER `PayPoolID`;

ALTER TABLE t_paychannel
    ADD agent_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '代理编码--关联卡商关联表';

