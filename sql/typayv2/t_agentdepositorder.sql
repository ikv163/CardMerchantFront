-- auto-generated definition
CREATE TABLE t_agentdepositorder
(
    orderindex                BIGINT(29) AUTO_INCREMENT COMMENT '索引'
        PRIMARY KEY,
    orderid                   VARCHAR(32)    DEFAULT '0'               NOT NULL COMMENT '订单号',
    supplierbranchid          INT            DEFAULT 0                 NOT NULL COMMENT '分部id',
    paytype                   INT            DEFAULT 0                 NOT NULL COMMENT '充值方式,1:网银转账2:快捷支付3:银联支付4:微信5:支付宝6:qq钱包7:京东支付8:云闪付',
    payamount                 DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '提款金额',
    paidamount                DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '实现到帐金额',
    paidchannelfee            DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '渠道手续费',
    supplierpaidchannelfee    DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '财务分支渠道费用',
    notifyurl                 VARCHAR(512)   DEFAULT ''                NOT NULL COMMENT '商户用于接收订单通知的地址',
    returnurl                 VARCHAR(512)   DEFAULT ''                NOT NULL COMMENT '订单提交后立即返回的商户页面地址',
    bankcode                  VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '银行卡简码',
    md5orderid                VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT 'md5签名',
    paychannelid              INT            DEFAULT 0                 NOT NULL COMMENT '处理该订单的充值渠道',
    banknum                   VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '处理该订单的银行卡',
    bankaccount               VARCHAR(64)    DEFAULT ''                NOT NULL COMMENT '处理该订单的银行卡简称',
    orderstatus               INT            DEFAULT 0                 NOT NULL COMMENT '订单状态0:创建，默认值;1：处理中;2:清算中;3:正常支付关闭;4：手工确认订单已完成关闭; 5:超时,6:超时关闭；7:取消关闭',
    orderpaidstatuschangetime DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '订单状态最后修改时间',
    ordernotifystatus         INT            DEFAULT 0                 NOT NULL COMMENT '订单通知状态',
    ordernotifytime           DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '订单通知状态最后修改时间',
    remark                    VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '附言',
    name                      VARCHAR(64)    DEFAULT ''                NOT NULL COMMENT '名称',
    createtime                DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    lastupdatetime            TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    agent_code                VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '代理编码'
)
    COMMENT '代理银行卡充值订单';

CREATE INDEX createtime_index
    ON t_agentdepositorder (createtime);

CREATE INDEX name_index
    ON t_agentdepositorder (name);

CREATE INDEX name_remark_index
    ON t_agentdepositorder (remark, name);

CREATE INDEX orderid_idx
    ON t_agentdepositorder (orderid);

