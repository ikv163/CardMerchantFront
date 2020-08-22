package com.pay.typay.biz.orders.controller;

import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.biz.orders.domain.Userecharge;
import com.pay.typay.biz.orders.service.IUserechargeService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.TySurppilerbranchService;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 用户充值订单Controller
 * 
 * @author warren
 * @date 2020-05-18
 */
@Controller
@RequestMapping("/orders/userecharge")
public class UserechargeController extends BaseController
{
    private String prefix = "orders/userecharge";

    @Autowired
    private IUserechargeService agentdepositorderService;

    @Autowired
    private TySurppilerbranchService surppilerbranchService;

    @RequiresPermissions("orders:userecharge:view")
    @GetMapping()
    public String userecharge()
    {
        return prefix + "/userecharge";
    }

    /**
     * 查询用户充值订单列表
     */
    @RequiresPermissions("orders:userecharge:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Userecharge agentdepositorder)
    {
        startPage();
        //卡分销订单支付方式只有网银支付
       // agentdepositorder.setPaytype(Long.parseLong("1"));
//        agentdepositorder.setSupplierbranchid(ShiroUtils.getSupplierbranchid());

        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
//            String supplierBranchIdGroup = surppilerbranchService.selectTSurppilerbranchByParentId(ShiroUtils.getSupplierbranchid());
//            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
//            agentdepositorder.setSupplierBranchIdGroupList(sList);
            agentdepositorder.setSupplierbranchid(sysUser.getSupplierbranchid());
            agentdepositorder.setCreateby(Long.parseLong("0"));
        } else {
            agentdepositorder.setCreateby(UtilsUser.getUserId());
        }


        List<Userecharge> list = agentdepositorderService.selectUserechargeList(agentdepositorder);
        return getDataTable(list);
    }

    /**
     * 导出用户充值订单列表
     */
    @RequiresPermissions("orders:userecharge:export")
    @Log(title = "导出用户充值订单", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_DEPOSIT_ORDER))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Userecharge agentdepositorder)
    {
//        agentdepositorder.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            String supplierBranchIdGroup = surppilerbranchService.selectTSurppilerbranchByParentId(ShiroUtils.getSupplierbranchid());
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            agentdepositorder.setSupplierBranchIdGroupList(sList);
            agentdepositorder.setCreateby(Long.parseLong("0"));
        } else {
            agentdepositorder.setCreateby(UtilsUser.getUserId());
        }
        List<Userecharge> list = agentdepositorderService.selectUserechargeList(agentdepositorder);
        ExcelUtil<Userecharge> util = new ExcelUtil<Userecharge>(Userecharge.class);
        return util.exportExcel(list, "userecharge", "充值订单" + DateUtils.getDate());
    }

    /**
     * 查看用户充值订单
     */
    @GetMapping("/proview/{orderindex}")
    @RequiresPermissions("orders:userecharge:list")
    public String proview(@PathVariable("orderindex") Long orderindex, ModelMap mmap)
    {
        Userecharge agentdepositorder = agentdepositorderService.selectUserechargeById(orderindex);
        mmap.put("agentdepositorder", agentdepositorder);
        return prefix + "/proview";
    }

    /**
     * 统计申请金额，充值金额和成功率数据
     * @return
     */
    @GetMapping("/getSummary")
    @ResponseBody
    @RequiresPermissions("orders:userecharge:list")
    public Userecharge getSummary(Userecharge agentdepositorder) {
        startPage();
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
            List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
            agentdepositorder.setSupplierBranchIdGroupList(sList);
            agentdepositorder.setCreateby(Long.parseLong("0"));
        } else {
            agentdepositorder.setCreateby(UtilsUser.getUserId());
        }
        //获取统计数据（申请金额和充值金额）
        Userecharge total = agentdepositorderService.getSummary(agentdepositorder);
        //获取成功的充值订单数
        Userecharge successDeposit = agentdepositorderService.getSuccessTotal(agentdepositorder);
        //获取全部充值订单数
        Userecharge allDeposit = agentdepositorderService.getAllTotal(agentdepositorder);

        BigDecimal successTotal = new BigDecimal(successDeposit.getOrderindex());
        BigDecimal allTotal = new BigDecimal(allDeposit.getOrderindex());
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
