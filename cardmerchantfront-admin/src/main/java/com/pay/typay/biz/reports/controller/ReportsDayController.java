package com.pay.typay.biz.reports.controller;

import com.pay.typay.biz.agent.domain.AgentBankcard;
import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.domain.Agentdepositorder;
import com.pay.typay.biz.agent.domain.Agentwithdraworder;
import com.pay.typay.biz.agent.service.IAgentcenterService;
import com.pay.typay.biz.agent.service.IAgentdepositorderService;
import com.pay.typay.biz.agent.service.IAgentwithdraworderService;
import com.pay.typay.biz.orders.domain.Userecharge;
import com.pay.typay.biz.orders.domain.Userwithdrew;
import com.pay.typay.biz.orders.service.IUserechargeService;
import com.pay.typay.biz.orders.service.IUserwithdrewService;
import com.pay.typay.biz.reports.domain.AgentTotal;
import com.pay.typay.biz.reports.domain.TransDetails;
import com.pay.typay.biz.reports.service.IReportsDayService;
import com.pay.typay.biz.reports.vo.ReportsDayVO;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.domain.SubtotalEntity;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysDictData;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysDictTypeService;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

/**
 * 银行卡账变Controller
 *
 * @author js-bucky
 * @date 2020-01-13
 */
@Controller
@RequestMapping("/reports/reportdalily")
public class ReportsDayController extends BaseController {
    private String prefix = "reports/reportdalily";

    @Autowired
    private IReportsDayService reportsDayService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private IUserechargeService agentdepositorderService;

    @Autowired
    private IAgentdepositorderService depositorderService;

    @Autowired
    private IUserwithdrewService agentwithdraworderService;

    @Autowired
    private IAgentcenterService tBankcardAgentService;

    @Autowired
    private IAgentwithdraworderService tAgentwithdraworderService;

    @RequiresPermissions("reports:reportdalily:view")
    @GetMapping()
    public String reportdalily() {
        return prefix + "/reportdalily";
    }

    /**
     * 查询银行卡日交易报表列表
     */
    @RequiresPermissions("reports:reportdalily:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReportsDayVO reportsDayVO) {
        Map<String,Object> params = reportsDayVO.getParams();
        params.put("startTime", LocalDate.now() + " 00:00:00");
        params.put("endTime", LocalDate.now() + " 23:59:59");
        List<ReportsDayVO> total = reportsDayService.selectAgentReportsDayList(reportsDayVO);
        if(total.isEmpty()){
            return getDataTable(total);
        }
        total.stream().forEach(v -> {
            if(StringUtils.isEmpty(v.getId())){
                v.setWithdrawAmount(BigDecimal.ZERO);
            }else {
                reportsDayVO.setAgentId(v.getId());
                v.setWithdrawAmount(reportsDayService.getAgentWithdraw(reportsDayVO));
                if(v.getWithdrawAmount() == null){
                    v.setWithdrawAmount(BigDecimal.ZERO);
                }
            }
        });
        SubtotalEntity subtotal = new SubtotalEntity();
        //代理个数
        subtotal.setAgentNum(total.size());
        BigDecimal sum  = total.stream().map(x -> new BigDecimal(x.getSuccessRate())).reduce(BigDecimal.ZERO,BigDecimal::add);
        //平均成功率
        subtotal.setAvgSuccessRate(sum.divide(new BigDecimal(total.size())).doubleValue());
        //交易笔数
        subtotal.setTransNum(total.stream().mapToInt(ReportsDayVO::getTotalCount).sum());
        //账户金额
        subtotal.setBalance(total.stream().map(x -> x.getBalance()).reduce(BigDecimal.ZERO,BigDecimal::add));
        //利润
        subtotal.setProfit(total.stream().map(x -> x.getProfit_balance()).reduce(BigDecimal.ZERO,BigDecimal::add));
        //已提金额
        subtotal.setWithdrawAmount(total.stream().map(x -> x.getWithdrawAmount()).reduce(BigDecimal.ZERO,BigDecimal::add));
        startPage();
        List<ReportsDayVO> list = reportsDayService.selectAgentReportsDayList(reportsDayVO);
        list.stream().forEach(v -> {
            if(StringUtils.isEmpty(v.getId())){
                v.setWithdrawAmount(BigDecimal.ZERO);
            }else {
                reportsDayVO.setAgentId(v.getId());
                BigDecimal withdrawAmount = reportsDayService.getAgentWithdraw(reportsDayVO);
                v.setWithdrawAmount(withdrawAmount == null ? BigDecimal.ZERO : withdrawAmount);
            }
        });
        SubtotalEntity totalEntity = new SubtotalEntity();
        //代理个数
        totalEntity.setAgentNum(list.size());
        BigDecimal sumTotal  = list.stream().map(x -> new BigDecimal(x.getSuccessRate())).reduce(BigDecimal.ZERO,BigDecimal::add);
        //平均成功率
        totalEntity.setAvgSuccessRate(sumTotal.divide(new BigDecimal(list.size())).doubleValue());
        //交易笔数
        totalEntity.setTransNum(list.stream().mapToInt(ReportsDayVO::getTotalCount).sum());
        //账户金额
        totalEntity.setBalance(list.stream().map(x -> x.getBalance()).reduce(BigDecimal.ZERO,BigDecimal::add));
        //利润
        totalEntity.setProfit(list.stream().map(x -> x.getProfit_balance()).reduce(BigDecimal.ZERO,BigDecimal::add));
        //已提金额
        totalEntity.setWithdrawAmount(list.stream().map(x -> x.getWithdrawAmount()).reduce(BigDecimal.ZERO,BigDecimal::add));
        return getDataTable(list,totalEntity,subtotal);
    }

    @PostMapping("/profilesinfo")
    @ResponseBody
    @RequiresPermissions("reports:paymentstatement:list")
    public TableDataInfo profilesinfo(ReportsDayVO reportsDayVO) {
        reportsDayVO.setParams(new HashMap<>());
        List<ReportsDayVO> list = reportsDayService.selectAgentReportsDayList(reportsDayVO);
        list.stream().forEach(v -> v.setWithdrawAmount(reportsDayService.getAgentWithdraw(reportsDayVO)));
        return getDataTable(list);
    }

    /**
     * 导出银行卡日交易报表列表
     */
    @RequiresPermissions("reports:reportdalily:export")
    @Log(title = "每日汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReportsDayVO reportsDay) {
        List<SysDictData> dictDataList = dictTypeService.getDictDataByType("agent_level");
        List<ReportsDayVO> list = reportsDayService.selectReportsDayList(reportsDay, dictDataList);
        ExcelUtil<ReportsDayVO> util = new ExcelUtil<ReportsDayVO>(ReportsDayVO.class);
        return util.exportExcel(list, "dalily_report", "每日汇总" + DateUtils.getDate());
    }



    /**
     * 统计每天的充值金额和提款金额
     */
    @PostMapping("/total")
    @ResponseBody
    @RequiresPermissions("reports:paymentstatement:list")
    public TableDataInfo total(Userecharge agentdepositorder,Userwithdrew agentwithdraworder,String userName,String searchData) {
        //获取充值金额的统计数据
        if (searchData.isEmpty()) {
            searchData = LocalDate.now() + "";
        }
        String startTime = searchData + " 00:00:00";
        String endTime = searchData + " 23:59:59";
        agentdepositorder.setCreateby(UtilsUser.getUserId());
        agentdepositorder.setBeginTime(startTime);
        agentdepositorder.setEndTime(endTime);
        Userecharge deposit = agentdepositorderService.getSummary(agentdepositorder);
        //获取提款金额的统计数据
        agentwithdraworder.setCreateby(UtilsUser.getUserId());
        agentwithdraworder.setBeginTime(startTime);
        agentwithdraworder.setEndTime(endTime);
//        agentwithdraworder.setCreatedtime(startTime);
        Userwithdrew withdraw = agentwithdraworderService.getSummary(agentwithdraworder);

        //构建一个实体，只有今日总额，今日充值金额和今日提款金额
        AgentTotal agentTotal = new AgentTotal();
        List<AgentTotal> list = new ArrayList<>();

        //充值金额默认为0，如果查询当天充值订单不为空，更新充值金额
        BigDecimal depositAmount = new BigDecimal("0");
        if (!ObjectUtils.isEmpty(deposit)) {
            depositAmount = deposit.getPaidamount();
        }
        agentTotal.setDepositAmount(depositAmount);

        //提款金额默认为0，如果查询当天提款订单不为空，更新提款金额
        BigDecimal withdrawAmount = new BigDecimal("0");
        if (!ObjectUtils.isEmpty(withdraw)) {
            withdrawAmount = withdraw.getPaidamount();
        }
        agentTotal.setWithdrawAmount(withdrawAmount);

        //总额 = 充值金额 + 提款金额
        agentTotal.setTotalAmount(depositAmount.add(withdrawAmount).doubleValue());
        list.add(agentTotal);
        return getDataTable(list);
    }

    /**
     * 统计每天的充值和提款详细数据
     */
    @PostMapping("/getWorkTypeSummary")
    @ResponseBody
    @RequiresPermissions("reports:paymentstatement:list")
    public TableDataInfo getWorkTypeSummary(Userecharge agentdepositorder,Userwithdrew agentwithdraworder,String userName,String searchData) {

        if (searchData.isEmpty()) {
            searchData = LocalDate.now() + "";
        }
        String startTime = searchData + " 00:00:00";
        String endTime = searchData + " 23:59:59";

        //获取充值金额的统计数据
        agentdepositorder.setCreateby(UtilsUser.getUserId());
        agentdepositorder.setBeginTime(startTime);
        agentdepositorder.setEndTime(endTime);
        //获取成功的充值订单数
        Userecharge successDeposit = agentdepositorderService.getSuccessTotal(agentdepositorder);
        //获取全部充值订单数
        Userecharge allDeposit = agentdepositorderService.getAllTotal(agentdepositorder);

        BigDecimal successDepositTotal = new BigDecimal(successDeposit.getOrderindex());
        BigDecimal allDepositTotal = new BigDecimal(allDeposit.getOrderindex());
        //默认成功率为0.00
        double depositRatio = 0.00;
        double avgDepositAmount = 0.00;
        //如果分母不为0，成功率 = 成功提款订单数*100 / 总提款订单数（四舍五入，保留两位小数）
        if (!allDepositTotal.equals(BigDecimal.ZERO)) {
            depositRatio = successDepositTotal.multiply(new BigDecimal("100")).divide(allDepositTotal,2,BigDecimal.ROUND_HALF_UP).doubleValue();
            avgDepositAmount = allDeposit.getPaidamount().divide(allDepositTotal,2,BigDecimal.ROUND_HALF_DOWN ).doubleValue();
        }

        //构建一个实体，只有工作模式，交易笔数，成功笔数，平均金额，交易总额，成功率
        TransDetails depositTrans = new TransDetails();
        TransDetails withdrawTrans = new TransDetails();
        List<TransDetails> list = new ArrayList<>();
        depositTrans.setWorkType("入款");
        depositTrans.setTransCount(allDepositTotal);
        depositTrans.setSuccessTransCount(successDepositTotal);
        depositTrans.setTransAmount(allDeposit.getPaidamount());
        depositTrans.setAvgAmount(avgDepositAmount);
        depositTrans.setSuccessRatio(depositRatio);
        list.add(depositTrans);


        //获取提款金额的统计数据
        agentwithdraworder.setCreateby(UtilsUser.getUserId());
        agentwithdraworder.setBeginTime(startTime);
        agentwithdraworder.setEndTime(endTime);
        //获取成功的提款订单数
        Userwithdrew successWithdraw = agentwithdraworderService.getSuccessTotal(agentwithdraworder);
        //获取全部提款订单数
        Userwithdrew allWithdraw = agentwithdraworderService.getAllTotal(agentwithdraworder);

        BigDecimal successWithdrawTotal = new BigDecimal(successWithdraw.getOrderindex());
        BigDecimal allWithdrawTotal = new BigDecimal(allWithdraw.getOrderindex());
        //默认成功率为0.00
        double withdrawRatio = 0.00;
        double avgWithdrawAmount = 0.00;
        //如果分母不为0，成功率 = 成功提款订单数*100 / 总提款订单数（四舍五入，保留两位小数）
        if (!allWithdrawTotal.equals(BigDecimal.ZERO)) {
            withdrawRatio = successWithdrawTotal.multiply(new BigDecimal("100")).divide(allWithdrawTotal,2,BigDecimal.ROUND_HALF_UP).doubleValue();
            avgWithdrawAmount = allWithdraw.getPaidamount().divide(allWithdrawTotal,2,BigDecimal.ROUND_HALF_DOWN ).doubleValue();
        }

        withdrawTrans.setWorkType("出款");
        withdrawTrans.setTransCount(allWithdrawTotal);
        withdrawTrans.setSuccessTransCount(successWithdrawTotal);
        withdrawTrans.setTransAmount(allWithdraw.getPaidamount());
        withdrawTrans.setAvgAmount(avgWithdrawAmount);
        withdrawTrans.setSuccessRatio(withdrawRatio);
        list.add(withdrawTrans);

        return getDataTable(list);
    }

    /**
     * 统计每天的充值和提款详细数据
     */
    @PostMapping("/getTotalSummary")
    @ResponseBody
    @RequiresPermissions("reports:paymentstatement:list")
    public TableDataInfo getTotalSummary(Userecharge agentdepositorder,Userwithdrew agentwithdraworder,String userName,String searchData) {
        //获取充值金额的统计数据
        if (searchData.isEmpty()) {
            searchData = LocalDate.now() + "";
        }
        String startTime = searchData + " 00:00:00";
        String endTime = searchData + " 23:59:59";
        agentdepositorder.setCreateby(UtilsUser.getUserId());
        agentdepositorder.setBeginTime(startTime);
        agentdepositorder.setEndTime(endTime);
        Userecharge deposit = agentdepositorderService.getSummary(agentdepositorder);
        //获取提款金额的统计数据
        agentwithdraworder.setCreateby(UtilsUser.getUserId());
        agentwithdraworder.setBeginTime(startTime);
        agentwithdraworder.setEndTime(endTime);
//        agentwithdraworder.setCreatedtime(startTime);
        Userwithdrew withdraw = agentwithdraworderService.getSummary(agentwithdraworder);

        //构建一个实体，只有今日总额，今日充值金额和今日提款金额
        AgentTotal agentTotal = new AgentTotal();
        List<AgentTotal> list = new ArrayList<>();

        //充值金额默认为0，如果查询当天充值订单不为空，更新充值金额
        BigDecimal depositAmount = new BigDecimal("0");
        if (!ObjectUtils.isEmpty(deposit)) {
            depositAmount = deposit.getPaidamount();
        }
        agentTotal.setDepositAmount(depositAmount);

        //提款金额默认为0，如果查询当天提款订单不为空，更新提款金额
        BigDecimal withdrawAmount = new BigDecimal("0");
        if (!ObjectUtils.isEmpty(withdraw)) {
            withdrawAmount = withdraw.getPaidamount();
        }
        agentTotal.setWithdrawAmount(withdrawAmount);

        //总额 = 充值金额 + 提款金额
        double totalAmount = depositAmount.add(withdrawAmount).doubleValue();
        agentTotal.setTotalAmount(totalAmount);

        //设置
        agentTotal.setAgentName(UtilsUser.getUserName());


        //获取成功的充值订单数
        Userecharge successDeposit = agentdepositorderService.getSuccessTotal(agentdepositorder);
        BigDecimal successDepositTotal = new BigDecimal(successDeposit.getOrderindex());
        //获取全部充值订单数
        Userecharge allDeposit = agentdepositorderService.getAllTotal(agentdepositorder);
        BigDecimal allDepositTotal = new BigDecimal(allDeposit.getOrderindex());


        //获取成功的提款订单数
        Userwithdrew successWithdraw = agentwithdraworderService.getSuccessTotal(agentwithdraworder);
        //获取全部提款订单数
        Userwithdrew allWithdraw = agentwithdraworderService.getAllTotal(agentwithdraworder);

        BigDecimal successWithdrawTotal = new BigDecimal(successWithdraw.getOrderindex());
        BigDecimal allWithdrawTotal = new BigDecimal(allWithdraw.getOrderindex());

        BigDecimal transCount = new BigDecimal(allDepositTotal.add(allWithdrawTotal).doubleValue());
        BigDecimal successTransCount = new BigDecimal(successDepositTotal.add(successWithdrawTotal).doubleValue());

        agentTotal.setTransCount(transCount);
        agentTotal.setSuccessTransCount(successTransCount);


        //默认成功率为0.00
        double ratio = 0.00;
        //如果分母不为0，成功率 = 成功提款订单数*100 / 总提款订单数（四舍五入，保留两位小数）
        if (!transCount.equals(BigDecimal.ZERO)) {
            ratio = successTransCount.multiply(new BigDecimal("100")).divide(transCount,2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        agentTotal.setSuccessRatio(ratio);



        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {  //如果不是管理员账号
            Agentcenter agentInfo = tBankcardAgentService.selectAgentcenterById(Long.parseLong(ShiroUtils.getAgentId()));
            if (!ObjectUtils.isEmpty(agentInfo)) {
                agentTotal.setAgentRatio(agentInfo.getRatio());
                agentTotal.setBalance(new BigDecimal(agentInfo.getBalance().toString()));
                double profit = new BigDecimal(totalAmount).multiply(new BigDecimal(agentInfo.getRatio().toString())).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_DOWN).doubleValue();

                agentTotal.setProfit(profit);
                agentTotal.setCreditBalance(agentInfo.getCreditBalance());
                agentTotal.setAvailableBalance(agentInfo.getAvailableBalance());

            }
        }





        list.add(agentTotal);



        //获取代理成功提款的金额
        Agentwithdraworder agentwithdraworder1 = tAgentwithdraworderService.getSuccessSummary(ShiroUtils.getAgentId(),startTime,endTime);
        if (!ObjectUtils.isEmpty(agentwithdraworder1)) {
            agentTotal.setAgentWithdrawAmount(new BigDecimal(agentwithdraworder1.getPaidamount()));
        }


        return getDataTable(list);
    }


}
