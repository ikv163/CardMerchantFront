-- auto-generated definition
CREATE TABLE t_agentwithdraworder
(
    orderindex        BIGINT(19)                               NOT NULL COMMENT '订单索引'
        PRIMARY KEY,
    orderid           VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '订单id',
    ordernotifytime   DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '订单通知时间',
    supplierbranchid  INT            DEFAULT -1                NOT NULL COMMENT '财务分部id',
    paytype           INT            DEFAULT -1                NOT NULL COMMENT '付款方式,1：银行卡，2：第三方，3：其他方式',
    payamount         DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '提款金额',
    paidamount        DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '实现到帐',
    notifyurl         VARCHAR(512)   DEFAULT ''                NOT NULL COMMENT '通知地址',
    returnurl         VARCHAR(512)   DEFAULT ''                NOT NULL COMMENT '回调地址',
    paychannelid      INT            DEFAULT 0                 NOT NULL COMMENT '处理该订单的充值渠道',
    bankaccount       VARCHAR(64)    DEFAULT ''                NOT NULL COMMENT '银行帐号',
    orderstatus       INT            DEFAULT 0                 NOT NULL COMMENT '订单状态',
    ordernotifystatus INT            DEFAULT 0                 NOT NULL COMMENT '订单通知状态',
    remark            VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '备注',
    bankcode          VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '银行卡简码',
    banknum           VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '银行卡号',
    bankaddress       VARCHAR(128)   DEFAULT ''                NOT NULL COMMENT '银行卡地址',
    bankowner         VARCHAR(64)    DEFAULT ''                NOT NULL COMMENT '银行卡持有者',
    md5orderid        VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT 'md5订单加密信息',
    digest            VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT 'digest',
    ordertype         INT            DEFAULT 0                 NOT NULL COMMENT '订单类型',
    createtime        DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    agent_code        VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '代理编码',
    lastupdatetime    DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    CONSTRAINT uniq_orderid
        UNIQUE (orderid)
)
    COMMENT '卡商提款订单';

CREATE INDEX create_ordernotify_ordertype
    ON t_agentwithdraworder (ordertype, ordernotifystatus, createtime);

CREATE INDEX createtime_index
    ON t_agentwithdraworder (createtime);

CREATE INDEX digest_index
    ON t_agentwithdraworder (digest);

CREATE INDEX orderid_idx
    ON t_agentwithdraworder (orderid);

CREATE INDEX supplierbranchid_index
    ON t_agentwithdraworder (supplierbranchid);

