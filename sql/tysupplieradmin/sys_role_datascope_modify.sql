ALTER TABLE tysupplieradmin.sys_role_datascope
    ADD COLUMN channel_scope INT(1) NOT NULL DEFAULT 0 COMMENT '是否渠道所有权限，默认：0 无权限' AFTER create_time;

ALTER TABLE tysupplieradmin.sys_role_datascope
    ADD COLUMN cardpool_scope INT(1) NOT NULL DEFAULT 0 COMMENT '是否卡池所有权限，默认：0 无权限' AFTER channel_scope;

