package com.pay.typay.biz.orders.controller;

import com.pay.typay.biz.agent.domain.Agentcreditorder;
import com.pay.typay.biz.agent.service.IAgentcreditorderService;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.biz.orders.domain.Userwithdrew;
import com.pay.typay.biz.orders.service.IUserwithdrewService;
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
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户提款订单Controller
 * 
 * @author warren
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/orders/exchargeamout")
public class ExchargeamoutController extends BaseController
{
    private String prefix = "orders/exchargeamout";

    @Autowired
    private IAgentcreditorderService agentcreditorderService;

    @RequiresPermissions("orders:exchargeamout:view")
    @GetMapping()
    public String exchargeamout()
    {
        return prefix + "/exchargeamout";
    }

    /**
     * 查询用户提款订单列表
     */
    @RequiresPermissions("orders:exchargeamout:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Agentcreditorder agentwithdraworder)
    {
        startPage();
        //orderType  1为信用订单，2为转额度订单
        agentwithdraworder.setOrderType(2);
        SysUser sysUser = ShiroUtils.getSysUser();
        //如果是管理员账号
        if (sysUser.isAdmin()) {
            String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            agentwithdraworder.setSupplierBranchIdGroupList(sList);
        } else {
            agentwithdraworder.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        }


        Map<String,Object> params = agentwithdraworder.getParams();
        if (params.isEmpty()){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }else if(ObjectUtils.isEmpty(params.get("beginCreatetime"))){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
        }else if(ObjectUtils.isEmpty(params.get("endCreatetime"))){
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }
        List<Agentcreditorder> list = agentcreditorderService.selectAgentcreditorderList(agentwithdraworder);
        return getDataTable(list);
    }

    /**
     * 导出用户提款订单列表
     */
    @RequiresPermissions("orders:exchargeamout:export")
    @Log(title = "导出额度转换订单", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_WITHDRAW_ORDER))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Agentcreditorder agentwithdraworder)
    {
        agentwithdraworder.setOrderType(2);
        agentwithdraworder.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        Map<String,Object> params = agentwithdraworder.getParams();
        if (params.isEmpty()){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }else if(ObjectUtils.isEmpty(params.get("beginCreatetime"))){
            params.put("beginCreatetime", DateUtils.getDateAgo("mon",3) + " 00:00:00");
        }else if(ObjectUtils.isEmpty(params.get("endCreatetime"))){
            params.put("endCreatetime", LocalDate.now() + " 23:59:59");
        }
        List<Agentcreditorder> list = agentcreditorderService.selectAgentcreditorderList(agentwithdraworder);
        ExcelUtil<Agentcreditorder> util = new ExcelUtil<Agentcreditorder>(Agentcreditorder.class);
        return util.exportExcel(list, "userwithdrew", "提款订单" + DateUtils.getDate());
    }

    /**
     * 查看用户提款订单
     */
    @GetMapping("/proview/{orderindex}")
    @RequiresPermissions("orders:exchargeamout:list")
    public String proview(@PathVariable("orderindex") Long orderindex, ModelMap mmap)
    {
        Agentcreditorder agentwithdraworder = agentcreditorderService.selectAgentcreditorderById(orderindex);
        mmap.put("agentwithdraworder", agentwithdraworder);
        return prefix + "/proview";
    }

}
