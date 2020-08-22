package com.pay.typay.biz.agent.controller;

import com.github.pagehelper.PageInfo;
import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.service.IAgentcenterService;
import com.pay.typay.biz.dict.ITDictBankCommonService;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.domain.SubtotalEntity;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.exception.BusinessException;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysDictDataService;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

/**
 * 卡商代理Controller
 *
 * @author oswald
 * @date 2020-05-13
 */
@Controller
@RequestMapping("/agent/agentcenter")
public class AgentcenterController extends BaseController {
    private String prefix = "agent/agentcenter";

    @Autowired
    private IAgentcenterService tBankcardAgentService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ITDictBankCommonService dictBankCommonService;


    @RequiresPermissions("agent:agentcenter:view")
    @GetMapping()
    public String agentcenter() {
        return prefix + "/agentcenter";
    }

    /**
     * 查询卡商代理列表
     */
    @RequiresPermissions("agent:agentcenter:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Agentcenter tBankcardAgent) {

        String supplierBranchIdGroup = "";
        Long userSupplierBranchId = Long.parseLong("1");
        SysUser sysUser = ShiroUtils.getSysUser();
        if(UtilsUser.getUserId() != 1) {  //如果不是admin账号
            if (sysUser.isAdmin()) {  //如果是管理员账号
//               supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
//                List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
//                tBankcardAgent.setSupplierBranchIdGroupList(sList);
            } else {   //如果是普通账号
//                userSupplierBranchId = UtilsUser.getUserSupplierbranchid();
//                tBankcardAgent.setSupplierBranchId(userSupplierBranchId);
                tBankcardAgent.setCreateby(UtilsUser.getLoginName());
            }
        }
        userSupplierBranchId = UtilsUser.getUserSupplierbranchid();
        tBankcardAgent.setSupplierBranchId(userSupplierBranchId);
        List<Agentcenter> list = tBankcardAgentService.selectAgentcenterList(tBankcardAgent);
        //计算总计
        SubtotalEntity  totalEntity  = new SubtotalEntity();
        //可用余额
        totalEntity .setAvailableBalance(list.stream().map(b -> new BigDecimal(b.getAvailableBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //余额
        totalEntity .setBalance(list.stream().map(b -> new BigDecimal(b.getBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));

        //冻结
        totalEntity .setFronzenBalance(list.stream().map(b -> new BigDecimal(b.getFronzenBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //利润
        totalEntity .setProfit(list.stream().map(b -> new BigDecimal(b.getProfitBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //代理个数
        totalEntity.setAgentNum(list.size());
        startPage();
        //计算当前页
        List<Agentcenter> list2 = tBankcardAgentService.selectAgentcenterList(tBankcardAgent);
        //计算总计
        SubtotalEntity  subtotal = new SubtotalEntity();
        //可用余额
        subtotal .setAvailableBalance(list2.stream().map(b -> new BigDecimal(b.getAvailableBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //余额
        subtotal .setBalance(list2.stream().map(b -> new BigDecimal(b.getBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //冻结
        subtotal .setFronzenBalance(list2.stream().map(b -> new BigDecimal(b.getFronzenBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //利润
        subtotal .setProfit(list2.stream().map(b -> new BigDecimal(b.getProfitBalance().toString())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //代理个数
        subtotal.setAgentNum(list2.size());

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list2);
        rspData.setTotal(new PageInfo(list2).getTotal());
        rspData.setSubtotal(subtotal);
        rspData.setTotalEntity(totalEntity);
        return rspData;
    }

    /**
     * 查询卡商代理列表
     */
    @RequiresPermissions("agent:agentcenter:list")
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(Agentcenter tBankcardAgent) {
        List<Agentcenter> list = tBankcardAgentService.checkLoginNameUnique(tBankcardAgent);
        return list.isEmpty() ? "0" : "1";
    }

    /**
     * 导出卡商代理列表
     */
    @RequiresPermissions("agent:agentcenter:export")
    @Log(title = "导出卡商代理", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_BANK_CARD))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Agentcenter tBankcardAgent) {
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        tBankcardAgent.setSupplierBranchId(userSupplierbranchid);
        tBankcardAgent.setCreateby(UtilsUser.getLoginName());
        List<Agentcenter> list = tBankcardAgentService.selectAgentcenterList(tBankcardAgent);
        ExcelUtil<Agentcenter> util = new ExcelUtil<Agentcenter>(Agentcenter.class);
        return util.exportExcel(list, "agentcenter");
    }

    /**
     * 新增卡商代理
     */
    @GetMapping("/add")
    public String add(ModelMap map) {
        int level = 1;
        SysUser sysUser = ShiroUtils.getSysUser();
        Double maxRatio = dictBankCommonService.selectMaxRatioByBranchId(sysUser.getSupplierbranchid());

        //如果是登录账号是admin，只能开一级代理;否则只能开比登录账号低一级的代理

        if(!sysUser.isAdmin()) {
            Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(Long.valueOf(ShiroUtils.getAgentId()));
            level = Integer.parseInt(tBankcardAgent.getAgentLevel()) + 1;
            maxRatio = tBankcardAgent.getRatio();
        }
        map.put("level",level);
        map.put("userId",UtilsUser.getUserId());
        map.put("levelLable",dictDataService.selectDictLabel("agent_level",level+""));
        map.put("maxRatio",maxRatio);
        return prefix + "/add";
    }

    /**
     * 新增保存卡商代理
     */
    @RequiresPermissions("agent:agentcenter:add")
    @Log(title = "新增卡商代理", businessType = BusinessType.INSERT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_BANK_CARD))
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Agentcenter tBankcardAgent) {
        Agentcenter checkAgentcenter = new Agentcenter();
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        checkAgentcenter.setSupplierBranchId(userSupplierbranchid);
        checkAgentcenter.setCreateby(UtilsUser.getLoginName());
        List<Agentcenter> list = tBankcardAgentService.selectAgentcenterList(checkAgentcenter);
        if(list.isEmpty() || list.size() == 0){
            if(!StringUtils.isEmpty(tBankcardAgent.getPassword())) {
                if (!UtilsUser.checkPassWord(tBankcardAgent.getPassword())) {
                    return error("密码不符合规则，请重新输入,(必须包含8-20位数字与字母和大写字母");
                }
            }
            String agentCode="A"+tBankcardAgent.getAgentLevel();
            Agentcenter agentcenter=tBankcardAgentService.selectAgentCenterByAgentCode(agentCode);
            if(agentcenter ==null || StringUtils.isEmpty(agentcenter.getAgentLevel())){
                //默认是A+等级+初始值(001)
                tBankcardAgent.setAgentCode(agentCode+"001");
            }else{
                Format f1=new DecimalFormat("000");
                agentCode+=f1.format(Integer.parseInt(agentcenter.getAgentCode().substring(2))+1);
                tBankcardAgent.setAgentCode(agentCode);
            }
            tBankcardAgent.setStatus("0");//默认停用状态

            //SysUser sysUser = ShiroUtils.getSysUser();
//        if (sysUser.isAdmin()) {  //如果是管理员账号
//            //Long maxId = dictBankCommonService.getMaxSupplierBranchId()+1;
//            tBankcardAgent.setSupplierBranchId(tBankcardAgent.getSupplierBranchId());
//        } else {
//            Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
//            tBankcardAgent.setSupplierBranchId(userSupplierbranchid);
//        }

            tBankcardAgent.setSupplierBranchId(tBankcardAgent.getSupplierBranchId());
            return toAjax(tBankcardAgentService.insertAgentcenter(tBankcardAgent));
        }
        return error("卡商只可以创建一个一级代理!");
    }


    /**
     * 修改保存卡商代理
     */
    @RequiresPermissions("agent:agentcenter:edit")
    @Log(title = "修改卡商代理", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_BANK_CARD))
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Agentcenter tBankcardAgent) {
        if(!StringUtils.isEmpty(tBankcardAgent.getPassword())) {
            if (!UtilsUser.checkPassWord(tBankcardAgent.getPassword())) {
                return error("密码不符合规则，请重新输入,(必须包含8-20位数字与字母和大写字母");
            }
        }

         Agentcenter agentcenter = tBankcardAgentService.selectAgentcenterById(tBankcardAgent.getId());

        if(agentcenter==null || "-1".equals(agentcenter.getStatus())){
            return  error("账号："+tBankcardAgent.getLoginName()+" 账号不存在或已删除");
        }
        if(tBankcardAgent.getLoginName()!= null && !agentcenter.getLoginName().equals(tBankcardAgent.getLoginName())){
            return  error("代理账号不可修改");
        }
        if(tBankcardAgent.getAgentLevel()!=null && !agentcenter.getAgentLevel().equals(tBankcardAgent.getAgentLevel())){
            return  error("代理级别不可修改");
        }
        return toAjax(tBankcardAgentService.updateAgentcenter(tBankcardAgent));
    }

    /**
     * 修改卡商代理
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:user:list")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(id);
        Double maxRatio = dictBankCommonService.selectMaxRatioByBranchId(tBankcardAgent.getSupplierBranchId());
//        if(UtilsUser.getUserId() != 1) {
//            maxRatio = tBankcardAgent.getRatio();
//        }
        mmap.put("tBankcardAgent", tBankcardAgent);
        mmap.put("maxRatio",maxRatio);
        return prefix + "/edit";
    }

    /**
     * 查看卡商代理
     */
    @GetMapping("/proview/{id}")
    @RequiresPermissions("system:user:list")
    public String proview(@PathVariable("id") Long id, ModelMap mmap) {
        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(id);
        mmap.put("tBankcardAgent", tBankcardAgent);
        return prefix + "/proview";
    }


    /**
     * 删除卡商代理
     */
    @RequiresPermissions("agent:agentcenter:remove")
    @Log(title = "删除卡商代理", businessType = BusinessType.DELETE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_BANK_CARD))
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        Agentcenter tBankcardAgent = tBankcardAgentService.selectAgentcenterById(Long.valueOf(ids));
        if("1".equals(tBankcardAgent.getStatus())){
            return error("启用状态不允许删除！");
        }
        return toAjax(tBankcardAgentService.deleteAgentcenterById(Long.valueOf(ids)));
    }

}
