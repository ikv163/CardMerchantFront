-- ----------------------------
-- 5、菜单权限表
-- ----------------------------


DROP TABLE IF EXISTS sys_menu;

CREATE TABLE sys_menu
(
    menu_id     BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    menu_name   VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '菜单名称',
    parent_id   BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    order_num   INT(4)       NOT NULL DEFAULT 0 COMMENT '显示顺序',
    url         VARCHAR(200) NOT NULL DEFAULT '#' COMMENT '请求地址',
    target      VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
    menu_type   CHAR(1)      NOT NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    visible     CHAR(1)      NOT NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
    perms       VARCHAR(100) NOT NULL DEFAULT '' COMMENT '权限标识',
    icon        VARCHAR(100) NOT NULL DEFAULT '#' COMMENT '菜单图标',
    create_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '更新者',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    remark      VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (menu_id)
) ENGINE = innodb
  AUTO_INCREMENT = 2000 COMMENT = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
INSERT INTO sys_menu
VALUES ('1', '系统管理', '0', '998', '#', '', 'M', '0', '', 'fa fa-gear', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '系统管理目录');

-- 二级菜单
INSERT INTO sys_menu
VALUES ('100', '用户管理', '1', '1', '/system/user', '', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11-33-00',
        'ry', '2018-03-16 11-33-00', '用户管理菜单');
INSERT INTO sys_menu
VALUES ('101', '角色管理', '1', '2', '/system/role', '', 'C', '0', 'system:role:view', '#', 'admin', '2018-03-16 11-33-00',
        'ry', '2018-03-16 11-33-00', '角色管理菜单');
INSERT INTO sys_menu
VALUES ('102', '菜单管理', '1', '3', '/system/menu', '', 'C', '1', 'system:menu:view', '#', 'admin', '2018-03-16 11-33-00',
        'ry', '2018-03-16 11-33-00', '菜单管理菜单');
INSERT INTO sys_menu
VALUES ('107', '通知公告', '1', '8', '/system/notice', '', 'C', '1', 'system:notice:view', '#', 'admin',
        '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '通知公告菜单');



-- 用户管理按钮
INSERT INTO sys_menu
VALUES ('1000', '用户查询', '100', '1', '#', '', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1001', '用户新增', '100', '2', '#', '', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1002', '用户修改', '100', '3', '#', '', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1003', '用户删除', '100', '4', '#', '', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1004', '用户导出', '100', '5', '#', '', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1005', '用户导入', '100', '6', '#', '', 'F', '0', 'system:user:import', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1006', '重置密码', '100', '7', '#', '', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11-33-00',
        'ry', '2018-03-16 11-33-00', '');
-- 角色管理按钮
INSERT INTO sys_menu
VALUES ('1007', '角色查询', '101', '1', '#', '', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1008', '角色新增', '101', '2', '#', '', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1009', '角色修改', '101', '3', '#', '', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1010', '角色删除', '101', '4', '#', '', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
INSERT INTO sys_menu
VALUES ('1011', '角色导出', '101', '5', '#', '', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '');
