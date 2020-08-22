package com.pay.typay.biz.orders.controller;

import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.biz.orders.domain.InternalTrans;
import com.pay.typay.biz.orders.domain.Userwithdrew;
import com.pay.typay.biz.orders.service.IUserwithdrewService;
import com.pay.typay.biz.orders.service.InternalTransService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.Translate;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 用户提款订单Controller
 * 
 * @author warren
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/orders/transfer")
public class TransferController extends BaseController
{
    private String prefix = "orders/transfer";

    @Autowired
    private IUserwithdrewService agentwithdraworderService;

    @Autowired
    private InternalTransService internalTransService;

    @RequiresPermissions("orders:transfer:view")
    @GetMapping()
    public String transfer()
    {
        return prefix + "/transfer";
    }

    /**
     * 查询用户提款订单列表
     */
    @RequiresPermissions("orders:transfer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(InternalTrans internalTrans)
    {
        startPage();
//        internalTrans.setOrdertype(1);
        internalTrans.setSupplierBranchID(ShiroUtils.getSupplierbranchid());
        Map<String,Object> params = internalTrans.getParams();
        if (params.isEmpty()){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }else if(ObjectUtils.isEmpty(params.get("beginCreatetime"))){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
        }else if(ObjectUtils.isEmpty(params.get("endCreatetime"))){
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }
        List<InternalTrans> list = internalTransService.selectInternalTransList(internalTrans);
        return getDataTable(list);
    }

    /**
     * 导出用户提款订单列表
     */
    @RequiresPermissions("orders:transfer:export")
    @Log(title = "导出中转订单", businessType = BusinessType.EXPORT,
            translate = @Translate(filed = "orderStatus",data = TranslateKeyConstant.OrderStatus),
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.Order_Key))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InternalTrans internalTrans)
    {
        internalTrans.setSupplierBranchID(ShiroUtils.getSupplierbranchid());
        Map<String,Object> params = internalTrans.getParams();
        if (params.isEmpty()){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }else if(ObjectUtils.isEmpty(params.get("beginCreatetime"))){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
        }else if(ObjectUtils.isEmpty(params.get("endCreatetime"))){
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }
        List<InternalTrans> list = internalTransService.selectInternalTransList(internalTrans);
        ExcelUtil<InternalTrans> util = new ExcelUtil<InternalTrans>(InternalTrans.class);
        return util.exportExcel(list, "userwithdrew", "提款订单" + DateUtils.getDate());
    }

    /**
     * 查看用户提款订单
     */
    @GetMapping("/proview/{orderindex}")
    @RequiresPermissions("orders:transfer:list")
    public String proview(@PathVariable("orderindex") Long orderindex, ModelMap mmap)
    {
        Userwithdrew agentwithdraworder = agentwithdraworderService.selectUserwithdrewById(orderindex);
        mmap.put("agentwithdraworder", agentwithdraworder);
        return prefix + "/proview";
    }

}
