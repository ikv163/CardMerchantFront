

ALTER TABLE t_paypool ADD `CreateBy` int(11) not null DEFAULT 0 COMMENT '创建人';
ALTER TABLE t_paypool ADD `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
ALTER TABLE t_paypool ADD `updateBy` int(11) not null DEFAULT 0 COMMENT '修改人';