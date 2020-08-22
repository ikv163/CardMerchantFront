package com.pay.typay.biz.bankcard.controller;

import java.util.Arrays;
import java.util.List;

import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.domain.TBankcardcenter;
import com.pay.typay.biz.service.ITBankcardcenterService;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.biz.bankcard.domain.Statementcenter;
import com.pay.typay.biz.bankcard.service.IStatementcenterService;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.common.core.page.TableDataInfo;

/**
 * 银行卡流水Controller
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
@Controller
@RequestMapping("/bankcard/statementcenter")
public class StatementcenterController extends BaseController
{
    private String prefix = "bankcard/statementcenter";

    @Autowired
    private IStatementcenterService tBanktransService;

    @Autowired
    private ITBankcardcenterService tBankcardService;

    @RequiresPermissions("bankcard:statementcenter:view")
    @GetMapping()
    public String statementcenter()
    {
        return prefix + "/statementcenter";
    }

    /**
     * 查询银行卡流水列表
     */
    @RequiresPermissions("bankcard:statementcenter:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Statementcenter tBanktrans)
    {
        startPage();
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            tBanktrans.setSupplierbranchid(sysUser.getSupplierbranchid());
            tBanktrans.setCreateby(Long.parseLong("0"));
        } else {
            tBanktrans.setCreateby(UtilsUser.getUserId());
        }

        List<Statementcenter> list = tBanktransService.selectStatementcenterList(tBanktrans);
        return getDataTable(list);
    }

    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Statementcenter tBanktrans)
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            tBanktrans.setSupplierbranchid(sysUser.getSupplierbranchid());
            tBanktrans.setCreateby(Long.parseLong("0"));
        } else {
            tBanktrans.setCreateby(UtilsUser.getUserId());
        }

        List<Statementcenter> list = tBanktransService.selectStatementcenterList(tBanktrans);
        ExcelUtil<Statementcenter> util = new ExcelUtil<Statementcenter>(Statementcenter.class);
        return util.exportExcel(list, "银行流水", "银行流水" + DateUtils.getDate());

    }

    /**
     * 生成流水
     * @param mmap
     * @param tBankcard
     * @param type
     * @return
     */
    @GetMapping("/create-trans")
    @RequiresPermissions("bankcard:bankcard_client_in:list")
    public String createTrans(ModelMap mmap,TBankcardcenter tBankcard,String type) {
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            tBankcard.setSupplierBranchIdGroupList(sList);
        } else {
            tBankcard.setSupplierbranchid(userSupplierbranchid);
            tBankcard.setCreateby(UtilsUser.getUserId());
        }
        List<TBankcardcenter> bankcard = tBankcardService.selectTBankcardList(tBankcard);
        mmap.put("bankcard", bankcard);
        mmap.put("type",type);
        return prefix + "/create-trans" ;
    }

    /**
     * 生成流水
     * @param tBanktrans
     * @param tBankcard
     * @return
     */
    @PostMapping("/create-trans-save")
    @ResponseBody
    public AjaxResult createTransSave( Statementcenter tBanktrans,TBankcardcenter tBankcard) {
        tBanktrans.setBalance(1000.00);
        return toAjax(tBanktransService.insertStatementcenter(tBanktrans));
    }

    /**
     * 统计支出和收入数据
     */
    @GetMapping("/total")
    @ResponseBody
    @RequiresPermissions("bankcard:bankcard_client_in:list")
    public TableDataInfo total(Statementcenter tBanktrans) {
        startPage();
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            tBanktrans.setSupplierBranchIdGroupList(sList);
            tBanktrans.setCreateby(Long.parseLong("0"));
        } else {
            tBanktrans.setCreateby(UtilsUser.getUserId());
        }
        List<Statementcenter> total = tBanktransService.getTotal(tBanktrans);
        return getDataTable(total);
    }
}
