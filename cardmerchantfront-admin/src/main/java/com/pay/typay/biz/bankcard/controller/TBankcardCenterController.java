package com.pay.typay.biz.bankcard.controller;

import com.pay.typay.biz.bankcard.domain.BankcardReview;
import com.pay.typay.biz.bankcard.service.IBankcardReviewService;
import com.pay.typay.biz.dict.GoogleCodeService;
import com.pay.typay.biz.domain.TBankcardcenter;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.biz.service.ITBankcardcenterService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 银行卡Controller
 *
 * @author oswald
 * @date 2020-01-06
 */
@Controller
@RequestMapping("/bankcard/manage_bankcard_center")
public class TBankcardCenterController extends BaseController {
    private String prefix = "bankcard/manage_bankcard_center";

    @Autowired
    private ITBankcardcenterService tBankcardService;

    @Autowired
    private IBankcardReviewService tAgentBankcardReviewService;

    @RequiresPermissions("bankcard:manage_bankcard_center:view")
    @GetMapping()
    public String manage_bankcard_center() {
        return prefix + "/manage_bankcard_center";
    }

    /**
     * 查询银行卡列表
     */
    @RequiresPermissions("bankcard:manage_bankcard_center:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TBankcardcenter tBankcard) {
        startPage();
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        SysUser sysUser = ShiroUtils.getSysUser();
        //如果是管理员账号
        if (!sysUser.isAdmin()) {
            tBankcard.setCreateby(UtilsUser.getUserId());
        }
        tBankcard.setSupplierbranchid(userSupplierbranchid);
        List<TBankcardcenter> list = tBankcardService.selectTBankcardList(tBankcard);
        return getDataTable(list);
    }

    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TBankcardcenter tBankcard)
    {
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        SysUser sysUser = ShiroUtils.getSysUser();
        //如果是管理员账号
        if (!sysUser.isAdmin()) {
            tBankcard.setCreateby(UtilsUser.getUserId());
        }
        tBankcard.setSupplierbranchid(userSupplierbranchid);
        List<TBankcardcenter> list = tBankcardService.selectTBankcardList(tBankcard);
        ExcelUtil<TBankcardcenter> util = new ExcelUtil<TBankcardcenter>(TBankcardcenter.class);
        return util.exportExcel(list, "银行卡", "银行卡" + DateUtils.getDate());
    }

    /**
     * 查询银行卡列表
     */
    @RequiresPermissions("bankcard:manage_bankcard_center:list")
    @PostMapping("/check")
    @ResponseBody
    public TableDataInfo check(TBankcardcenter tBankcard) {
        startPage();
        List<TBankcardcenter> list = tBankcardService.selectCheck(tBankcard);
        return getDataTable(list);
    }

    /**
     * 查询银行卡列表
     */
    @PostMapping("/listByWorktype1")
    @ResponseBody
    @RequiresPermissions("bankcard:manage_bankcard_center:list")
    public TableDataInfo listByWorktype1(TBankcardcenter tBankcard) {
        tBankcard.setSurplier_type(1L);
        List<TBankcardcenter> list = tBankcardService.selectListByWorktype(tBankcard);
        return getDataTable(list);
    }

    /**
     * 查询银行卡列表
     */
    @RequiresPermissions("bankcard:manage_bankcard_center:list")
    @GetMapping("/total")
    @ResponseBody
    public TableDataInfo total(TBankcardcenter tBankcard) {
        startPage();
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

        List<TBankcardcenter> total = tBankcardService.getBankcardTotal(tBankcard);
        return getDataTable(total);
    }


    /**
     * 获取金额统计
     * 余额，今日交易金额，累积金额
     */
    @RequiresPermissions("bankcard:manage_bankcard_center:list")
    @GetMapping("/getSummary")
    @ResponseBody
    public TableDataInfo getSummary(TBankcardcenter tBankcard) {
        startPage();
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
        List<TBankcardcenter> total = tBankcardService.getSummary(tBankcard);
        List<TBankcardcenter> total2 = total;
        return getDataTable(total);
    }



    /**
     * 新增银行卡
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("roleid", ShiroUtils.getSysUser().getRoleId());
        return prefix + "/add";
    }

    /**
     * 新增保存银行卡
     */
    @RequiresPermissions("bankcard:manage_bankcard_center:add")
    @Log(title = "新增银行卡", businessType = BusinessType.INSERT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.BankCard_Key))
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TBankcardcenter tBankcard) {

        TBankcardcenter bankcardcenter = new TBankcardcenter();
        bankcardcenter.setBankcode(tBankcard.getBankcode());
        List<TBankcardcenter> list = tBankcardService.selectCheck(bankcardcenter);
        if (!list.isEmpty()) {
            return error("银行卡简码已存在");
        }

        tBankcard.setCreateby(ShiroUtils.getUserId());
        tBankcard.setCreatetime(new Date());
        return toAjax(tBankcardService.insertTBankcard(tBankcard));
    }

    @Autowired
    private GoogleCodeService googleCodeService;

    /**
     * 修改保存银行卡
     */
    @Log(title = "修改银行卡", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.BankCard_Key))
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TBankcardcenter tBankcard) {
        try {
            if (StringUtils.isNotEmpty(tBankcard.getGooglecode())) {
                googleCodeService.verifyGooglecode(tBankcard.getGooglecode());
            }
        } catch (Exception e) {
            return error("验证码错误");
        }
        //银行卡详情修改 验证银行卡简码是否重复，批量修改银行卡状态不验证银行卡简码
        if (StringUtils.isNotBlank(tBankcard.getBankcode()) && null != tBankcard.getBankid()) {
            TBankcardcenter bankcardcenter = new TBankcardcenter();
            bankcardcenter.setBankcode(tBankcard.getBankcode());
            List<TBankcardcenter> list = tBankcardService.selectCheck(bankcardcenter);
            if (!list.isEmpty()) {
                if (list.size() > 1) {
                    return error("银行卡简码已存在");
                }
                if (!list.get(0).getBankid().equals(tBankcard.getBankid())) {
                    return error("银行卡简码已存在");
                }
            }
        }

        tBankcard.setUpdateby(ShiroUtils.getUserId());
        tBankcard.setUpdateTime(new Date());
        return toAjax(tBankcardService.updateTBankcard(tBankcard));
    }


    /**
     * 修改保存银行卡
     */
    @Log(title = "修改银行卡", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.BankCard_Key))
    @PostMapping("/application")
    @ResponseBody
    public AjaxResult application(TBankcardcenter tBankcard) {
        try {
            if (StringUtils.isNotEmpty(tBankcard.getGooglecode())) {
                googleCodeService.verifyGooglecode(tBankcard.getGooglecode());
            }
        } catch (Exception e) {
            return error("验证码错误");
        }
        SysUser sysUser = ShiroUtils.getSysUser();
        //银行卡详情修改 验证银行卡简码是否重复，批量修改银行卡状态不验证银行卡简码
        //查询申请是否存在
        if (!ObjectUtils.isEmpty(tAgentBankcardReviewService.selectBankcardReviewByBankId(tBankcard.getBankid()))) {
            return error("该卡存在修改卡类型申请，请耐心等待审核");
        }
        BankcardReview bankcardReview = new BankcardReview();
        bankcardReview.setBankcardId(tBankcard.getBankid());
        bankcardReview.setBeforWorktype(tBankcard.getBeforWorkType());
        bankcardReview.setAfterWorktype(tBankcard.getWorktype().intValue());
        bankcardReview.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        bankcardReview.setAgentId(Long.valueOf(tBankcard.getAgentId()));
        bankcardReview.setCreateTime(new Date());
        bankcardReview.setReviewTime(new Date());
        bankcardReview.setApplicantName(sysUser.getUserName());
        bankcardReview.setApplicantId(sysUser.getUserId());
        tBankcard.setUpdateby(ShiroUtils.getUserId());
        tBankcard.setUpdateTime(new Date());
        tAgentBankcardReviewService.insertBankcardReview(bankcardReview);
        tBankcard.setWorktype(bankcardReview.getBeforWorktype().longValue());
        return toAjax(tBankcardService.updateTBankcard(tBankcard));
    }

    /**
     * 修改银行卡
     */
    @GetMapping("/edit/{bankid}")
    @RequiresPermissions("bankcard:manage_bankcard_center:list")
    public String edit(@PathVariable("bankid") Long bankid, ModelMap mmap) {
        TBankcardcenter tBankcard = tBankcardService.selectTBankcardById(bankid);
        mmap.put("tBankcard", tBankcard);
        return prefix + "/edit";
    }

    @GetMapping("/proview/{bankid}")
    @RequiresPermissions("bankcard:manage_bankcard_center:list")
    public String proview(@PathVariable("bankid") Long bankid, ModelMap mmap) {
        TBankcardcenter tBankcard = tBankcardService.selectTBankcardById(bankid);
        mmap.put("tBankcard", tBankcard);
        return prefix + "/proview";
    }

    /**
     * 删除银行卡
     */
    @RequiresPermissions("bankcard:manage_bankcard_center:remove")
    @Log(title = "删除银行卡", businessType = BusinessType.DELETE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.BankCard_Key))
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tBankcardService.deleteTBankcardByIds(ids));
    }
}
