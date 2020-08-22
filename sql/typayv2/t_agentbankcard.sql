CREATE TABLE t_agentbankcard
(
    bankid           BIGINT(19)                               NOT NULL COMMENT '银行卡id'
        PRIMARY KEY,
    supplierbranchid INT            DEFAULT -1                NOT NULL COMMENT '分部id',
    banknum          VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '银行卡号码',
    bankcode         VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '银行卡编码',
    banktype         VARCHAR(8)     DEFAULT ''                NOT NULL COMMENT '银行卡类型代码',
    address          VARCHAR(128)   DEFAULT ''                NOT NULL COMMENT '开户地址',
    depositaddress   VARCHAR(45)    DEFAULT ''                NOT NULL COMMENT '充值地址',
    ownername        VARCHAR(64)    DEFAULT ''                NOT NULL COMMENT '开户人',
    ownerphone       VARCHAR(16)    DEFAULT ''                NOT NULL COMMENT '开户人手机预留号码',
    owneridentity    VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '开户人身份证',
    worktype         INT            DEFAULT -1                NOT NULL COMMENT '工作模式1:收款,2:付款',
    balance          DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '余额',
    status           INT            DEFAULT 1                 NOT NULL COMMENT '1：启用，0:停用，-1:软删除,-2:预启用, -3:交易限额停用 ,-4:余额超额停用,-5:交易笔数超额停用,-6:无转入转出停用,-7异常，-8临时停用，100：在线',
    remark           VARCHAR(64)    DEFAULT ''                NOT NULL COMMENT '备注',
    createtime       DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    lastupdatetime   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    cardindex        VARCHAR(64)    DEFAULT ''                NOT NULL COMMENT '银行卡index',
    createby         VARCHAR(100)   DEFAULT ''                NOT NULL COMMENT '创建人',
    updatetime       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    password         VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '提款密码',
    agent_code                VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '代理编码',
    CONSTRAINT uniq_bankcode
        UNIQUE (bankcode) COMMENT '银行简码',
    CONSTRAINT uniq_banknum
        UNIQUE (banknum, bankcode)
)
    COMMENT '代理银行卡表';
