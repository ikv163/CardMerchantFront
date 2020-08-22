-- ----------------------------
-- 三方下发帐变 2020年1月28日18:38:26
-- ----------------------------

CREATE TABLE `t_thirdchannelissuedrecord`
(
    `OrderIndex`             INT PRIMARY KEY AUTO_INCREMENT COMMENT '平台订单索引',
    `PayChannelID`           INT(11)  NOT NULL DEFAULT 0 COMMENT '渠道ID',
    `OrderID`                VARCHAR(32) NOT NULL DEFAULT '' COMMENT '订单号',
    `ChannelOrderID`         VARCHAR(64) NOT NULL DEFAULT '' COMMENT '渠道订单号',
    `TransType`              INT(11) NOT NULL DEFAULT 1 COMMENT '交易方式,0：充值，1：提款',
    `TransAmount`            DECIMAL(10, 2)   NOT NULL    DEFAULT '0.00',
    `PaidAmount`             DECIMAL(10, 2)   NOT NULL    DEFAULT '0.00',
    `SupplierPaidChannelFee` DECIMAL(10, 2)   NOT NULL    DEFAULT '0.00',
    `PayChannelFee`          DECIMAL(10, 2)   NOT NULL    DEFAULT '0.00',
    `SurpplierFee`           DECIMAL(10, 2)   NOT NULL    DEFAULT '0.00',
    `PreBalance`             DECIMAL(10, 2)   NOT NULL    DEFAULT '0.00',
    `Balance`                DECIMAL(10, 2)   NOT NULL    DEFAULT '0.00',
    `CreateTime`             TIMESTAMP        NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    `SupplierBranchID`       INT(11) NOT NULL DEFAULT 2,
    `MerchantID`             INT(11) NOT NULL DEFAULT 0 COMMENT '商户ID ',
    KEY `paychannelid_index` (`PayChannelID`) USING BTREE,
    KEY `ChannelOrderID_index` (`ChannelOrderID`, `PayChannelID`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='三方渠道下发账变表';


-- ----------------------------
-- 三方下发报表  增加字段 2020年1月28日18:38:46
-- ----------------------------

ALTER TABLE t_thirdchannelwithdraworder ADD CloseType INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '处理方式 0:自动 默认值;1:手动';
ALTER TABLE t_thirdchannelwithdraworder ADD `MerchantID` INT(11) COMMENT '商户ID';
ALTER TABLE t_thirdchannelwithdraworder ADD `Operator` INT(11) DEFAULT 0 COMMENT '操作人ID';
