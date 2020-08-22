ALTER TABLE t_bankclient
    ADD COLUMN surplier_type INT(1) NOT NULL DEFAULT 0 COMMENT '卡类型（0为天下渠道，1为卡商渠道，默认0）' AFTER updateBy;

