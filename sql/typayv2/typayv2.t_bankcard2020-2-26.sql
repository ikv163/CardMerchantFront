alter table t_bankcard
    add authes VARCHAR(2000) not null default '' COMMENT '保存多个权限名称';



ALTER TABLE t_bankcard ADD `CreateBy` int(11) not null DEFAULT 0 COMMENT '创建人';
ALTER TABLE t_bankcard ADD `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
ALTER TABLE t_bankcard ADD `updateBy` int(11) not null DEFAULT 0 COMMENT '修改人';