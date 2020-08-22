package com.pay.typay.biz.agent.controller;

import com.pay.typay.biz.agent.domain.AgentBankcard;
import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.domain.Agentcreditorder;
import com.pay.typay.biz.agent.domain.Agentdepositorder;
import com.pay.typay.biz.agent.service.IAgentBankcardService;
import com.pay.typay.biz.agent.service.IAgentcenterService;
import com.pay.typay.biz.agent.service.IAgentcreditorderService;
import com.pay.typay.biz.agent.service.IAgentdepositorderService;
import com.pay.typay.biz.dict.GoogleCodeService;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.mango.MangoUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.common.utils.telegram.TelegramUtils;
import com.pay.typay.framework.util.GoogleAuthenticator;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysUserService;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 代理银行卡Controller
 *
 * @author oswald
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/agent/profiles")
public class ProfilesController extends BaseController {
    private String prefix = "agent/profiles" ;

    @Autowired
    private IAgentBankcardService tAgentbankcardService;

    @Autowired
    private GoogleCodeService googleCodeService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private MangoUtils mangoUtils;

    @Value("${mango.userId}")
    private String userId;

    @Value("${mango.token}")
    private String mangoToken;

    @Value("${mango.roomname}")
    private String roomName;

    @RequiresPermissions("agent:profiles:view")
    @GetMapping()
    public String AgentBankcard() {
        return prefix + "/profiles" ;
    }


    @Value("${telegram.chatId}")
    private String telegarmChatId;

    @Value("${telegram.token}")
    private String telegarmToken;


    /**
     * 查询代理银行卡列表
     */
    @RequiresPermissions("agent:profiles:list")
    @PostMapping("/agentbankcard/list")
    @ResponseBody
    public TableDataInfo agentbankcardlist(AgentBankcard tAgentbankcard) {
        startPage();
        List<AgentBankcard> list = tAgentbankcardService.selectAgentBankcardList(tAgentbankcard);
        return getDataTable(list);
    }
    @Autowired
    private IAgentcenterService tBankcardAgentService;

    /**
     * 查询卡商代理列表
     */
    @RequiresPermissions("agent:profiles:list")
    @PostMapping("/profilesinfo")
    @ResponseBody
    public TableDataInfo profilesinfo(Agentcenter tBankcardAgent)
    {
        startPage();
        List<Agentcenter> list = tBankcardAgentService.profilesinfo(tBankcardAgent);
        return getDataTable(list);
    }

    @RequiresPermissions("agent:profiles:resetPwd")
    @Log(title = "修改密码", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.User_Info_Key))
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/agent-edit";
    }

    @RequiresPermissions("agent:profiles:resetPwd")
    @Log(title = "修改密码", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.User_Info_Key))
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user) {
        SysUser sysOpterUser = userService.selectUserById(user.getUserId());
        String currentLoginName = UtilsUser.getLoginName();
        //如果是管理员,并且,不是自己,就不可以修改
        if (sysOpterUser.isAdmin()) {
            if (!currentLoginName.equals(sysOpterUser.getLoginName())) {
                return error("禁止操作");
            }
        }
        try {
            googleCodeService.verifyGooglecode(user.getVerifycode());
        } catch (Exception e) {
            return error(e.getMessage());
        }

        if(!StringUtils.isEmpty(user.getPassword())) {
            if (!UtilsUser.checkPassWord(user.getPassword())) {
                return error("请重新输入(必须包含8-20位数字与字母，有英文大写)");
            }
        }
        user.setPassword(PHPpassword.PHPpasswordHash(user.getPassword()));
        if (userService.resetUserPwd(user) > 0) {
            if (ShiroUtils.getUserId().equals(user.getUserId())) {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }

    @RequiresPermissions("agent:profiles:sndisplay")
    @GetMapping("/sndisplay/{userId}")
    public String sndisplay(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/googlecode-edit";
    }

    @PostMapping("/resetGoogleCode")
    @RequiresPermissions("agent:profiles:list")
    @Log(title = "重置谷歌秘钥", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.User_Info_Key))
    @ResponseBody
    public Map<String, Object> resetGoogleCode(SysUser user) {
        SysUser sysOpterUser = userService.selectUserById(user.getUserId());
        String currentLoginName = UtilsUser.getLoginName();

        //如果是管理员,并且,不是自己,就不可以修改
        Map<String,Object> modelMap = new HashMap<>();
        if (sysOpterUser.isAdmin()) {
            if (!currentLoginName.equals(sysOpterUser.getLoginName())) {
                modelMap.put("msg","禁止操作");
                modelMap.put("code","500");
                return modelMap;
            }
        }

        try {
            googleCodeService.verifyGooglecode(user.getVerifycode());
        } catch (Exception e) {
            return error(e.getMessage());
        }

        user.setGooglecode(GoogleAuthenticator.generateSecretKey());
        user.setUpdateTime(new Date());

        userService.updateUserGoogleCode(user);
        if (currentLoginName.equals(sysOpterUser.getLoginName())) {
            SysUser sysUser = ShiroUtils.getSysUser();
            sysUser.setGooglecode(user.getGooglecode());
            ShiroUtils.setSysUser(sysUser);
        }
        String googlecode = user.getGooglecode();
        modelMap.put("googlecode",googlecode);
        modelMap.put("msg","修改成功");
        modelMap.put("code","200");
        return modelMap;
    }


    /**
     * 查询代理银行卡列表
     */
    @RequiresPermissions("agent:profiles:list")
    @PostMapping("/agentbankcard/check")
    @ResponseBody
    public TableDataInfo checkagentbankcardlist(AgentBankcard tAgentbankcard) {
        startPage();
        List<AgentBankcard> list = tAgentbankcardService.selectAgentBankcardListAll(tAgentbankcard);
        return getDataTable(list);
    }



    /**
     * 新增代理银行卡
     */
    @GetMapping("/agentbankcard/add")
    public String agentbankcardadd() {
        return prefix + "/agentbankcard-add" ;
    }

    /**
     * 新增保存代理银行卡
     */
    @RequiresPermissions("agent:profiles:add")
    @Log(title = "代理银行卡", businessType = BusinessType.INSERT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_BANK_CARD))
    @PostMapping("/agentbankcard/add")
    @ResponseBody
    public AjaxResult agentbankcardaddSave(AgentBankcard tAgentbankcard) {
        //验证谷歌验证码
        try {
            googleCodeService.verifyGooglecode(tAgentbankcard.getGooglecode());
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return toAjax(tAgentbankcardService.insertAgentBankcard(tAgentbankcard));
    }

    /**
     * 修改保存代理银行卡
     */
    @RequiresPermissions("agent:profiles:edit")
    @Log(title = "修改代理银行卡", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_BANK_CARD))
    @PostMapping("/agentbankcard/edit")
    @ResponseBody
    public AjaxResult agentbankcardeditSave(AgentBankcard tAgentbankcard) {
        return toAjax(tAgentbankcardService.updateAgentBankcard(tAgentbankcard));
    }

    /**
     * 修改代理银行卡
     */
    @GetMapping("/agentbankcard/edit/{bankid}")
    @RequiresPermissions("agent:profiles:list")
    public String agentbankcardedit(@PathVariable("bankid") Long bankid, ModelMap mmap) {
        AgentBankcard tAgentbankcard = tAgentbankcardService.selectAgentBankcardById(bankid);
        mmap.put("tAgentbankcard", tAgentbankcard);
        return prefix + "/agentbankcard-edit" ;
    }

    /**
     * 查看代理银行卡
     */
    @GetMapping("/agentbankcard/proview/{bankid}")
    @RequiresPermissions("agent:profiles:list")
    public String agentbankcardproview(@PathVariable("bankid") Long bankid, ModelMap mmap) {
        AgentBankcard tAgentbankcard = tAgentbankcardService.selectAgentBankcardById(bankid);
        mmap.put("tAgentbankcard", tAgentbankcard);
        return prefix + "/agentbankcard-proview" ;
    }


    /**
     * 删除代理银行卡
     */
    @RequiresPermissions("agent:profiles:remove")
    @Log(title = "删除代理银行卡", businessType = BusinessType.DELETE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_BANK_CARD))
    @PostMapping("/agentbankcard/remove")
    @ResponseBody
    public AjaxResult agentbankcardremove(String ids) {
        return toAjax(tAgentbankcardService.deleteAgentBankcardByIds(ids));
    }


    /*****************************充值订单***********************************************/
    @Autowired
    private IAgentdepositorderService agentdepositorderService;
    /**
     * 查询代理银行卡充值订单列表
     */
    @RequiresPermissions("agent:profiles:list")
    @PostMapping("/agentdepositorder/list")
    @ResponseBody
    public TableDataInfo agentdepositorderlist(Agentdepositorder agentdepositorder) {
        BigDecimal totalAmount;
        BigDecimal currentPageAmount;
        startPage();
        List<Agentdepositorder> list = new ArrayList<>();
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser.isAdmin()) {  //如果是管理员账号
            totalAmount = agentdepositorderService.calcAgentdepositAmount(agentdepositorder);
            list = agentdepositorderService.selectAgentdepositorderList(agentdepositorder);
            //小计
            currentPageAmount = list.stream().map(x -> new BigDecimal(x.getPaidamount().toString())).reduce(BigDecimal.ZERO,BigDecimal::add);
        } else {
            if(ObjectUtils.isEmpty(sysUser.getAgentId())){
                totalAmount = new BigDecimal("0");
                currentPageAmount = new BigDecimal("0");
                list.add(new Agentdepositorder());
            } else {
                //总计
                totalAmount = agentdepositorderService.calcAgentdepositAmount(agentdepositorder);
                list = agentdepositorderService.selectAgentdepositorderList(agentdepositorder);
                //小计
                currentPageAmount = list.stream().map(x -> new BigDecimal(x.getPaidamount().toString())).reduce(BigDecimal.ZERO,BigDecimal::add);
            }
        }
        return getDataTable(list,currentPageAmount,totalAmount);
    }

    /**
     * 导出代理银行卡充值订单列表
     */
    @RequiresPermissions("agent:profiles:export")
    @Log(title = "导出代理银行卡充值订单列表", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_DEPOSIT_ORDER))
    @PostMapping("/agentdepositorder/export")
    @ResponseBody
    public AjaxResult export(Agentdepositorder agentdepositorder) {
        List<Agentdepositorder> list = agentdepositorderService.selectAgentdepositorderList(agentdepositorder);

        ExcelUtil<Agentdepositorder> util = new ExcelUtil<>(Agentdepositorder.class);
        return util.exportExcel(list, "代理银行卡充值订单");
    }

    /**
     * 新增代理银行卡充值订单
     */
    @GetMapping("/agentdepositorder/add")
    public String agentdepositorderadd() {
        return prefix + "/agentdepositorder-add" ;
    }

    /**
     * 新增保存代理银行卡充值订单
     */
    @RequiresPermissions("agent:profiles:add")
    @Log(title = "代理银行卡充值订单", businessType = BusinessType.INSERT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_DEPOSIT_ORDER))
    @PostMapping("/agentdepositorder/add")
    @ResponseBody
    public AjaxResult agentdepositorderaddSave(Agentdepositorder agentdepositorder) {
        return toAjax(agentdepositorderService.insertAgentdepositorder(agentdepositorder));
    }

    /**
     * 修改保存代理银行卡充值订单
     */
    @RequiresPermissions("agent:profiles:edit")
    @Log(title = "代理银行卡充值订单", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_DEPOSIT_ORDER))
    @PostMapping("/agentdepositorder/edit")
    @ResponseBody
    public AjaxResult agentdepositordereditSave(Agentdepositorder agentdepositorder) {
        Agentdepositorder agentdepositorder1 = agentdepositorderService.selectAgentdepositorderById(agentdepositorder.getOrderindex());
        if(agentdepositorder1.getOrderstatus() != 0){
            return AjaxResult.error("订单已处理，请勿重复处理.");
        }
        return toAjax(agentdepositorderService.updateAgentdepositorder(agentdepositorder));
    }

    /**
     * 查看代理银行卡充值订单
     */
    @GetMapping("/agentdepositorder/proview/{orderindex}")
    @RequiresPermissions("agent:profiles:list")
    public String agentdepositorderproview(@PathVariable("orderindex") Long orderindex, ModelMap mmap) {
        Agentdepositorder tAgentdepositorder = agentdepositorderService.selectAgentdepositorderById(orderindex);
        mmap.put("tAgentdepositorder", tAgentdepositorder);
        return prefix + "/agentdepositorder-proview" ;
    }

    /**
     * 额度转换页面
     * @param mmap
     * @return
     */
    @GetMapping("/transmoney")
    @RequiresPermissions("agent:profiles:list")
    public String transmoney(ModelMap mmap) {
        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(Long.parseLong(ShiroUtils.getAgentId()));
        mmap.put("tBankcardAgent", tBankcardAgent);
        return prefix + "/transmoney" ;
    }

    /**
     * 额度转换
     * @param availableBalance
     * @param transType
     * @param transMoney
     * @param payCode
     * @return
     */
    @PostMapping("/transmoney")
    @ResponseBody
    public AjaxResult transmoneysave( double availableBalance,String transType,double transMoney,String payCode,Agentcreditorder agentcreditorder) {
        if (!"1".equals(transType)) {
            return AjaxResult.error("很抱歉，目前只支持转换信用金额！");
        }
        if (transMoney <= 0.00) {
            return AjaxResult.error("转换金额要大于0.00元！");
        }

        if (availableBalance < transMoney) {
            return AjaxResult.error("转换金额不能大于可提金额！");
        }

        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(Long.parseLong(ShiroUtils.getAgentId()));
        //验证支付密码
        if(!PHPpassword.PHPpasswordVerify(payCode,tBankcardAgent.getPayCode())) {
            return AjaxResult.error("资金密码不正确");
        }

        double enableMoney = tBankcardAgent.getAvailableBalance() - transMoney;
        double creditMoney = tBankcardAgent.getCreditBalance() + transMoney;
        tBankcardAgent.setAvailableBalance(enableMoney);
        tBankcardAgent.setCreditBalance(creditMoney);




        return toAjax(tBankcardAgentService.transMoney(tBankcardAgent,agentcreditorder,transMoney));
    }

    /**
     * 修改支付密码的页面
     * @param mmap
     * @return
     */
    @GetMapping("/editPayCodeView")
    @RequiresPermissions("agent:profiles:list")
    public String editPayCodeView(ModelMap mmap) {
        String agentName = ShiroUtils.getLoginName();
        mmap.put("agentName", agentName);
        return prefix + "/editpaycode" ;
    }

    /**
     * 修改支付密码
     * @param googleCode
     * @param payCode
     * @return
     */
    @PostMapping("/editPayCode")
    @ResponseBody
    public AjaxResult editPayCode( String googleCode,String payCode) {
        //验证谷歌验证码
        String agentName = ShiroUtils.getLoginName();
        try {
            googleCodeService.verifyGooglecode(googleCode);
        } catch (Exception e) {
            String sendMsg = "！！！请注意！！！【" + agentName + "】修改支付密码失败，失败原因【"+e.getMessage()+"】！！！";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
            return AjaxResult.error(e.getMessage());
        }

        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentCenterByAgentName(agentName);
        //找不到卡商信息，抛出错误信息
        if (ObjectUtils.isEmpty(tBankcardAgent)) {
            return AjaxResult.error("不是有效的卡商代理");
        }
        //设置密码
        tBankcardAgent.setPayCode(PHPpassword.PHPpasswordHash(payCode));
        String sendMsg = "！！！请注意！！！【" + agentName + "】修改了支付密码！！！";
        mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        return toAjax(tBankcardAgentService.updateAgentcenter(tBankcardAgent));
    }

}
