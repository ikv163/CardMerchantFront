
-- ----------------------------
--   银行卡客户端 增加字段 2020年1月28日18:38:46
-- ----------------------------
ALTER TABLE t_bankclient ADD `realReMark` varchar(45) not null DEFAULT '' COMMENT '真实的备注';

ALTER TABLE t_bankclient ADD `CreateBy` int(11) not null DEFAULT 0 COMMENT '创建人';
ALTER TABLE t_bankclient ADD `updateTime` timestamp not null  DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间';
ALTER TABLE t_bankclient ADD `updateBy` int(11) not null DEFAULT 0 COMMENT '修改人';


