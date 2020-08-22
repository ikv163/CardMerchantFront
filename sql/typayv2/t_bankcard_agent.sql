-- auto-generated definition
CREATE TABLE t_bankcard_agent
(
    id                 BIGINT AUTO_INCREMENT COMMENT '代理ID'
        PRIMARY KEY,
    supplier_branch_id INT            DEFAULT 0                 NOT NULL COMMENT '财务分支',
    agent_code         VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '代理编码',
    agent_name         VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '代理名称',
    parent_agent_code  VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '父级代理编码',
    balance            DECIMAL(20, 2) DEFAULT 0.00              NOT NULL COMMENT '总余额',
    credit_balance     DECIMAL(20, 2) DEFAULT 0.00              NOT NULL COMMENT '信用余额',
    available_balance  DECIMAL(20, 2) DEFAULT 0.00              NOT NULL COMMENT '可用余额',
    fronzen_balance    DECIMAL(20, 2) DEFAULT 0.00              NOT NULL COMMENT '冻结余额',
    ratio              DECIMAL(20, 2) DEFAULT 0.00              NOT NULL COMMENT '费率',
    profit_balance     DECIMAL(20, 2) DEFAULT 0.00              NOT NULL COMMENT '收益',
    status             INT            DEFAULT 1                 NOT NULL COMMENT '1：启用，0:停用，-1:软删除',
    remark             VARCHAR(128)   DEFAULT ''                NOT NULL COMMENT '备注',
    create_time        DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    last_update_time   DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    version            BIGINT         DEFAULT 0                 NOT NULL COMMENT '版本',
    user_id            BIGINT         DEFAULT -1                NOT NULL COMMENT '卡商代理登录账号',
    agent_level        INT            DEFAULT -1                NOT NULL COMMENT '代理层级：0为卡商（总代），1为一级代理',
    createby           VARCHAR(100)   DEFAULT ''                NOT NULL COMMENT '创建人',
    CONSTRAINT uniq_agent_code
        UNIQUE (agent_code)
)
    COMMENT '卡商代理';

