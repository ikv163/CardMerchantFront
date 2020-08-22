package com.pay.typay.admin.controller.monitor;

import com.pay.typay.biz.dict.GoogleCodeService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.exception.BusinessException;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.shiro.service.SysPasswordService;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysLogininfor;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysLogininforService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author js-oswald
 */
@Controller
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
    private String prefix = "monitor/logininfor";

    @Autowired
    private ISysLogininforService logininforService;

    @Autowired
    private SysPasswordService passwordService;

    @RequiresPermissions("monitor:logininfor:view")
    @GetMapping()
    public String logininfor() {
        return prefix + "/logininfor";
    }

    @RequiresPermissions("monitor:logininfor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysLogininfor logininfor) {
        startPage();
        SysUser sysUser = ShiroUtils.getSysUser();
        Long supplierbranchid = sysUser.getSupplierbranchid();
        logininfor.setSupplierbranchid(supplierbranchid);
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "登陆日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:logininfor:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysLogininfor logininfor) {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        return util.exportExcel(list, "登陆日志");
    }

//    @RequiresPermissions("monitor:logininfor:remove")
//    @Log(title = "登陆日志" , businessType = BusinessType.DELETE)
//    @PostMapping("/remove")
//    @ResponseBody
//    public AjaxResult remove(String ids) {
//        return toAjax(logininforService.deleteLogininforByIds(ids));
//    }
//
//    @RequiresPermissions("monitor:logininfor:remove")
//    @Log(title = "登陆日志" , businessType = BusinessType.CLEAN)
//    @PostMapping("/clean")
//    @ResponseBody
//    public AjaxResult clean() {
//        logininforService.cleanLogininfor();
//        return success();
//    }

    //    @RequiresPermissions("monitor:logininfor:unlock")
    @Autowired
    private
    GoogleCodeService googleCodeService;
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @PostMapping("/unlock")
    @ResponseBody
    public AjaxResult unlock(String loginName,String verifycode) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {
            throw new BusinessException("需要管理员权限才可以对此操作");
        }
        
        try {
            googleCodeService.verifyGooglecode(verifycode);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        passwordService.unlock(loginName);
        return success();
    }
}
