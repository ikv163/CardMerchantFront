package com.pay.typay.biz.channel;

import com.pay.typay.biz.domain.Paychannel;
import com.pay.typay.biz.messages.ConstantsSelectUI;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.biz.service.IPayChannelService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.Translate;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 支付渠道Controller
 *
 * @author Warren
 * @date 2020-01-18
 */
@Slf4j
@Controller
@RequestMapping("/channel/paymentchannel")
public class PaymentchannelController extends BaseController {
    private String prefix = "channel/paymentchannel";

    @Autowired
    private IPayChannelService paychannelService;

    @RequiresPermissions("channel:paymentchannel:view")
    @GetMapping()
    public String paychannel() {
        return prefix + "/paymentchannel";
    }

    /**
     * 查询支付渠道列表
     */
    @RequiresPermissions("channel:paymentchannel:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Paychannel paychannel) {
        paychannel.setSurplierType(1);
        startPage();
        List<Paychannel> list = paychannelService.selectBankPaychannelList(paychannel);
        return getDataTable(list);
    }

    /**
     * 导出支付渠道列表
     */
    @RequiresPermissions("channel:paymentchannel:export")
    @Log(title = "导出支付渠道", businessType = BusinessType.EXPORT,
            translate = { @Translate(filed = "status",data = TranslateKeyConstant.PaymentPool_PayChannelStatus)},
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.Payment_Channel))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Paychannel paychannel) {
        paychannel.setSurplierType(1);
        List<Paychannel> list = paychannelService.selectBankPaychannelList(paychannel);
        ExcelUtil<Paychannel> util = new ExcelUtil<Paychannel>(Paychannel.class);
        return util.exportExcel(list, "paychannel");
    }

    /**
     * 新增支付渠道
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存支付渠道
     */
//    @RequiresPermissions("channel:paymentchannel:add")
    @Log(title = "新增支付渠道", businessType = BusinessType.INSERT,
            translate = { @Translate(filed = "status",data = TranslateKeyConstant.PaymentPool_PayChannelStatus)},
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.Payment_Channel))
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Paychannel paychannel) {
        return toAjax(paychannelService.insertPaychannel(paychannel));
    }

    /**
     * 修改保存支付渠道
     */
    //@RequiresPermissions("config:paymentchannelthirdparty:edit")
    @Log(title = "修改支付渠道", businessType = BusinessType.UPDATE,
            translate = { @Translate(filed = "status",data = TranslateKeyConstant.PaymentPool_PayChannelStatus)},
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.Payment_Channel))
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(String idsStr, Long status) {
        List ids = Arrays.asList(Optional.ofNullable(idsStr).orElse("").split(","));
        List<Paychannel> paychannels = paychannelService.selectPayChannelByIds(ids, 2L);
        List<Paychannel> result = paychannels.stream().filter(o -> !status.equals(o.getStatus())).collect(Collectors.toList());
        result.forEach(paychannel -> paychannel.setStatus(status));
        return result.isEmpty() ? toAjax(0) : toAjax(paychannelService.updatePaychannels(result));
    }

    /**
     * 修改保存支付渠道
     */
    //@RequiresPermissions("config:paymentchannelthirdparty:edit")
    @Log(title = "修改支付渠道", businessType = BusinessType.UPDATE,
            translate = { @Translate(filed = "status",data = TranslateKeyConstant.PaymentPool_PayChannelStatus)},
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.Payment_Channel))
    @PostMapping("/editSave")
    @ResponseBody
    public AjaxResult edit(Paychannel paychannel) {
        return toAjax(paychannelService.updatePaychannel(paychannel));
    }

    /**
     * 修改支付渠道
     */
    @GetMapping("/edit/{paychannelid}")
    public String edit(@PathVariable("paychannelid") Long paychannelid, ModelMap mmap) {
        Paychannel paychannel = paychannelService.selectPaychannelById(paychannelid, 3L);
        mmap.put("paychannel", paychannel);
        return prefix + "/edit";
    }

    /**
     * 查看支付渠道
     */
    @GetMapping("/proview/{paychannelid}")
    public String proview(@PathVariable("paychannelid") Long paychannelid, ModelMap mmap) {
        Paychannel paychannel = paychannelService.selectPaychannelById(paychannelid, 2L);
        mmap.put("paychannel", paychannel);
        return prefix + "/proview";
    }

}
