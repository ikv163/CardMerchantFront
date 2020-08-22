-- auto-generated definition
CREATE TABLE t_bankcard_agent_accountrecord
(
    id                 BIGINT AUTO_INCREMENT COMMENT '记录ID'
        PRIMARY KEY,
    agent_code         VARCHAR(10)    DEFAULT ''                NOT NULL COMMENT '代理编码',
    supplier_branch_id INT            DEFAULT 0                 NOT NULL COMMENT '财务分支',
    order_id           VARCHAR(32)    DEFAULT ''                NOT NULL COMMENT '订单ID',
    bank_id            INT            DEFAULT 0                 NOT NULL COMMENT '银行卡ID',
    trans_type         INT            DEFAULT -1                NOT NULL COMMENT '交易方式,0：充值，1：提款，2 交押金，3 退押金 4 利润转押金',
    trans_amount       DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '交易金额',
    paid_amount        DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '实际交易金额',
    balance            DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '余额',
    available_balance  DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '可用余额',
    frozen_balance     DECIMAL(10, 2) DEFAULT 0.00              NOT NULL COMMENT '冻结余额',
    create_time        DATETIME       DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
)
    COMMENT '卡商卡账变';

CREATE INDEX idx_create_time_agent
    ON t_bankcard_agent_accountrecord (create_time, agent_code);

