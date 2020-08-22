package com.pay.typay.biz.agent.controller;

import com.github.pagehelper.PageInfo;
import com.pay.typay.biz.agent.domain.AgentBankcard;
import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.domain.Agentwithdraworder;
import com.pay.typay.biz.agent.service.IAgentBankcardService;
import com.pay.typay.biz.agent.service.IAgentcenterService;
import com.pay.typay.biz.agent.service.IAgentwithdraworderService;
import com.pay.typay.biz.dict.GoogleCodeService;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.mango.MangoUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.common.utils.telegram.TelegramUtils;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 卡商提款订单Controller
 * 
 * @author oswald
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/agent/applywithdrew")
public class AgentwithdraworderController extends BaseController
{
    private String prefix = "agent/applywithdrew";

    @Autowired
    private IAgentwithdraworderService tAgentwithdraworderService;

    @Autowired
    private IAgentcenterService tBankcardAgentService;


    @Value("${telegram.chatId}")
    private String telegarmChatId;

    @Value("${telegram.token}")
    private String telegarmToken;

    @Autowired
    private MangoUtils mangoUtils;

    @Value("${mango.userId}")
    private String userId;

    @Value("${mango.token}")
    private String mangoToken;

    @Value("${mango.roomname}")
    private String roomName;




    @Autowired
    private
    GoogleCodeService googleCodeService;

    @RequiresPermissions("agent:applywithdrew:view")
    @GetMapping()
    public String agentwithdraworder()
    {
        return prefix + "/applywithdrew";
    }

    /**
     * 查询卡商提款订单列表
     */
    @RequiresPermissions("agent:applywithdrew:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Agentwithdraworder tAgentwithdraworder)
    {
        List<Agentwithdraworder> list1= tAgentwithdraworderService.selectAgentwithdraworderList(tAgentwithdraworder);
        BigDecimal sum = list1.stream().map(x ->new BigDecimal(x.getPayamount().toString())).reduce(BigDecimal.ZERO,BigDecimal::add);
        startPage();
        List<Agentwithdraworder> list = tAgentwithdraworderService.selectAgentwithdraworderList(tAgentwithdraworder);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setSumBalance(sum);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 导出卡商提款订单列表
     */
    @RequiresPermissions("agent:applywithdrew:export")
    @Log(title = "导出卡商提款订单", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_WITHDRAW_ORDER))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Agentwithdraworder tAgentwithdraworder)
    {
        List<Agentwithdraworder> list = tAgentwithdraworderService.selectAgentwithdraworderList(tAgentwithdraworder);
        ExcelUtil<Agentwithdraworder> util = new ExcelUtil<Agentwithdraworder>(Agentwithdraworder.class);
        return util.exportExcel(list, "agentwithdraworder");
    }

    /**
     * 新增卡商提款订单
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        //获取卡商的可提款金额
        //BigDecimal withdrawOkAmount = iAgentBankcardService.calcWithdrawOkAmount();
        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(Long.parseLong(ShiroUtils.getAgentId()));
        BigDecimal withdrawOkAmount = new BigDecimal(tBankcardAgent.getAvailableBalance().toString());
        mmap.put("withdrawOkAmount", withdrawOkAmount);
        return prefix + "/add";
    }

    /**
     * 新增保存卡商提款订单
     */
    @RequiresPermissions("agent:applywithdrew:add")
    @Log(title = "新增卡商提款订单", businessType = BusinessType.INSERT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_WITHDRAW_ORDER))
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Agentwithdraworder tAgentwithdraworder)
    {
        //验证谷歌验证码
        try {
            googleCodeService.verifyGooglecode(tAgentwithdraworder.getGooglecode());
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        //获取卡商的可提款金额
        //BigDecimal withdrawOkAmount = iAgentBankcardService.calcWithdrawOkAmount();
        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(Long.parseLong(ShiroUtils.getAgentId()));
        BigDecimal withdrawOkAmount = new BigDecimal(tBankcardAgent.getAvailableBalance().toString());
        BigDecimal payAmount = new BigDecimal( tAgentwithdraworder.getPayamount().toString());
        if (withdrawOkAmount.compareTo(payAmount) < 0) {
            return AjaxResult.error("可提款金额不足");
        }

        if(tBankcardAgent.getPayCode().isEmpty()){
            return AjaxResult.error("请先设置支付密码");
        }


        //验证支付密码,如果输入的密码不正确，抛出异常提示：支付密码不正确
        if(!PHPpassword.PHPpasswordVerify(tAgentwithdraworder.getPassword(),tBankcardAgent.getPayCode())) {
            return AjaxResult.error("资金密码不正确");
        }
        //减少可用余额
        tBankcardAgent.setAvailableBalance(tBankcardAgent.getAvailableBalance() - tAgentwithdraworder.getPayamount());
        //增加冻结金额
        tBankcardAgent.setFronzenBalance(tBankcardAgent.getFronzenBalance() + tAgentwithdraworder.getPayamount());

        tAgentwithdraworder.setAgent_id(Long.parseLong(ShiroUtils.getAgentId()));
        tAgentwithdraworder.setAgent_name((ShiroUtils.getLoginName()));
        tBankcardAgentService.updateAgentcenter(tBankcardAgent);
         int count = tAgentwithdraworderService.insertAgentwithdraworder(tAgentwithdraworder);
         if(count > 0) {
             StringBuilder stringBuilder = new StringBuilder();
             stringBuilder.append("\n");
             stringBuilder.append(":✅代理级别:" + tBankcardAgent.getAgentLevel() + "级别✅✅✅✅✅\n");
             stringBuilder.append(":✅卡商名称:" + tBankcardAgent.getAgentName() +"\n");
             stringBuilder.append("✅代理账号:" + tBankcardAgent.getAgentCode() + "\n");
             stringBuilder.append("✅提款单号:" + tAgentwithdraworder.getOrderid() + "\n");
             stringBuilder.append("✅申请提款:" + tAgentwithdraworder.getPayamount() + "\n");
             mangoUtils.sendMessage(userId,stringBuilder.toString(),roomName,mangoToken);
         }
        return toAjax(count);
    }

    /**
     * 修改保存卡商提款订单
     */
    @RequiresPermissions("agent:applywithdrew:edit")
    @Log(title = "修改卡商提款订单", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_WITHDRAW_ORDER))
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Agentwithdraworder tAgentwithdraworder)
    {
        return toAjax(tAgentwithdraworderService.updateAgentwithdraworder(tAgentwithdraworder));
    }

    /**
     * 修改保存卡商提款订单
     */
    @RequiresPermissions("agent:applywithdrew:edit")
    @Log(title = "代理取消提款订单", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_WITHDRAW_ORDER))
    @PostMapping("/cancelOrder")
    @ResponseBody
    public AjaxResult cancelOrder(Agentwithdraworder tAgentwithdraworder)
    {
        Agentwithdraworder current = tAgentwithdraworderService.selectAgentwithdraworderById(tAgentwithdraworder.getOrderindex());
        if (current.getOrderstatus() != 0){
            return AjaxResult.error("订单已处理，请勿重复处理.");
        }
        return toAjax(tAgentwithdraworderService.cancelOrder(tAgentwithdraworder));
    }

    /**
     * 修改卡商提款订单
     */
    @GetMapping("/edit/{orderindex}")
    @RequiresPermissions("agent:applywithdrew:list")
    public String edit(@PathVariable("orderindex") Long orderindex, ModelMap mmap)
    {
        Agentwithdraworder tAgentwithdraworder = tAgentwithdraworderService.selectAgentwithdraworderById(orderindex);
        mmap.put("tAgentwithdraworder", tAgentwithdraworder);
        return prefix + "/edit";
    }

    /**
     * 查看卡商提款订单
     */
    @GetMapping("/proview/{orderindex}")
    @RequiresPermissions("agent:applywithdrew:list")
    public String proview(@PathVariable("orderindex") Long orderindex, ModelMap mmap)
    {
        Agentwithdraworder tAgentwithdraworder = tAgentwithdraworderService.selectAgentwithdraworderById(orderindex);
        mmap.put("tAgentwithdraworder", tAgentwithdraworder);
        return prefix + "/proview";
    }



}
