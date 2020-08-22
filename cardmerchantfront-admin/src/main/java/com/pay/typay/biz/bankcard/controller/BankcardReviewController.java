package com.pay.typay.biz.bankcard.controller;

import java.util.Arrays;
import java.util.List;

import com.pay.typay.biz.bankcard.domain.BankcardReview;
import com.pay.typay.biz.bankcard.service.IBankcardReviewService;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.utils.poi.ExcelUtil;
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
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;

/**
 * 代理银行卡审核Controller
 * 
 * @author ruoyi
 * @date 2020-07-19
 */
@Controller
@RequestMapping("/bancard/bancard_review")
public class BankcardReviewController extends BaseController
{
    private String prefix = "bankcard/bancard_review";

    @Autowired
    private IBankcardReviewService tAgentBankcardReviewService;

    @RequiresPermissions("bankcard:bankcardReview:view")
    @GetMapping()
    public String bancard_review()
    {
        return prefix + "/bankcardReview";
    }

    /**
     * 查询代理银行卡审核列表
     */
    @PostMapping("/list")
    @ResponseBody
    @RequiresPermissions("bankcard:bankcardReview:list")
    public TableDataInfo list(BankcardReview tAgentBankcardReview)
    {
        startPage();
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            tAgentBankcardReview.setSupplierBranchIdGroupList(sList);
        } else {
            tAgentBankcardReview.setSupplierbranchid(userSupplierbranchid);
            tAgentBankcardReview.setApplicantId(UtilsUser.getUserId());
            tAgentBankcardReview.setApplicantName(sysUser.getUserName());
        }
        List<BankcardReview> list = tAgentBankcardReviewService.selectBankcardReviewList(tAgentBankcardReview);
        return getDataTable(list);
    }

    /**
     * 导出代理银行卡审核列表
     */
    @Log(title = "导出代理银行卡审核", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.BANK_CARD_REVIEW))
    @PostMapping("/export")
    @ResponseBody
    @RequiresPermissions("bankcard:bankcardReview:export")
    public AjaxResult export(BankcardReview tAgentBankcardReview)
    {
        List<BankcardReview> list = tAgentBankcardReviewService.selectBankcardReviewList(tAgentBankcardReview);
        ExcelUtil<BankcardReview> util = new ExcelUtil<BankcardReview>(BankcardReview.class);
        return util.exportExcel(list, "BankcardReview");
    }

    /**
     * 查看代理银行卡审核
     */
    @GetMapping("/proview/{id}")
    @RequiresPermissions("bankcard:bankcardReview:list")
    public String proview(@PathVariable("id") Long id, ModelMap mmap)
    {
        BankcardReview tAgentBankcardReview = tAgentBankcardReviewService.selectBankcardReviewById(id);
        mmap.put("tAgentBankcardReview", tAgentBankcardReview);
        return prefix + "/proview";
    }


    /**
     * 删除代理银行卡审核
     */
    @Log(title = "删除代理银行卡审核", businessType = BusinessType.DELETE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.BANK_CARD_REVIEW))
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("bankcard:bankcardReview:list")
    public AjaxResult remove(String ids)
    {
        return toAjax(tAgentBankcardReviewService.deleteBankcardReviewByIds(ids));
    }
}
