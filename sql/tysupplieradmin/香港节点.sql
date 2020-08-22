USE tysupplieradmin;

CREATE TABLE sys_role_datascope
(
    id               BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'id',
    role_id          BIGINT                NOT NULL DEFAULT 0 COMMENT '用户id',
    datascope_id     BIGINT                NOT NULL DEFAULT 0 COMMENT '数据权限id',
    supplierbranchid INT                   NOT NULL DEFAULT 0 COMMENT 'supplierbranchid',
    create_time      DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '角色数据权限';

RENAME TABLE system_log TO bak_system_log;
RENAME TABLE sys_datascope TO bak_sys_datascope;
RENAME TABLE sys_user_datascope TO bak_sys_user_datascope;



CREATE TABLE `system_log`
(
    `id`          INT(10)      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `type`        TINYINT(3)   NOT NULL DEFAULT 0 COMMENT '操作类型：1添加，2修改，3删除',
    `class`       VARCHAR(255) NOT NULL DEFAULT '' COMMENT '操作class',
    `route`       VARCHAR(255) NOT NULL DEFAULT '' COMMENT '路由',
    `table`       VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '操作表',
    `description` TEXT         NOT NULL DEFAULT '' COMMENT '操作信息',
    `create_time` INT(10)      NOT NULL DEFAULT 0 COMMENT '操作时间',
    `user_id`     INT(10)      NOT NULL DEFAULT 0 COMMENT '用户ID',
    `ip`          VARCHAR(15)  NOT NULL DEFAULT '' COMMENT '操作人ip',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 772084


  ROW_FORMAT = DYNAMIC COMMENT ='日志';


CREATE TABLE `sys_datascope`
(
    `datascope_id`   BIGINT(20)    NOT NULL AUTO_INCREMENT COMMENT 'datascope_id',
    `bankcardpool`   VARCHAR(5000) NOT NULL DEFAULT '' COMMENT '卡池集合',
    `paychannel_ids` VARCHAR(5000) NOT NULL DEFAULT '' COMMENT '数据权限id号集合',
    PRIMARY KEY (`datascope_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
    COMMENT ='sys_datascope';


CREATE TABLE `sys_user_datascope`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`      BIGINT(20) NOT NULL DEFAULT '0' COMMENT '用户id',
    `datascope_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '数据权限id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_datascope_user_id_uindex` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
    COMMENT ='sys_user_datascope';




RENAME TABLE sys_menu TO backsys_menu20200425;


CREATE TABLE `sys_menu`
(
    `menu_id`     BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '菜单名称',
    `parent_id`   BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   INT(4)       NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `url`         VARCHAR(200) NOT NULL DEFAULT '#' COMMENT '请求地址',
    `target`      VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
    `menu_type`   CHAR(1)      NOT NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     CHAR(1)      NOT NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `perms`       VARCHAR(100) NOT NULL DEFAULT '' COMMENT '权限标识',
    `icon`        VARCHAR(100) NOT NULL DEFAULT '#' COMMENT '菜单图标',
    `create_by`   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_by`   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `remark`      VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8062 COMMENT = '菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, '系统管理', 0, 998, '#', '', 'M', '0', '', 'fa fa-gear', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '系统管理目录');
INSERT INTO `sys_menu`
VALUES (100, '用户管理', 1, 1, '/system/user', '', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `sys_menu`
VALUES (101, '角色管理', 1, 2, '/system/role', '', 'C', '0', 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu`
VALUES (102, '菜单管理', 1, 3, '/system/menu', '', 'C', '1', 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu`
VALUES (107, '通知公告', 1, 8, '/system/notice', '', 'C', '1', 'system:notice:view', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '通知公告菜单');
INSERT INTO `sys_menu`
VALUES (1000, '用户查询', 100, 1, '#', '', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1001, '用户新增', 100, 2, '#', '', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1002, '用户修改', 100, 3, '#', '', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1003, '用户删除', 100, 4, '#', '', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1004, '用户导出', 100, 5, '#', '', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1005, '用户导入', 100, 6, '#', '', 'F', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1006, '重置密码', 100, 7, '#', '', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1007, '角色查询', 101, 1, '#', '', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1008, '角色新增', 101, 2, '#', '', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1009, '角色修改', 101, 3, '#', '', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1010, '角色删除', 101, 4, '#', '', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1011, '角色导出', 101, 5, '#', '', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1012, '菜单查询', 102, 1, '#', '', 'F', '0', 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1013, '菜单新增', 102, 2, '#', '', 'F', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1014, '菜单修改', 102, 3, '#', '', 'F', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1015, '菜单删除', 102, 4, '#', '', 'F', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1035, '公告查询', 107, 1, '#', '', 'F', '0', 'system:notice:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1036, '公告新增', 107, 2, '#', '', 'F', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1037, '公告修改', 107, 3, '#', '', 'F', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1038, '公告删除', 107, 4, '#', '', 'F', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (2000, '银行卡参数', 0, 1, '#', '', 'C', '0', '#', 'fa fa-credit-card', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '银行卡参数-目录');
INSERT INTO `sys_menu`
VALUES (2001, '银行卡管理中心', 2000, 1, '/bankcard/manage_bankcard_center', '', 'C', '0',
        'bankcard:manage_bankcard_center:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '银行卡管理中心菜单');
INSERT INTO `sys_menu`
VALUES (2002, '银行卡管理中心查询', 2001, 0, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2003, '银行卡管理中心添加', 2001, 1, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:add', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2004, '银行卡管理中心修改', 2001, 2, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2005, '银行卡管理中心批量额度调整', 2001, 3, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:editpop', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2006, '银行卡管理中心批量修改状态', 2001, 4, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:statusedit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2008, '银行卡管理中心批量修改工作模式', 2001, 5, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:worktypeedit', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2009, '银行卡管理中心设置自动手动区分金额', 2001, 6, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:sethandwithdraw', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2010, '银行卡管理中心删除', 2001, 7, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:remove', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2011, '银行卡管理中心导出', 2001, 8, '#', '', 'F', '0', 'bankcard:manage_bankcard_center:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2012, '银行卡卡池', 2000, 2, '/bankcard/bankcard_pool', '', 'C', '0', 'bankcard:bankcard_pool:view', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡卡池菜单');
INSERT INTO `sys_menu`
VALUES (2013, '银行卡卡池查询', 2012, 0, '#', '', 'F', '0', 'bankcard:bankcard_pool:list', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2014, '银行卡卡池添 加', 2012, 1, '#', '', 'F', '0', 'bankcard:bankcard_pool:add', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2015, '银行卡卡池修改', 2012, 2, '#', '', 'F', '0', 'bankcard:bankcard_pool:edit', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2016, '银行卡卡池删除', 2012, 3, '#', '', 'F', '0', 'bankcard:bankcard_pool:remove', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2017, '银行卡客户端', 2000, 3, '/bankcard/bankcard_client', '', 'C', '0', 'bankcard:bankcard_client:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡客户端菜单');
INSERT INTO `sys_menu`
VALUES (2018, '银行卡客户端查询', 2017, 0, '#', '', 'F', '0', 'bankcard:bankcard_client:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2019, '银行卡客户端添 加', 2017, 1, '#', '', 'F', '0', 'bankcard:bankcard_client:add', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2020, '银行卡客户端修改', 2017, 2, '#', '', 'F', '0', 'bankcard:bankcard_client:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2021, '银行卡客户端删除', 2017, 3, '#', '', 'F', '0', 'bankcard:bankcard_client:remove', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2022, '银行卡客户端启用', 2017, 4, '#', '', 'F', '0', 'bankcard:bankcard_client:operateurl', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2023, '业务配置', 0, 2, '#', '', 'C', '0', '#', 'fa fa-sheqel', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '业务配置-目录');
INSERT INTO `sys_menu`
VALUES (2024, '支付池', 2023, 1, '/config/paymentpool', '', 'C', '0', 'config:paymentpool:view', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '支付池菜单');
INSERT INTO `sys_menu`
VALUES (2025, '支付池查询', 2024, 0, '#', '', 'F', '0', 'config:paymentpool:list', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2026, '支付池添 加', 2024, 1, '#', '', 'F', '0', 'config:paymentpool:add', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2027, '支付池修改', 2024, 2, '#', '', 'F', '0', 'config:paymentpool:edit', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2028, '支付池删除', 2024, 3, '#', '', 'F', '0', 'config:paymentpool:remove', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2029, '支付池渠道维护', 2024, 4, '#', '', 'F', '0', 'config:paymentpool:selectDeptTree', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2030, '银行卡支付渠道', 2023, 2, '/config/paymentchannelbankcard', '', 'C', '0', 'config:paymentchannelbankcard:view',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡支付渠道菜单');
INSERT INTO `sys_menu`
VALUES (2031, '银行卡支付渠道查询', 2030, 0, '#', '', 'F', '0', 'config:paymentchannelbankcard:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2032, '银行卡支付渠道新增', 2030, 1, '#', '', 'F', '0', 'config:paymentchannelbankcard:add', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2033, '银行卡支付渠道编辑', 2030, 2, '#', '', 'F', '0', 'config:paymentchannelbankcard:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2034, '银行卡支付渠道删除', 2030, 3, '#', '', 'F', '0', 'config:paymentchannelbankcard:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2035, '银行卡支付渠道启用', 2030, 4, '#', '', 'F', '0', 'config:paymentchannelbankcard:enable', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2036, '银行卡支付渠道停用', 2030, 5, '#', '', 'F', '0', 'config:paymentchannelbankcard:stop', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2037, '第三方支付渠道', 2023, 3, '/config/paymentchannelthirdparty', '', 'C', '0',
        'config:paymentchannelthirdparty:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '第三方支付渠道菜单');
INSERT INTO `sys_menu`
VALUES (2038, '第三方支付渠道查询', 2037, 0, '#', '', 'F', '0', 'config:paymentchannelthirdparty:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2039, '第三方支付渠道启用', 2037, 1, '#', '', 'F', '0', 'config:paymentchannelthirdparty:enable', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2040, '第三方支付渠道停用', 2037, 2, '#', '', 'F', '0', 'config:paymentchannelthirdparty:stop', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2041, '第三方支付渠道测试', 2037, 3, '#', '', 'F', '0', 'config:paymentchannelthirdparty:checkChannel', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2042, '第三方支付渠道三方渠道修改', 2037, 4, '#', '', 'F', '0', 'config:paymentchannelthirdparty:updateThirdChannel', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2043, '第三方支付渠道查看', 2037, 5, '#', '', 'F', '0', 'config:paymentchannelthirdparty:proview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2044, '业务监控', 0, 3, '#', '', 'C', '0', '#', 'fa fa-bar-chart', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '业务监控-目录');
INSERT INTO `sys_menu`
VALUES (2045, '银行卡客户端监控', 2044, 1, '/monitors/monitorsactivebankcard', '', 'C', '0',
        'monitors:monitorsactivebankcard:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '银行卡客户端监控菜单');
INSERT INTO `sys_menu`
VALUES (2046, '银行卡客户端监控查询', 2045, 0, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2047, '银行卡客户端监控导出', 2045, 1, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2048, '银行卡客户端监控测卡', 2045, 2, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:testCard', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2049, '银行卡客户端监控批量额度调整', 2045, 3, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:batchBalance', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2050, '银行卡客户端监控申请资金', 2045, 4, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:applyMoney', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2051, '银行卡客户端监控关', 2045, 5, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:openAndClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2052, '银行卡客户端监控开', 2045, 6, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:openAndClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2053, '银行卡客户端监控重置', 2045, 7, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:reset', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2054, '银行卡客户端监控校验余额', 2045, 8, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:checkBalance', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2055, '银行卡客户端监控上卡', 2045, 9, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:downAndUp', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2056, '银行卡客户端监控下卡', 2045, 10, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:downAndUp', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2057, '银行卡客户端监控只抓同行', 2045, 11, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:onlySameBank', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2058, '银行卡客户端监控银行卡状态修改', 2045, 12, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:editStatus', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2059, '银行卡客户端监控参数设置', 2045, 13, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:paramSetting', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2060, '银行卡客户端监控客户端状态修改', 2045, 14, '#', '', 'F', '0', 'monitors:monitorsactivebankcard:batchUpdateClient', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2061, '三方渠道监控', 2044, 4, '/monitors/monitorsthirdpartychannel', '', 'C', '0',
        'monitors:monitorsthirdpartychannel:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '三方渠道监控菜单');
INSERT INTO `sys_menu`
VALUES (2062, '三方渠道监控查询', 2061, 0, '#', '', 'F', '0', 'monitors:monitorsthirdpartychannel:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2063, '三方渠道监控下发', 2061, 1, '#', '', 'F', '0', 'monitors:monitorsthirdpartychannel:issued', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2064, '三方渠道监控修改', 2061, 2, '#', '', 'F', '0', 'monitors:monitorsthirdpartychannel:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2065, '三方渠道监控停止', 2061, 3, '#', '', 'F', '0', 'monitors:monitorsthirdpartychannel:stop', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2066, '三方渠道监控启用', 2061, 4, '#', '', 'F', '0', 'monitors:monitorsthirdpartychannel:enable', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2067, '三方渠道监控导出', 2061, 5, '#', '', 'F', '0', 'monitors:monitorsthirdpartychannel:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2068, '出入款银行卡渠道监控', 2044, 5, '/monitors/monitorsinmoneychannel', '', 'C', '0',
        'monitors:monitorsinmoneychannel:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '出入款银行卡渠道监控菜单');
INSERT INTO `sys_menu`
VALUES (2069, '出入款银行卡渠道监控查询', 2068, 0, '#', '', 'F', '0', 'monitors:monitorsinmoneychannel:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2070, '出入款银行卡渠道监控修改', 2068, 1, '#', '', 'F', '0', 'monitors:monitorsinmoneychannel:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2071, '出入款银行卡渠道监控停止', 2068, 2, '#', '', 'F', '0', 'monitors:monitorsinmoneychannel:stop', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2072, '出入款银行卡渠道监控启用', 2068, 3, '#', '', 'F', '0', 'monitors:monitorsinmoneychannel:enable', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2073, '卡池资金监控', 2044, 6, '/monitors/monitorsfundspool', '', 'C', '0', 'monitors:monitorsfundspool:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '卡池资金监控菜单');
INSERT INTO `sys_menu`
VALUES (2074, '卡池资金监控查询', 2073, 0, '#', '', 'F', '0', 'monitors:monitorsfundspool:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2075, '订单管理', 0, 4, '#', '', 'C', '0', '#', 'fa fa-table', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '订单管理-目录');
INSERT INTO `sys_menu`
VALUES (2076, '银行卡收款订单', 2075, 1, '/orders/ordersbankcardreceipt', '', 'C', '0', 'orders:ordersbankcardreceipt:view',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡收款订单菜单');
INSERT INTO `sys_menu`
VALUES (2077, '银行卡收款订单查询', 2076, 0, '#', '', 'F', '0', 'orders:ordersbankcardreceipt:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2078, '银行卡收款订单导出', 2076, 1, '#', '', 'F', '0', 'orders:ordersbankcardreceipt:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2079, '银行卡收款订单推送', 2076, 2, '#', '', 'F', '0', 'orders:ordersbankcardreceipt:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2080, '银行卡收款订单确认', 2076, 3, '#', '', 'F', '0', 'orders:ordersbankcardreceipt:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2081, '银行卡收款订单取消', 2076, 4, '#', '', 'F', '0', 'orders:ordersbankcardreceipt:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2082, '银行卡收款订单查看', 2076, 5, '#', '', 'F', '0', 'orders:ordersbankcardreceipt:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2083, '银行卡手动出款订单', 2075, 2, '/orders/ordersbankcardmanualoutmoney', '', 'C', '0',
        'orders:ordersbankcardmanualoutmoney:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '银行卡手动出款订单菜单');
INSERT INTO `sys_menu`
VALUES (2084, '银行卡手动出款订单查询', 2083, 0, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2085, '银行卡手动出款订单导出', 2083, 1, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2086, '银行卡手动出款订单分派订单', 2083, 2, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:allotorder', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2087, '银行卡手动出款订单取消分派', 2083, 3, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:closeAllotOrder', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2088, '银行卡手动出款订单推送', 2083, 4, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2089, '银行卡手动出款订单确认', 2083, 5, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:orderConfirm', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2090, '银行卡手动出款订单取消', 2083, 6, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2091, '银行卡手动出款订单查看', 2083, 7, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:orderProview', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2092, '银行内转订单', 2075, 3, '/orders/ordersbankinnertransfer', '', 'C', '0', 'orders:ordersbankinnertransfer:view',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行内转订单菜单');
INSERT INTO `sys_menu`
VALUES (2093, '银行内转订单查询', 2092, 0, '#', '', 'F', '0', 'orders:ordersbankinnertransfer:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2094, '银行内转订单导出', 2092, 1, '#', '', 'F', '0', 'orders:ordersbankinnertransfer:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2095, '银行内转订单取消', 2092, 2, '#', '', 'F', '0', 'orders:ordersbankinnertransfer:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2096, '银行内转订单查看', 2092, 3, '#', '', 'F', '0', 'orders:ordersbankinnertransfer:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2097, '银行卡提款订单', 2075, 4, '/orders/ordersbankcardwithdrew', '', 'C', '0', 'orders:ordersbankcardwithdrew:view',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡提款订单菜单');
INSERT INTO `sys_menu`
VALUES (2098, '银行卡提款订单查询', 2097, 0, '#', '', 'F', '0', 'orders:ordersbankcardwithdrew:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2099, '银行卡提款订单导出', 2097, 1, '#', '', 'F', '0', 'orders:ordersbankcardwithdrew:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2100, '银行卡提款订单转手动', 2097, 2, '#', '', 'F', '0', 'orders:ordersbankcardwithdrew:orderhand', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2101, '银行卡提款订单推送', 2097, 3, '#', '', 'F', '0', 'orders:ordersbankcardwithdrew:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2102, '银行卡提款订单确认', 2097, 4, '#', '', 'F', '0', 'orders:ordersbankcardwithdrew:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2103, '银行卡提款订单取消', 2097, 5, '#', '', 'F', '0', 'orders:ordersbankcardwithdrew:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2104, '银行卡提款订单查看', 2097, 6, '#', '', 'F', '0', 'orders:ordersbankcardwithdrew:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2105, '银行卡测卡订单', 2075, 5, '/orders/ordersbankcardtest', '', 'C', '0', 'orders:ordersbankcardtest:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡测卡订单菜单');
INSERT INTO `sys_menu`
VALUES (2106, '银行卡测卡订单查询', 2105, 0, '#', '', 'F', '0', 'orders:ordersbankcardtest:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2107, '银行卡测卡订单导出', 2105, 1, '#', '', 'F', '0', 'orders:ordersbankcardtest:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2108, '银行卡测卡订单确认', 2105, 2, '#', '', 'F', '0', 'orders:ordersbankcardtest:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2109, '银行卡测卡订单取消', 2105, 3, '#', '', 'F', '0', 'orders:ordersbankcardtest:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2110, '银行卡测卡订单查看', 2105, 4, '#', '', 'F', '0', 'orders:ordersbankcardtest:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2111, '三方存款订单', 2075, 6, '/orders/ordersthirdpartydeposit', '', 'C', '0', 'orders:ordersthirdpartydeposit:view',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '三方存款订单菜单');
INSERT INTO `sys_menu`
VALUES (2112, '三方存款订单查询', 2111, 0, '#', '', 'F', '0', 'orders:ordersthirdpartydeposit:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2113, '三方存款订单导出', 2111, 1, '#', '', 'F', '0', 'orders:ordersthirdpartydeposit:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2114, '三方存款订单推送', 2111, 2, '#', '', 'F', '0', 'orders:ordersthirdpartydeposit:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2115, '三方存款订单确认', 2111, 3, '#', '', 'F', '0', 'orders:ordersthirdpartydeposit:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2116, '三方存款订单取消', 2111, 4, '#', '', 'F', '0', 'orders:ordersthirdpartydeposit:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2117, '三方存款订单查看', 2111, 5, '#', '', 'F', '0', 'orders:ordersthirdpartydeposit:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2118, '三方提款订单', 2075, 7, '/orders/ordersthirdpartywidthdrew', '', 'C', '0',
        'orders:ordersthirdpartywidthdrew:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '三方提款订单菜单');
INSERT INTO `sys_menu`
VALUES (2119, '三方提款订单查询', 2118, 0, '#', '', 'F', '0', 'orders:ordersthirdpartywidthdrew:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2120, '三方提款订单导出', 2118, 1, '#', '', 'F', '0', 'orders:ordersthirdpartywidthdrew:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2121, '三方提款订单推送', 2118, 2, '#', '', 'F', '0', 'orders:ordersthirdpartywidthdrew:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2122, '三方提款订单确认', 2118, 3, '#', '', 'F', '0', 'orders:ordersthirdpartywidthdrew:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2123, '三方提款订单取消', 2118, 4, '#', '', 'F', '0', 'orders:ordersthirdpartywidthdrew:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2124, '三方提款订单查看', 2118, 5, '#', '', 'F', '0', 'orders:ordersthirdpartywidthdrew:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2125, '三方下发订单', 2075, 8, '/orders/ordersthirdpartydelivery', '', 'C', '0',
        'orders:ordersthirdpartydelivery:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '三方下发订单菜单');
INSERT INTO `sys_menu`
VALUES (2126, '三方下发订单查询', 2125, 0, '#', '', 'F', '0', 'orders:ordersthirdpartydelivery:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2127, '三方下发订单导出', 2125, 1, '#', '', 'F', '0', 'orders:ordersthirdpartydelivery:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2128, '三方下发订单推送', 2125, 2, '#', '', 'F', '0', 'orders:ordersthirdpartydelivery:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2129, '三方下发订单确认', 2125, 3, '#', '', 'F', '0', 'orders:ordersthirdpartydelivery:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2130, '三方下发订单取消', 2125, 4, '#', '', 'F', '0', 'orders:ordersthirdpartydelivery:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2131, '三方下发订单查看', 2125, 5, '#', '', 'F', '0', 'orders:ordersthirdpartydelivery:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2132, '三方测试订单', 2075, 9, '/orders/ordersthirdpartytest', '', 'C', '0', 'orders:ordersthirdpartytest:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '三方测试订单菜单');
INSERT INTO `sys_menu`
VALUES (2133, '三方测试订单查询', 2132, 0, '#', '', 'F', '0', 'orders:ordersthirdpartytest:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2134, '三方测试订单导出', 2132, 1, '#', '', 'F', '0', 'orders:ordersthirdpartytest:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2135, '三方测试订单推送', 2132, 2, '#', '', 'F', '0', 'orders:ordersthirdpartytest:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2136, '三方测试订单确认', 2132, 3, '#', '', 'F', '0', 'orders:ordersthirdpartytest:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2137, '三方测试订单取消', 2132, 4, '#', '', 'F', '0', 'orders:ordersthirdpartytest:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2138, '三方测试订单查看', 2132, 5, '#', '', 'F', '0', 'orders:ordersthirdpartytest:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2139, '内转代付订单', 2075, 10, '/orders/thirdwithdraworderpayroll', '', 'C', '0',
        'orders:thirdwithdraworderpayroll:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '内转代付订单菜单');
INSERT INTO `sys_menu`
VALUES (2140, '内转代付订单查询', 2139, 0, '#', '', 'F', '0', 'orders:thirdwithdraworderpayroll:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2141, '内转代付订单导出', 2139, 1, '#', '', 'F', '0', 'orders:thirdwithdraworderpayroll:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2142, '内转代付订单推送', 2139, 2, '#', '', 'F', '0', 'orders:thirdwithdraworderpayroll:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2143, '内转代付订单确认', 2139, 3, '#', '', 'F', '0', 'orders:thirdwithdraworderpayroll:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2144, '内转代付订单取消', 2139, 4, '#', '', 'F', '0', 'orders:thirdwithdraworderpayroll:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2145, '内转代付订单查看', 2139, 5, '#', '', 'F', '0', 'orders:thirdwithdraworderpayroll:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2146, '代理出款订单', 2075, 11, '/orders/thirdwithdraworderproxy', '', 'C', '0',
        'orders:thirdwithdraworderproxy:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '代理出款订单菜单');
INSERT INTO `sys_menu`
VALUES (2147, '代理出款订单查询', 2146, 0, '#', '', 'F', '0', 'orders:thirdwithdraworderproxy:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2148, '代理出款订单导出', 2146, 1, '#', '', 'F', '0', 'orders:thirdwithdraworderproxy:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2149, '代理出款订单推送', 2146, 2, '#', '', 'F', '0', 'orders:thirdwithdraworderproxy:orderSend', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2150, '代理出款订单确认', 2146, 3, '#', '', 'F', '0', 'orders:thirdwithdraworderproxy:orderConfirm', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2151, '代理出款订单取消', 2146, 4, '#', '', 'F', '0', 'orders:thirdwithdraworderproxy:orderClose', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2152, '代理出款订单查看', 2146, 5, '#', '', 'F', '0', 'orders:thirdwithdraworderproxy:orderProview', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2153, '交易流水管理', 0, 5, '#', '', 'C', '0', '#', 'fa fa-sheqel', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '交易流水管理-目录');
INSERT INTO `sys_menu`
VALUES (2154, '银行卡交易流水', 2153, 1, '/transactionrecord/transactionrecordbankcard', '', 'C', '0',
        'transactionrecord:transactionrecordbankcard:view', '#', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '银行卡交易流水菜单');
INSERT INTO `sys_menu`
VALUES (2155, '银行卡交易流水查询', 2154, 0, '#', '', 'F', '0', 'transactionrecord:transactionrecordbankcard:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2156, '银行卡交易流水修改', 2154, 1, '#', '', 'F', '0', 'transactionrecord:transactionrecordbankcard:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2157, '银行卡交易流水补单', 2154, 2, '#', '', 'F', '0', 'transactionrecord:transactionrecordbankcard:add', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2158, '银行卡交易流水删除', 2154, 3, '#', '', 'F', '0', 'transactionrecord:transactionrecordbankcard:remove', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2159, '银行卡交易流水重新匹配', 2154, 4, '#', '', 'F', '0', 'transactionrecord:transactionrecordbankcard:commit', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2160, '银行卡交易流水修改备注', 2154, 5, '#', '', 'F', '0', 'transactionrecord:transactionrecordbankcard:modifyremark',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2161, '银行卡交易流水导出', 2154, 6, '#', '', 'F', '0', 'transactionrecord:transactionrecordbankcard:export', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2162, '报表管理', 0, 6, '#', '', 'C', '0', '#', 'fa fa-list-alt', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '报表管理-目录');
INSERT INTO `sys_menu`
VALUES (2163, '银行卡统计报表', 2162, 1, '/reports/reportsbankcardgraph', '', 'C', '0', 'reports:reportsbankcardgraph:view',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡统计报表菜单');
INSERT INTO `sys_menu`
VALUES (2164, '银行卡统计报表查询', 2163, 0, '#', '', 'F', '0', 'reports:reportsbankcardgraph:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2165, '银行卡统计报表修改', 2163, 1, '#', '', 'F', '0', 'reports:reportsbankcardgraph:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2166, '银行卡统计报表导出', 2163, 2, '#', '', 'F', '0', 'reports:reportsbankcardgraph:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2167, '银行卡账变', 2162, 2, '/reports/reportsbankcardcus', '', 'C', '0', 'reports:reportsbankcardcus:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '银行卡账变菜单');
INSERT INTO `sys_menu`
VALUES (2168, '银行卡账变查询', 2167, 0, '#', '', 'F', '0', 'reports:reportsbankcardcus:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2169, '银行卡账变修改', 2167, 1, '#', '', 'F', '0', 'reports:reportsbankcardcus:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2170, '银行卡账变导出', 2167, 2, '#', '', 'F', '0', 'reports:reportsbankcardcus:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2171, '三方渠道报表', 2162, 3, '/reports/reportsthirdpartychannel', '', 'C', '0',
        'reports:reportsthirdpartychannel:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '三方渠道报表菜单');
INSERT INTO `sys_menu`
VALUES (2172, '三方渠道报表查询', 2171, 0, '#', '', 'F', '0', 'reports:reportsthirdpartychannel:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2173, '三方渠道报表修改', 2171, 1, '#', '', 'F', '0', 'reports:reportsthirdpartychannel:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2174, '三方渠道报表导出', 2171, 2, '#', '', 'F', '0', 'reports:reportsthirdpartychannel:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2175, '三方下发报表', 2162, 4, '/reports/reportsthirdpartydelivery', '', 'C', '0',
        'reports:reportsthirdpartydelivery:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '三方下发报表菜单');
INSERT INTO `sys_menu`
VALUES (2176, '三方下发报表查询', 2175, 0, '#', '', 'F', '0', 'reports:reportsthirdpartydelivery:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2177, '三方下发报表修改', 2175, 1, '#', '', 'F', '0', 'reports:reportsthirdpartydelivery:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2178, '三方下发报表导出', 2175, 2, '#', '', 'F', '0', 'reports:reportsthirdpartydelivery:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2179, '渠道统计报表', 2162, 5, '/reports/reportsgraphchannel', '', 'C', '0', 'reports:reportsgraphchannel:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '渠道统计报表菜单');
INSERT INTO `sys_menu`
VALUES (2180, '渠道统计报表查询', 2179, 0, '#', '', 'F', '0', 'reports:reportsgraphchannel:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2181, '渠道统计报表修改', 2179, 1, '#', '', 'F', '0', 'reports:reportsgraphchannel:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2182, '渠道统计报表导出', 2179, 2, '#', '', 'F', '0', 'reports:reportsgraphchannel:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2183, '商户统计报表', 2162, 6, '/reports/reportsmerchatgraph', '', 'C', '0', 'reports:reportsmerchatgraph:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '商户统计报表菜单');
INSERT INTO `sys_menu`
VALUES (2184, '商户统计报表查询', 2183, 0, '#', '', 'F', '0', 'reports:reportsmerchatgraph:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2185, '商户统计报表修改', 2183, 1, '#', '', 'F', '0', 'reports:reportsmerchatgraph:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2186, '商户统计报表导出', 2183, 2, '#', '', 'F', '0', 'reports:reportsmerchatgraph:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2187, '日志管理', 0, 7, '#', '', 'C', '0', '#', 'fa fa-map', 'admin', '2020-01-05 00:00:00', 'admin',
        '2020-01-05 00:00:00', '日志管理-目录');
INSERT INTO `sys_menu`
VALUES (2188, '操作日志', 2187, 1, '/monitor/operlog', '', 'C', '0', 'monitor:operlog:view', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '操作日志菜单');
INSERT INTO `sys_menu`
VALUES (2189, '操作日志查询', 2188, 0, '#', '', 'F', '0', 'monitor:operlog:list', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2190, '操作日志导出', 2188, 1, '#', '', 'F', '0', 'monitor:operlog:export', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2191, '登录日志', 2187, 2, '/monitor/logininfor', '', 'C', '0', 'monitor:logininfor:view', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '登录日志菜单');
INSERT INTO `sys_menu`
VALUES (2192, '登录日志查询', 2191, 0, '#', '', 'F', '0', 'monitor:logininfor:list', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2193, '登录日志导出', 2191, 1, '#', '', 'F', '0', 'monitor:logininfor:export', '#', 'admin', '2020-01-05 00:00:00',
        'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5000, '出款银行卡客户端监控', 2044, 2, '/monitors/monitorsactivebankcard_out', '', 'C', '0',
        'monitors:monitorsactivebankcard_out:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '出款银行卡客户端监控菜单');
INSERT INTO `sys_menu`
VALUES (5001, '出款银行卡客户端监控查询', 5000, 0, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5002, '出款银行卡客户端监控导出', 5000, 1, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5003, '出款银行卡客户端监控测卡', 5000, 2, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:testCard', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5004, '出款银行卡客户端监控批量额度调整', 5000, 3, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:batchBalance', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5005, '出款银行卡客户端监控关', 5000, 5, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:openAndClose', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5006, '出款银行卡客户端监控开', 5000, 6, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:openAndClose', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5007, '出款银行卡客户端监控校验余额', 5000, 8, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:checkBalance', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5008, '出款银行卡客户端监控上卡', 5000, 9, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:downAndUp', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5009, '出款银行卡客户端监控下卡', 5000, 10, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:downAndUp', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5010, '出款银行卡客户端监控只抓同行', 5000, 11, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:onlySameBank', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5011, '出款银行卡客户端监控银行卡状态修改', 5000, 12, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:editStatus', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5012, '出款银行卡客户端监控参数设置', 5000, 13, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:paramSetting', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5013, '出款银行卡客户端监控客户端状态修改', 5000, 14, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:batchUpdateClient',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5014, '出款银行卡客户端监控重置', 5000, 7, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:reset', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5015, '出款银行卡客户端监控申请资金', 5000, 4, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_out:applyMoney', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5555, '银行卡手动出款订单锁定', 2083, 6, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:lockorder', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (5556, '银行卡手动出款订单解锁', 2083, 7, '#', '', 'F', '0', 'orders:ordersbankcardmanualoutmoney:unlockorder', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6000, '入款银行卡客户端监控', 2044, 3, '/monitors/monitorsactivebankcard_in', '', 'C', '0',
        'monitors:monitorsactivebankcard_in:view', '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00',
        '入款银行卡客户端监控菜单');
INSERT INTO `sys_menu`
VALUES (6001, '入款银行卡客户端监控查询', 6000, 0, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6002, '入款银行卡客户端监控导出', 6000, 1, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6003, '入款银行卡客户端监控测卡', 6000, 2, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:testCard', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6004, '入款银行卡客户端监控批量额度调整', 6000, 3, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:batchBalance', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6005, '入款银行卡客户端监控关', 6000, 5, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:openAndClose', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6006, '入款银行卡客户端监控开', 6000, 6, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:openAndClose', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6007, '入款银行卡客户端监控校验余额', 6000, 8, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:checkBalance', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6008, '入款银行卡客户端监控上卡', 6000, 9, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:downAndUp', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6009, '入款银行卡客户端监控下卡', 6000, 10, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:downAndUp', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6010, '入款银行卡客户端监控只抓同行', 6000, 11, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:onlySameBank', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6011, '入款银行卡客户端监控银行卡状态修改', 6000, 12, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:editStatus', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6012, '入款银行卡客户端监控参数设置', 6000, 13, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:paramSetting', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6013, '入款银行卡客户端监控客户端状态修改', 6000, 14, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:batchUpdateClient',
        '#', 'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6014, '入款银行卡客户端监控重置', 6000, 7, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:reset', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6015, '入款银行卡客户端监控申请资金', 6000, 4, '#', '', 'F', '0', 'monitors:monitorsactivebankcard_in:applyMoney', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6654, '出款交易流水', 2153, 2, '/transactionrecord/withdraw', '', 'C', '0', 'transactionrecord:withdraw:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '出款交易流水菜单');
INSERT INTO `sys_menu`
VALUES (6655, '出款交易流水查询', 6654, 0, '#', '', 'F', '0', 'transactionrecord:withdraw:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6656, '出款交易流水修改', 6654, 1, '#', '', 'F', '0', 'transactionrecord:withdraw:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6657, '出款交易流水补单', 6654, 2, '#', '', 'F', '0', 'transactionrecord:withdraw:add', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6658, '出款交易流水删除', 6654, 3, '#', '', 'F', '0', 'transactionrecord:withdraw:remove', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6659, '出款交易流水重新匹配', 6654, 4, '#', '', 'F', '0', 'transactionrecord:withdraw:commit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6660, '出款交易流水修改备注', 6654, 5, '#', '', 'F', '0', 'transactionrecord:withdraw:modifyremark', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (6661, '出款交易流水导出', 6654, 6, '#', '', 'F', '0', 'transactionrecord:withdraw:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (7054, '入款交易流水', 2153, 3, '/transactionrecord/deposit', '', 'C', '0', 'transactionrecord:deposit:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '入款交易流水菜单');
INSERT INTO `sys_menu`
VALUES (7055, '入款交易流水查询', 7054, 0, '#', '', 'F', '0', 'transactionrecord:deposit:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (7056, '入款交易流水修改', 7054, 1, '#', '', 'F', '0', 'transactionrecord:deposit:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (7057, '入款交易流水补单', 7054, 2, '#', '', 'F', '0', 'transactionrecord:deposit:add', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (7058, '入款交易流水删除', 7054, 3, '#', '', 'F', '0', 'transactionrecord:deposit:remove', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (7059, '入款交易流水重新匹配', 7054, 4, '#', '', 'F', '0', 'transactionrecord:deposit:commit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (7060, '入款交易流水修改备注', 7054, 5, '#', '', 'F', '0', 'transactionrecord:deposit:modifyremark', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (7061, '入款交易流水导出', 7054, 6, '#', '', 'F', '0', 'transactionrecord:deposit:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (8054, '异常交易流水', 2153, 4, '/transactionrecord/exception', '', 'C', '0', 'transactionrecord:exception:view', '#',
        'admin', '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '异常交易流水菜单');
INSERT INTO `sys_menu`
VALUES (8055, '异常交易流水查询', 8054, 0, '#', '', 'F', '0', 'transactionrecord:exception:list', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (8056, '异常交易流水修改', 8054, 1, '#', '', 'F', '0', 'transactionrecord:exception:edit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (8057, '异常交易流水补单', 8054, 2, '#', '', 'F', '0', 'transactionrecord:exception:add', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (8058, '异常交易流水删除', 8054, 3, '#', '', 'F', '0', 'transactionrecord:exception:remove', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (8059, '异常交易流水重新匹配', 8054, 4, '#', '', 'F', '0', 'transactionrecord:exception:commit', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (8060, '异常交易流水修改备注', 8054, 5, '#', '', 'F', '0', 'transactionrecord:exception:modifyremark', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (8061, '异常交易流水导出', 8054, 6, '#', '', 'F', '0', 'transactionrecord:exception:export', '#', 'admin',
        '2020-01-05 00:00:00', 'admin', '2020-01-05 00:00:00', '');



delete from  `sys_role`;
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', 1, 1, '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员', 0);


delete from  `sys_user`;
INSERT INTO `sys_user` VALUES (1, 'admin', 'admin', '00', '/profile/avatar/2020/04/16/afb2bb7fa7e07db3b8edf99a0ce6540d.png', '$2a$13$b7W6z3QJ0bHOZ420Qm2T8uOdY9abe9RjMyLPSQXaC8EPN6SxDWf/S', '0', '0', '10.4.20.222', '2020-04-25 00:34:06', 'admin', '2018-03-16 11:33:00', 'ry', '2020-04-25 00:34:06', 3, 'MWXU4UVQL2OUS4M4', 0, 1);


delete from `sys_user_role`;
INSERT INTO `sys_user_role` VALUES (1, 1);

