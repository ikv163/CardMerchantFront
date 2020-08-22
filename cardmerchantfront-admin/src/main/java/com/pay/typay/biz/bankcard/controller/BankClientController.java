package com.pay.typay.biz.bankcard.controller;

import com.pay.typay.biz.domain.BankClient;
import com.pay.typay.biz.domain.TBankcardcenter;
import com.pay.typay.biz.messages.ConstantsSelectUI;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.biz.service.IBankClientService;
import com.pay.typay.biz.service.ITBankcardcenterService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.Translate;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 银行卡客户端Controller
 *
 * @author js-bucky
 * @date 2020-01-08
 */
@Controller
@RequestMapping("/bankcard/bankcard_client")
public class BankClientController extends BaseController {
    private String prefix = "bankcard/bankcard_client";

    @Autowired
    private IBankClientService bankClientService;

    @Autowired
    private ITBankcardcenterService tBankcardService;


    @RequiresPermissions("bankcard:bankcard_client:view")
    @GetMapping()
    public String bankcard_client() {
        return prefix + "/bankcard_client";
    }

    /**
     * 查询银行卡客户端列表
     */
    @RequiresPermissions("bankcard:bankcard_client:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BankClient bankClient) {
        startPage();
//        SysUser sysUser = ShiroUtils.getSysUser();
//        if (sysUser.isAdmin()) {  //如果是管理员账号
//            String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
//            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
//            bankClient.setSupplierBranchIdGroupList(sList);
//        } else {
//            bankClient.setSupplierBranchID(UtilsUser.getUserSupplierbranchid());
//        }
        bankClient.setSupplierBranchID(UtilsUser.getUserSupplierbranchid());
        bankClient.setSurplierType(1);
        List<BankClient> list = bankClientService.selectBankClientList(bankClient);
        return getDataTable(list);
    }
    /**
     * 查询银行卡客户端列表
     */
    @RequiresPermissions("bankcard:bankcard_client:list")
    @PostMapping("/check")
    @ResponseBody
    public TableDataInfo check(BankClient bankClient) {
        startPage();
        List<BankClient> list = bankClientService.check(bankClient);
        return getDataTable(list);
    }

    /**
    /**
     * 导出银行卡客户端列表
     */
    @RequiresPermissions("bankcard:bankcard_client:export")
    @Log(title = "导出银行卡客户端", businessType = BusinessType.EXPORT,
            translate = {@Translate(filed = "workType",data = TranslateKeyConstant.ClientWorkType),
                    @Translate(filed = "status",data = TranslateKeyConstant.ClientStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.BankClient_Key)})
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BankClient bankClient) {
        bankClient.setSupplierBranchID(UtilsUser.getUserSupplierbranchid());
        bankClient.setSurplierType(1);
        List<BankClient> list = bankClientService.selectBankClientList(bankClient);
        ExcelUtil<BankClient> util = new ExcelUtil<BankClient>(BankClient.class);
        return util.exportExcel(list, "bankcard_client");
    }

    /**
     * 新增银行卡客户端
     */
    @GetMapping("/add")
    @RequiresPermissions("bankcard:bankcard_client:list")
    public String add(ModelMap mmap,TBankcardcenter tBankcard) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            tBankcard.setSupplierBranchIdGroupList(sList);
        } else {
            Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
            tBankcard.setSupplierbranchid(userSupplierbranchid);
            tBankcard.setCreateby(UtilsUser.getUserId());
        }
        List<TBankcardcenter> bankcard = tBankcardService.selectTBankcardList(tBankcard);
        mmap.put("bankcard", bankcard);
        return prefix + "/add";
    }

    /**
     * 新增保存银行卡客户端
     */
    @RequiresPermissions("bankcard:bankcard_client:add")
    @Log(title = "新增银行卡客户端", businessType = BusinessType.INSERT,
            translate = {@Translate(filed = "workType",data = TranslateKeyConstant.ClientWorkType),
                    @Translate(filed = "status",data = TranslateKeyConstant.ClientStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.BankClient_Key)})
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BankClient bankClient) {
        bankClient.setCreatetime(new Date());
        bankClient.setCreateby(UtilsUser.getUserId());
        bankClient.setSurplierType(1);
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {  //如果是管理员账号
            bankClient.setSupplierBranchID(UtilsUser.getUserSupplierbranchid());
        }
        return toAjax(bankClientService.insertBankClient(bankClient));
    }

    /**
     * 修改银行卡客户端
     */
    @GetMapping("/edit/{clientId}")
    @RequiresPermissions("bankcard:bankcard_client:list")
    public String edit(@PathVariable("clientId") Long clientId, ModelMap mmap) {
        BankClient bankClient = bankClientService.selectBankClientById(clientId);
        mmap.put("bankClient", bankClient);
        return prefix + "/edit";
    }

    /**
     * 查看银行卡客户端
     */
    @GetMapping("/proview/{clientId}")
    @RequiresPermissions("bankcard:bankcard_client:list")
    public String proview(@PathVariable("clientId") Long clientId, ModelMap mmap) {
        BankClient bankClient = bankClientService.selectBankClientById(clientId);
        mmap.put("bankClient", bankClient);
        return prefix + "/proview";
    }

    /**
     * 修改保存银行卡客户端
     */
    @RequiresPermissions("bankcard:bankcard_client:edit")
    @Log(title = "修改银行卡客户端", businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "workType",data = TranslateKeyConstant.ClientWorkType),
                    @Translate(filed = "status",data = TranslateKeyConstant.ClientStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.BankClient_Key)})
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BankClient bankClient) {
        bankClient.setUpdatetime(new Date());
        bankClient.setUpdateby(UtilsUser.getUserId());
        return toAjax(bankClientService.updateBankClient(bankClient));
    }

    /**
     * 删除银行卡客户端
     */
    @RequiresPermissions("bankcard:bankcard_client:remove")
    @Log(title = "删除银行卡客户端", businessType = BusinessType.DELETE,
            translate = {@Translate(filed = "workType",data = TranslateKeyConstant.ClientWorkType),
                    @Translate(filed = "status",data = TranslateKeyConstant.ClientStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.BankClient_Key)})
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bankClientService.deleteBankClientByIds(ids));
    }


    /**
     * 删除银行卡客户端
     */
    @RequiresPermissions("bankcard:bankcard_client:remove")
    @Log(title = "删除银行卡客户端", businessType = BusinessType.DELETE,
            translate = {@Translate(filed = "workType",data = TranslateKeyConstant.ClientWorkType),
                    @Translate(filed = "status",data = TranslateKeyConstant.ClientStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.BankClient_Key)})
    @PostMapping("/statusedit")
    @ResponseBody
    public AjaxResult statusedit(String ids) {
        return toAjax(bankClientService.updateStatusByIds(ids));
    }


}
