
-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
INSERT INTO `sys_user` (`user_id`, `login_name`, `user_name`, `user_type`, `avatar`, `password`, `status`, `del_flag`,
                        `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`,
                        `supplierbranchid`, `googlecode`, `parent_id`, `orderallotrule`, `supplierbranchidgroup`)
VALUES (1, 'boss', 'boss', '00', '', '$2a$13$b7W6z3QJ0bHOZ420Qm2T8uOdY9abe9RjMyLPSQXaC8EPN6SxDWf/S', '0', '0',
        '0:0:0:0:0:0:0:1', '2020-05-11 11:54:40', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-11 19:54:39', 3,
        '4MDCMNXSB3JNFSIT', 0, 1, '');

INSERT INTO `sys_user` (`user_id`, `login_name`, `user_name`, `user_type`, `avatar`, `password`, `status`, `del_flag`,
                        `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`,
                        `supplierbranchid`, `googlecode`, `parent_id`, `orderallotrule`, `supplierbranchidgroup`)
VALUES (2, '普通角色', 'common', '00', '', '$2a$13$b7W6z3QJ0bHOZ420Qm2T8uOdY9abe9RjMyLPSQXaC8EPN6SxDWf/S', '0', '0',
        '0:0:0:0:0:0:0:1', '2020-05-11 11:54:40', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-11 19:54:39', 3,
        '4MDCMNXSB3JNFSIT', 0, 1, '普通角色');




-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '管理员', 'admin', 1, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员', 0);
INSERT INTO sys_role
VALUES ('2', '普通角色',  'common', 2, 2, '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '普通角色',1);


-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
INSERT INTO sys_user_role
VALUES ('1', '1');
INSERT INTO sys_user_role
VALUES ('2', '2');


-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
INSERT INTO sys_role_menu
VALUES ('2', '1');
INSERT INTO sys_role_menu
VALUES ('2', '2');
INSERT INTO sys_role_menu
VALUES ('2', '3');
INSERT INTO sys_role_menu
VALUES ('2', '100');
INSERT INTO sys_role_menu
VALUES ('2', '101');
INSERT INTO sys_role_menu
VALUES ('2', '102');
INSERT INTO sys_role_menu
VALUES ('2', '103');
INSERT INTO sys_role_menu
VALUES ('2', '104');
INSERT INTO sys_role_menu
VALUES ('2', '105');
INSERT INTO sys_role_menu
VALUES ('2', '106');
INSERT INTO sys_role_menu
VALUES ('2', '107');
INSERT INTO sys_role_menu
VALUES ('2', '108');
INSERT INTO sys_role_menu
VALUES ('2', '109');
INSERT INTO sys_role_menu
VALUES ('2', '110');
INSERT INTO sys_role_menu
VALUES ('2', '111');
INSERT INTO sys_role_menu
VALUES ('2', '112');
INSERT INTO sys_role_menu
VALUES ('2', '113');
INSERT INTO sys_role_menu
VALUES ('2', '114');
INSERT INTO sys_role_menu
VALUES ('2', '115');
INSERT INTO sys_role_menu
VALUES ('2', '500');
INSERT INTO sys_role_menu
VALUES ('2', '501');
INSERT INTO sys_role_menu
VALUES ('2', '1000');
INSERT INTO sys_role_menu
VALUES ('2', '1001');
INSERT INTO sys_role_menu
VALUES ('2', '1002');
INSERT INTO sys_role_menu
VALUES ('2', '1003');
INSERT INTO sys_role_menu
VALUES ('2', '1004');
INSERT INTO sys_role_menu
VALUES ('2', '1005');
INSERT INTO sys_role_menu
VALUES ('2', '1006');
INSERT INTO sys_role_menu
VALUES ('2', '1007');
INSERT INTO sys_role_menu
VALUES ('2', '1008');
INSERT INTO sys_role_menu
VALUES ('2', '1009');
INSERT INTO sys_role_menu
VALUES ('2', '1010');
INSERT INTO sys_role_menu
VALUES ('2', '1011');
INSERT INTO sys_role_menu
VALUES ('2', '1012');
INSERT INTO sys_role_menu
VALUES ('2', '1013');
INSERT INTO sys_role_menu
VALUES ('2', '1014');
INSERT INTO sys_role_menu
VALUES ('2', '1015');
INSERT INTO sys_role_menu
VALUES ('2', '1016');
INSERT INTO sys_role_menu
VALUES ('2', '1017');
INSERT INTO sys_role_menu
VALUES ('2', '1018');
INSERT INTO sys_role_menu
VALUES ('2', '1019');
INSERT INTO sys_role_menu
VALUES ('2', '1020');
INSERT INTO sys_role_menu
VALUES ('2', '1021');
INSERT INTO sys_role_menu
VALUES ('2', '1022');
INSERT INTO sys_role_menu
VALUES ('2', '1023');
INSERT INTO sys_role_menu
VALUES ('2', '1024');
INSERT INTO sys_role_menu
VALUES ('2', '1025');
INSERT INTO sys_role_menu
VALUES ('2', '1026');
INSERT INTO sys_role_menu
VALUES ('2', '1027');
INSERT INTO sys_role_menu
VALUES ('2', '1028');
INSERT INTO sys_role_menu
VALUES ('2', '1029');
INSERT INTO sys_role_menu
VALUES ('2', '1030');
INSERT INTO sys_role_menu
VALUES ('2', '1031');
INSERT INTO sys_role_menu
VALUES ('2', '1032');
INSERT INTO sys_role_menu
VALUES ('2', '1033');
INSERT INTO sys_role_menu
VALUES ('2', '1034');
INSERT INTO sys_role_menu
VALUES ('2', '1035');
INSERT INTO sys_role_menu
VALUES ('2', '1036');
INSERT INTO sys_role_menu
VALUES ('2', '1037');
INSERT INTO sys_role_menu
VALUES ('2', '1038');
INSERT INTO sys_role_menu
VALUES ('2', '1039');
INSERT INTO sys_role_menu
VALUES ('2', '1040');
INSERT INTO sys_role_menu
VALUES ('2', '1041');
INSERT INTO sys_role_menu
VALUES ('2', '1042');
INSERT INTO sys_role_menu
VALUES ('2', '1043');
INSERT INTO sys_role_menu
VALUES ('2', '1044');
INSERT INTO sys_role_menu
VALUES ('2', '1045');
INSERT INTO sys_role_menu
VALUES ('2', '1046');
INSERT INTO sys_role_menu
VALUES ('2', '1047');
INSERT INTO sys_role_menu
VALUES ('2', '1048');
INSERT INTO sys_role_menu
VALUES ('2', '1049');
INSERT INTO sys_role_menu
VALUES ('2', '1050');
INSERT INTO sys_role_menu
VALUES ('2', '1051');
INSERT INTO sys_role_menu
VALUES ('2', '1052');
INSERT INTO sys_role_menu
VALUES ('2', '1053');
INSERT INTO sys_role_menu
VALUES ('2', '1054');
INSERT INTO sys_role_menu
VALUES ('2', '1055');
INSERT INTO sys_role_menu
VALUES ('2', '1056');
INSERT INTO sys_role_menu
VALUES ('2', '1057');
INSERT INTO sys_role_menu
VALUES ('2', '1058');
INSERT INTO sys_role_menu
VALUES ('2', '1059');
INSERT INTO sys_role_menu
VALUES ('2', '1060');
INSERT INTO sys_role_menu
VALUES ('2', '1061');



INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`,
                             `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`,
                             `update_time`, `remark`)
VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '性别男'),
       (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '性别女'),
       (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '性别未知'),
       (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '显示菜单'),
       (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '隐藏菜单'),
       (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '正常状态'),
       (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '停用状态'),
       (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '系统默认是'),
       (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '系统默认否'),
       (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '通知'),
       (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '公告'),
       (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '正常状态'),
       (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '关闭状态'),
       (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '新增操作'),
       (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '修改操作'),
       (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '删除操作'),
       (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '授权操作'),
       (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '导出操作'),
       (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '导入操作'),
       (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '强退操作'),
       (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '生成操作'),
       (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '清空操作'),
       (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '正常状态'),
       (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '停用状态'),
       (101, 1, '一代', '1', 'agent_level', '', '', 'Y', '0', 'admin', '2020-05-15 14:48:12', 'admin',
        '2020-05-15 14:49:41', '一代'),
       (102, 2, '二代', '2', 'agent_level', '', '', 'Y', '0', 'admin', '2020-05-15 14:48:24', 'admin',
        '2020-05-15 14:49:46', '二代'),
       (103, 3, '三代', '3', 'agent_level', '', '', 'Y', '0', 'admin', '2020-05-15 14:48:39', 'admin',
        '2020-05-15 14:49:51', '三代'),
       (104, 0, '启用', '1', 'bankcardstatus', '', 'success', 'Y', '0', 'admin', '2020-05-15 20:53:52', 'admin',
        '2020-05-15 20:56:28', ''),
       (105, 1, '停用', '0', 'bankcardstatus', '', 'danger', 'Y', '0', 'admin', '2020-05-15 20:54:48', 'admin',
        '2020-05-15 20:55:00', ''),
       (107, 1, '收款', '1', 'bankcardworktype', '', 'primary', 'Y', '0', 'admin', '2020-05-15 20:58:00', 'admin',
        '2020-05-15 20:58:24', ''),
       (108, 2, '出款', '2', 'bankcardworktype', '', 'primary', 'Y', '0', 'admin', '2020-05-15 20:58:14', '',
        '2020-05-15 20:58:14', ''),
       (109, 3, '中转', '3', 'bankcardworktype', '', 'primary', 'Y', '0', 'admin', '2020-05-15 20:58:45', '',
        '2020-05-15 20:58:45', ''),
       (110, 0, '卡转卡', '0', 'payment_type', '', '', 'Y', '0', 'admin', '2020-05-15 22:42:19', '', '2020-05-15 22:42:19',
        '卡转卡'),
       (111, 1, '支付宝转卡', '1', 'payment_type', '', '', 'Y', '0', 'admin', '2020-05-15 22:42:33', '',
        '2020-05-15 22:42:33', '支付宝转卡'),
       (112, 0, '用户充值', '0', 'trans_type', '', '', 'Y', '0', 'admin', '2020-05-15 22:44:10', '', '2020-05-15 22:44:10',
        '用户充值'),
       (113, 1, '用户提款', '1', 'trans_type', '', '', 'Y', '0', 'admin', '2020-05-15 22:44:23', '', '2020-05-15 22:44:23',
        '用户提款'),
       (114, 2, '额度转换', '2', 'trans_type', '', '', 'Y', '0', 'admin', '2020-05-15 22:44:36', '', '2020-05-15 22:44:36',
        '额度转换'),
       (115, 3, '中转', '3', 'trans_type', '', '', 'Y', '0', 'admin', '2020-05-15 22:45:38', '', '2020-05-15 22:45:38',
        '中转'),
       (116, 0, '已支付', '0', 'order_status', '', '', 'Y', '0', 'admin', '2020-05-15 22:46:52', '', '2020-05-15 22:46:52',
        '已支付'),
       (117, 1, '已补单', '1', 'order_status', '', '', 'Y', '0', 'admin', '2020-05-15 22:47:04', '', '2020-05-15 22:47:04',
        '已补单'),
       (118, 0, '创建', '0', 'depositestatus', '', 'primary', 'Y', '0', 'admin', '2020-05-16 17:36:39', 'admin',
        '2020-05-16 21:51:47', ''),
       (119, 1, '处理中', '1', 'depositestatus', '', 'success', 'Y', '0', 'admin', '2020-05-16 17:37:20', 'admin',
        '2020-05-16 21:51:38', ''),
       (120, 2, '清算中', '2', 'depositestatus', '', 'success', 'Y', '0', 'admin', '2020-05-16 17:37:52', 'admin',
        '2020-05-16 21:51:32', ''),
       (121, 3, '正常支付', '3', 'depositestatus', '', 'success', 'Y', '0', 'admin', '2020-05-16 17:38:08', 'admin',
        '2020-05-16 21:51:25', ''),
       (122, 4, '手工确认', '4', 'depositestatus', '', 'success', 'Y', '0', 'admin', '2020-05-16 17:38:25', 'admin',
        '2020-05-16 21:51:19', ''),
       (123, 7, '取消', '7', 'depositestatus', '', 'danger', 'Y', '0', 'admin', '2020-05-16 17:38:51', 'admin',
        '2020-05-16 21:51:10', ''),
       (124, 6, '超时', '6', 'depositestatus', '', 'warning', 'Y', '0', 'admin', '2020-05-16 17:39:01', 'admin',
        '2020-05-16 21:51:00', ''),
       (125, 5, '超时', '5', 'depositestatus', '', 'warning', 'Y', '0', 'admin', '2020-05-16 17:39:26', 'admin',
        '2020-05-16 21:50:50', '');


INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`,
                             `update_time`, `remark`)
VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户性别列表'),
       (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单状态列表'),
       (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统开关列表'),
       (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统是否列表'),
       (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知类型列表'),
       (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知状态列表'),
       (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作类型列表'),
       (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录状态列表'),
       (100, '代理层级', 'agent_level', '0', 'admin', '2020-05-15 14:46:20', '', '2020-05-15 14:46:20', '代理层级'),
       (101, '银行卡状态', 'bankcardstatus', '0', 'admin', '2020-05-15 20:52:58', '', '2020-05-15 20:52:58', ''),
       (102, '银行卡工作模式', 'bankcardworktype', '0', 'admin', '2020-05-15 20:57:13', '', '2020-05-15 20:57:13', ''),
       (103, '渠道支付方式', 'payment_type', '0', 'admin', '2020-05-15 22:41:52', '', '2020-05-15 22:41:52', '渠道支付方式'),
       (104, '交易类型', 'trans_type', '0', 'admin', '2020-05-15 22:43:30', '', '2020-05-15 22:43:30', '交易类型'),
       (105, '订单状态', 'order_status', '0', 'admin', '2020-05-15 22:46:23', '', '2020-05-15 22:46:23', '订单状态'),
       (106, '订单状态', 'depositestatus', '0', 'admin', '2020-05-16 17:36:05', '', '2020-05-16 17:36:05',
        '订单状态0:创建，默认值;1：处理中;2:清算中;3:正常支付关闭;4：手工确认订单已完成关闭; 5:超时,6:超时关闭；7:取消关闭');
