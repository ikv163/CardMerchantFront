-- ----------------------------
-- 2、用户信息表
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user
(

    user_id          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    login_name       VARCHAR(30)  NOT NULL DEFAULT '' COMMENT '登录账号',
    user_name        VARCHAR(30)  NOT NULL DEFAULT '' COMMENT '用户昵称',
    user_type        VARCHAR(2)   NOT NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
    avatar           VARCHAR(100) NOT NULL DEFAULT '' COMMENT '头像路径',
    password         VARCHAR(128) NOT NULL DEFAULT '' COMMENT '密码',
    status           CHAR(1)      NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    del_flag         CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    login_ip         VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '最后登陆IP',
    login_date       DATETIME     NOT NULL DEFAULT now() COMMENT '最后登陆时间',
    create_by        VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    create_time      DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    update_by        VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '更新者',
    update_time      DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    supplierbranchid INT(11)      NOT NULL DEFAULT 2 COMMENT 'supplierbranchid',
    googlecode       VARCHAR(80)  NOT NULL DEFAULT '' COMMENT 'SN密钥',
    parent_id        BIGINT(20)   NOT NULL DEFAULT '1' COMMENT '父级id',
    `orderallotrule`   INT         NOT NULL DEFAULT '0' COMMENT '订单操作角色,0:默认,1:分配员,2:操作员',
    PRIMARY KEY (user_id)

)
    COMMENT '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
INSERT INTO `sys_user` (`user_id`, `login_name`, `user_name`, `user_type`, `avatar`, `password`, `status`, `del_flag`, `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`, `supplierbranchid`, `googlecode`, `parent_id`, `orderallotrule`) VALUES (1,'admin','admin','00','/profile/avatar/2020/04/16/afb2bb7fa7e07db3b8edf99a0ce6540d.png','$2a$13$b7W6z3QJ0bHOZ420Qm2T8uOdY9abe9RjMyLPSQXaC8EPN6SxDWf/S','0','0','0:0:0:0:0:0:0:1','2020-04-22 16:49:22','admin','2018-03-16 11:33:00','ry','2020-04-23 00:49:22',3,'4MDCMNXSB3JNFSIT',0,1);

-- ----------------------------
-- 用户级别的数据权限中间表
-- ----------------------------

DROP TABLE IF EXISTS sys_user_datascope;
CREATE TABLE sys_user_datascope
(
    id           BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    user_id      BIGINT NOT NULL DEFAULT 0 COMMENT '用户id',
    datascope_id BIGINT NOT NULL DEFAULT 0 COMMENT '数据权限id'
) COMMENT 'sys_user_datascope';

CREATE UNIQUE INDEX sys_user_datascope_user_id_uindex
    ON sys_user_datascope (user_id);


-- ----------------------------
-- 数据权限表
-- ----------------------------
DROP TABLE IF EXISTS sys_datascope;
CREATE TABLE sys_datascope
(
    datascope_id   BIGINT AUTO_INCREMENT COMMENT 'datascope_id'
        PRIMARY KEY,
    paychannel_ids VARCHAR(5000) DEFAULT '' NOT NULL COMMENT '数据权限id号集合',
    bankcardpool   VARCHAR(5000) DEFAULT '' NOT NULL COMMENT '卡池集合'

) COMMENT 'sys_datascope';



-- ----------------------------
-- 4、角色信息表
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
    role_id     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '角色ID'
        PRIMARY KEY,
    role_name   VARCHAR(30)  NOT NULL DEFAULT '' COMMENT '角色名称',
    role_key    VARCHAR(100) NOT NULL DEFAULT '' COMMENT '角色权限字符串',
    role_sort   INT(4)       NOT NULL DEFAULT 0 COMMENT '显示顺序',
    data_scope  CHAR         NOT NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    status      CHAR         NOT NULL DEFAULT 1 COMMENT '角色状态（0正常 1停用）',
    del_flag    CHAR         NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    create_time DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    update_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '更新者',
    update_time DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    remark      VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
    parent_id   BIGINT       NOT NULL DEFAULT 1 COMMENT '父级角色id'
)
    COMMENT '角色信息表';



-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '管理员', 'admin', 1, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员', 0);



-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role
(
    user_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
    role_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE = innodb COMMENT = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
INSERT INTO sys_user_role
VALUES ('1', '1');
INSERT INTO sys_user_role
VALUES ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu
(
    role_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '角色ID',
    menu_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE = innodb COMMENT = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log
(
    oper_id        BIGINT(20)    NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    title          VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '模块标题',
    business_type  INT(2)        NOT NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    method         VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '方法名称',
    request_method VARCHAR(10)   NOT NULL DEFAULT '' COMMENT '请求方式',
    operator_type  INT(1)        NOT NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    oper_name      VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '操作人员',
    dept_name      VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '部门名称',
    oper_url       VARCHAR(255)  NOT NULL DEFAULT '' COMMENT '请求URL',
    oper_ip        VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '主机地址',
    oper_location  VARCHAR(255)  NOT NULL DEFAULT '' COMMENT '操作地点',
    oper_param     VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '请求参数',
    json_result    VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '返回参数',
    status         INT(1)        NOT NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    error_msg      VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '错误消息',
    oper_time      DATETIME      NOT NULL DEFAULT now() COMMENT '操作时间',
    PRIMARY KEY (oper_id)
) ENGINE = innodb
  AUTO_INCREMENT = 100 COMMENT = '操作日志记录';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type
(
    dict_id     BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    dict_name   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典名称',
    dict_type   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典类型',
    status      CHAR(1)      NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    create_time DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    update_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '更新者',
    update_time DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    remark      VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (dict_id)

) ENGINE = innodb
  AUTO_INCREMENT = 100 COMMENT = '字典类型表';

INSERT INTO sys_dict_type
VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '用户性别列表');
INSERT INTO sys_dict_type
VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '菜单状态列表');
INSERT INTO sys_dict_type
VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统开关列表');
INSERT INTO sys_dict_type
VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统是否列表');
INSERT INTO sys_dict_type
VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '通知类型列表');
INSERT INTO sys_dict_type
VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '通知状态列表');
INSERT INTO sys_dict_type
VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '操作类型列表');
INSERT INTO sys_dict_type
VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '登录状态列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data
(
    dict_code   BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    dict_sort   INT(4)       NOT NULL DEFAULT 0 COMMENT '字典排序',
    dict_label  VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典标签',
    dict_value  VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典键值',
    dict_type   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '字典类型',
    css_class   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '样式属性（其他样式扩展）',
    list_class  VARCHAR(100) NOT NULL DEFAULT '' COMMENT '表格回显样式',
    is_default  CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    status      CHAR(1)      NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    create_time DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    update_by   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '更新者',
    update_time DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    remark      VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (dict_code)
) ENGINE = innodb
  AUTO_INCREMENT = 100 COMMENT = '字典数据表';

INSERT INTO sys_dict_data
VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00',
        '性别男');
INSERT INTO sys_dict_data
VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00',
        '性别女');
INSERT INTO sys_dict_data
VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00',
        '性别未知');
INSERT INTO sys_dict_data
VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '显示菜单');
INSERT INTO sys_dict_data
VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '隐藏菜单');
INSERT INTO sys_dict_data
VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '正常状态');
INSERT INTO sys_dict_data
VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '停用状态');
INSERT INTO sys_dict_data
VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '系统默认是');
INSERT INTO sys_dict_data
VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '系统默认否');
INSERT INTO sys_dict_data
VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '通知');
INSERT INTO sys_dict_data
VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '公告');
INSERT INTO sys_dict_data
VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '正常状态');
INSERT INTO sys_dict_data
VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '关闭状态');
INSERT INTO sys_dict_data
VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '新增操作');
INSERT INTO sys_dict_data
VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '修改操作');
INSERT INTO sys_dict_data
VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '删除操作');
INSERT INTO sys_dict_data
VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '授权操作');
INSERT INTO sys_dict_data
VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '导出操作');
INSERT INTO sys_dict_data
VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '导入操作');
INSERT INTO sys_dict_data
VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '强退操作');
INSERT INTO sys_dict_data
VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '生成操作');
INSERT INTO sys_dict_data
VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '清空操作');
INSERT INTO sys_dict_data
VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '正常状态');
INSERT INTO sys_dict_data
VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '停用状态');


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config
(
    config_id    INT(5)       NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    config_name  VARCHAR(100) NOT NULL DEFAULT '' COMMENT '参数名称',
    config_key   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '参数键名',
    config_value VARCHAR(500) NOT NULL DEFAULT '' COMMENT '参数键值',
    config_type  CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    create_by    VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '创建者',
    create_time  DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    update_by    VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '更新者',
    update_time  DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    remark       VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (config_id)
) ENGINE = innodb
  AUTO_INCREMENT = 100 COMMENT = '参数配置表';

INSERT INTO sys_config
VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO sys_config
VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '初始化密码 123456');
INSERT INTO sys_config
VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '深黑主题theme-dark，浅色主题theme-light，深蓝主题theme-blue');


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
DROP TABLE IF EXISTS sys_logininfor;
CREATE TABLE sys_logininfor
(
    info_id        BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    login_name     VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '登录账号',
    ipaddr         VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '登录IP地址',
    login_location VARCHAR(255) NOT NULL DEFAULT '' COMMENT '登录地点',
    browser        VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '浏览器类型',
    os             VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '操作系统',
    status         CHAR(1)      NOT NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
    msg            VARCHAR(255) NOT NULL DEFAULT '' COMMENT '提示消息',
    login_time     DATETIME     NOT NULL DEFAULT now() COMMENT '访问时间',
    PRIMARY KEY (info_id)
) ENGINE = innodb
  AUTO_INCREMENT = 100 COMMENT = '系统访问记录';


-- ----------------------------
-- 15、在线用户记录
-- ----------------------------
DROP TABLE IF EXISTS sys_user_online;
CREATE TABLE sys_user_online
(
    id               BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    sessionId        VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '用户会话id',
    login_name       VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '登录账号',
    dept_name        VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '部门名称',
    ipaddr           VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '登录IP地址',
    login_location   VARCHAR(255) NOT NULL DEFAULT '' COMMENT '登录地点',
    browser          VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '浏览器类型',
    os               VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '操作系统',
    status           VARCHAR(10)  NOT NULL DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
    start_timestamp  DATETIME     NOT NULL DEFAULT now() COMMENT 'session创建时间',
    last_access_time DATETIME     NOT NULL DEFAULT now() COMMENT 'session最后访问时间',
    expire_time      INT(5)       NOT NULL DEFAULT 0 COMMENT '超时时间，单位为分钟'
) ENGINE = innodb COMMENT = '在线用户记录';



-- ----------------------------
-- 18、通知公告表
-- ----------------------------
DROP TABLE IF EXISTS sys_notice;
CREATE TABLE sys_notice
(
    notice_id      INT(4)        NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    notice_title   VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '公告标题',
    notice_type    CHAR(1)       NOT NULL DEFAULT 1 COMMENT '公告类型（1通知 2公告）',
    notice_content VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '公告内容',
    status         CHAR(1)       NOT NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    create_by      VARCHAR(64)   NOT NULL DEFAULT '' COMMENT '创建者',
    create_time    DATETIME      NOT NULL DEFAULT now() COMMENT '创建时间',
    update_by      VARCHAR(64)   NOT NULL DEFAULT '' COMMENT '更新者',
    update_time    DATETIME      NOT NULL DEFAULT now() COMMENT '更新时间',
    remark         VARCHAR(255)  NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (notice_id)
) ENGINE = innodb
  AUTO_INCREMENT = 10 COMMENT = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
INSERT INTO sys_notice
VALUES ('1', '温馨提醒：2018-07-01 天下支付新版本发布啦', '2', '新版本内容', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '管理员');
INSERT INTO sys_notice
VALUES ('2', '维护通知：2018-07-01 天下支付系统凌晨维护', '1', '维护内容', '0', 'admin', '2018-03-16 11-33-00', 'ry',
        '2018-03-16 11-33-00', '管理员');


