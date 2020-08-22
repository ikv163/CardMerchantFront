package com.pay.typay.admin.controller.system;

import com.pay.typay.common.config.Global;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysMenu;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页 业务处理
 *
 * @author js-oswald
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("userid", user.getUserId());
        mmap.put("supplierbranchid", user.getSupplierbranchid());
        mmap.put("updateTime", user.getUpdateTime());
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("demoEnabled", Global.isDemoEnabled());
        return "index";
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap) {
        return "skin";
    }

    // 切换主题
    @GetMapping("/system/switchSuppliderbranchid")
    public String switchSuppliderbranchid(ModelMap mmap) {
        SysUser sysUser = ShiroUtils.getSysUser();
        Long supplierbranchid = sysUser.getSupplierbranchid();
        mmap.put("supplierbranchid", supplierbranchid);
        return "switchSuppliderbranchid";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", Global.getVersion());
        return "main";
    }
}
