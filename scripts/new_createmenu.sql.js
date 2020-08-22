let menujson = require('./new_bizmenu');
let custom = false;
let createMangager = {
    启用: true,
    停用: true,
    重置额度: true
}
let sql = `
USE tysupplieradmin;

`;
let content_order_num = 0;
for (let key in menujson) {
    let contents = menujson[key];
    let modulname = contents['modulename'].split('-').slice(1);
    content_order_num++;
    if (!custom) {
        sql += `
 DELETE FROM sys_menu WHERE menu_id=${contents.menu_id};

INSERT INTO sys_menu (menu_id,menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(${contents.menu_id},'${key}', '0', '${content_order_num}', '#', 'C', '0', '#', 'fa fa-${contents.icon}', 'admin', '2020-01-05', 'admin', '2020-01-05', '${key}-目录');
   `;

    }

    let submenu_order_num = 0;
    for (let submeuKey in contents.submenu) {
        let secondMenu = contents.submenu[submeuKey];
        submenu_order_num++;
        if (!custom) {
            sql += `
DELETE FROM sys_menu WHERE menu_id=${secondMenu.menu_id};

INSERT INTO sys_menu (menu_id,menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(${secondMenu.menu_id},'${secondMenu['cn']}', ${contents.menu_id}, '${submenu_order_num}', '/${modulname.slice(-1)}/${secondMenu['en']}', 'C', '0', '${modulname.slice(-1)}:${secondMenu['en']}:view', '#', 'admin', '2020-01-05', 'admin', '2020-01-05', '${secondMenu['cn']}菜单');
        `;
        }

        secondMenu.rights && secondMenu.rights.forEach((v, order_num) => {
            if (!custom) {
                sql += `
DELETE FROM sys_menu WHERE menu_id=${v.menu_id};

INSERT INTO sys_menu  (menu_id,menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
 VALUES(${v.menu_id},'${secondMenu['cn']}${v.cn}', ${secondMenu.menu_id}, '${order_num}',  '#',  'F', '0', '${modulname.slice(-1)}:${secondMenu['en']}:${v.en}',         '#', 'admin', '2020-01-05', 'admin', '2020-01-05', '');
`;

            }
        });


    }
}

console.log(sql);
