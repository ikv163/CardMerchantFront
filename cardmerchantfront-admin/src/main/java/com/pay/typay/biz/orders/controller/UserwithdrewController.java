package com.pay.typay.biz.orders.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.pay.typay.biz.bankcard.domain.Statementcenter;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.biz.orders.domain.Userwithdrew;
import com.pay.typay.biz.orders.service.IUserwithdrewService;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.TySurppilerbranchService;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.common.core.page.TableDataInfo;

/**
 * 用户提款订单Controller
 * 
 * @author warren
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/orders/userwithdrew")
public class UserwithdrewController extends BaseController
{
    private String prefix = "orders/userwithdrew";

    @Autowired
    private IUserwithdrewService agentwithdraworderService;

    @Autowired
    private TySurppilerbranchService surppilerbranchService;

    @RequiresPermissions("orders:userwithdrew:view")
    @GetMapping()
    public String userwithdrew()
    {
        return prefix + "/userwithdrew";
    }

    /**
     * 查询用户提款订单列表
     */
    @RequiresPermissions("orders:userwithdrew:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Userwithdrew agentwithdraworder)
    {
        startPage();
        agentwithdraworder.setOrdertype(0);
        agentwithdraworder.setCreateby(UtilsUser.getUserId());
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            String supplierBranchIdGroup = surppilerbranchService.selectTSurppilerbranchByParentId(ShiroUtils.getSupplierbranchid());
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            agentwithdraworder.setSupplierBranchIdGroupList(sList);
            agentwithdraworder.setCreateby(Long.parseLong("0"));
        } else {
            agentwithdraworder.setCreateby(UtilsUser.getUserId());
        }
        List<Userwithdrew> list = agentwithdraworderService.selectUserwithdrewList(agentwithdraworder);
        return getDataTable(list);
    }

    /**
     * 导出用户提款订单列表
     */
    @RequiresPermissions("orders:userwithdrew:export")
    @Log(title = "导出用户提款订单", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_WITHDRAW_ORDER))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Userwithdrew agentwithdraworder)
    {
        //提款ordertype=0为商户订单
        agentwithdraworder.setOrdertype(0);
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
        List<Userwithdrew> list = agentwithdraworderService.selectUserwithdrewList(agentwithdraworder);
        ExcelUtil<Userwithdrew> util = new ExcelUtil<Userwithdrew>(Userwithdrew.class);
        return util.exportExcel(list, "userwithdrew", "提款订单" + DateUtils.getDate());
    }

    /**
     * 查看用户提款订单
     */
    @GetMapping("/proview/{orderindex}")
    @RequiresPermissions("orders:userwithdrew:list")
    public String proview(@PathVariable("orderindex") Long orderindex, ModelMap mmap)
    {
        Userwithdrew agentwithdraworder = agentwithdraworderService.selectUserwithdrewById(orderindex);
        mmap.put("agentwithdraworder", agentwithdraworder);
        return prefix + "/proview";
    }

    /**
     * 统计申请金额，提款金额和成功率数据
     */
    @GetMapping("/getSummary")
    @ResponseBody
    @RequiresPermissions("orders:userwithdrew:list")
    public Userwithdrew getSummary(Userwithdrew agentwithdraworder) {
        startPage();
        agentwithdraworder.setOrdertype(0);
        agentwithdraworder.setCreateby(UtilsUser.getUserId());
        //获取统计数据（申请金额和提款金额）
        Userwithdrew total = agentwithdraworderService.getSummary(agentwithdraworder);
        //获取成功的提款订单数
        Userwithdrew successWithdraw = agentwithdraworderService.getSuccessTotal(agentwithdraworder);
        //获取全部提款订单数
        Userwithdrew allWithdraw = agentwithdraworderService.getAllTotal(agentwithdraworder);
        BigDecimal successTotal = new BigDecimal(successWithdraw.getOrderindex());
        BigDecimal allTotal = new BigDecimal(allWithdraw.getOrderindex());
        //默认成功率为0.00
        double ratio = 0.00;
        //如果分母不为0，成功率 = 成功提款订单数*100 / 总提款订单数（四舍五入，保留两位小数）
        if (!allTotal.equals(BigDecimal.ZERO)) {
            ratio = successTotal.multiply(new BigDecimal("100")).divide(allTotal,2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        //如果数据不为空，设置成功率
        if (!ObjectUtils.isEmpty(total)) {
            total.setRatio(ratio);
        }
        return total;
    }

}
