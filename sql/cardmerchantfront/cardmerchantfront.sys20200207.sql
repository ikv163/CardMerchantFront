

-- ----------------------------
-- 2、用户信息表
-- ----------------------------

CREATE TABLE `sys_user`
(
    `user_id`               BIGINT(20)                              NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `login_name`            VARCHAR(30) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '登录账号',
    `user_name`             VARCHAR(30) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '用户昵称',
    `user_type`             VARCHAR(2) COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `avatar`                VARCHAR(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '头像路径',
    `password`              VARCHAR(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
    `status`                CHAR(1) COLLATE utf8mb4_unicode_ci      NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag`              CHAR(1) COLLATE utf8mb4_unicode_ci      NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `login_ip`              VARCHAR(50) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '最后登陆IP',
    `login_date`            DATETIME                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登陆时间',
    `create_by`             VARCHAR(64) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time`           DATETIME                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`             VARCHAR(64) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '更新者',
    `update_time`           DATETIME                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `supplierbranchid`      INT(11)                                 NOT NULL DEFAULT '2' COMMENT 'supplierbranchid',
    `googlecode`            VARCHAR(80) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT 'SN密钥',
    `parent_id`             BIGINT(20)                              NOT NULL DEFAULT '1' COMMENT '父级id',
    `orderallotrule`        INT(11)                                 NOT NULL DEFAULT '0' COMMENT '订单操作角色,0:默认,1:分配员,2:操作员',
    `supplierbranchidgroup` VARCHAR(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '财务分支组',
    PRIMARY KEY (`user_id`)
) AUTO_INCREMENT = 2 COMMENT ='用户信息表';




-- ----------------------------
-- 4、角色信息表

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
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------

CREATE TABLE sys_user_role
(
    user_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
    role_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE = innodb COMMENT = '用户和角色关联表';



-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
CREATE TABLE sys_role_menu
(
    role_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '角色ID',
    menu_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE = innodb COMMENT = '角色和菜单关联表';


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
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


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
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






-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
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
