
USE tysupplieradmin;



DELETE FROM sys_menu WHERE menu_id=59195573;

INSERT INTO sys_menu (menu_id,menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(59195573,'中转卡池资金监控', 2044, '2', '/monitors/monitorsfundstransitpool', 'C', '0', 'monitors:monitorsfundstransitpool:view', '#', 'admin', '2020-01-05', 'admin', '2020-01-05', '中转卡池资金监控菜单');
        
DELETE FROM sys_menu WHERE menu_id=59195574;

INSERT INTO sys_menu  (menu_id,menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
 VALUES(59195574,'中转卡池资金监控查询', 59195573, '0',  '#',  'F', '0', 'monitors:monitorsfundstransitpool:list',         '#', 'admin', '2020-01-05', 'admin', '2020-01-05', '');




INSERT INTO sys_menu  (menu_id,menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(53192625,'下发银行卡管理重置', 51001, '10',  '#',  'F', '0', 'bankcard:manage_bankcard_delivery:reset',         '#', 'admin', '2020-01-05', 'admin', '2020-01-05', '');



INSERT INTO sys_menu  (menu_id,menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(53192626,'下发银行卡管理校对余额', 51001, '11',  '#',  'F', '0', 'bankcard:manage_bankcard_delivery:checkBalance',         '#', 'admin', '2020-01-05', 'admin', '2020-01-05', '');
