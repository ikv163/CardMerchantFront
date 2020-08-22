
-- ----------------------------
-- 支付渠道表  增加字段 2020年2月12日13:47:23
-- ----------------------------

ALTER TABLE t_paychannel ADD `PayPoolID` INT(11)  NOT NULL DEFAULT 0 COMMENT '支付池ID';


-- ----------------------------
-- 银行卡提款订单  增加字段 2020年2月24日16:52:42
-- ----------------------------
ALTER TABLE t_withdraworder ADD `allotuserid` INT(11)  NOT NULL DEFAULT 0 COMMENT '分配人员id';
ALTER TABLE t_withdraworder ADD `allotstatus` INT(11)  NOT NULL DEFAULT 0 COMMENT '0:未分配,1:已分配';
ALTER TABLE t_withdraworder ADD `allottime` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间';
