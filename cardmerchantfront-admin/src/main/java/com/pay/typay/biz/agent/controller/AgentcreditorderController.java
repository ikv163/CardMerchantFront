package com.pay.typay.biz.agent.controller;

import java.util.List;

import com.pay.typay.biz.agent.domain.Agentcreditorder;
import com.pay.typay.biz.agent.service.IAgentcreditorderService;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.common.annotation.TranslateKey;
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
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.common.core.page.TableDataInfo;

/**
 * 代理额度转换,信用订单Controller
 * 
 * @author warren
 * @date 2020-05-26
 */
@Controller
@RequestMapping("/biz/agentcreditorder")
public class AgentcreditorderController extends BaseController
{
    private String prefix = "biz/agentcreditorder";

    @Autowired
    private IAgentcreditorderService tAgentcreditorderService;

    @RequiresPermissions("biz:agentcreditorder:view")
    @GetMapping()
    public String agentcreditorder()
    {
        return prefix + "/agentcreditorder";
    }

    /**
     * 查询代理额度转换,信用订单列表
     */
    @RequiresPermissions("biz:agentcreditorder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Agentcreditorder tAgentcreditorder)
    {
        startPage();
        List<Agentcreditorder> list = tAgentcreditorderService.selectAgentcreditorderList(tAgentcreditorder);
        return getDataTable(list);
    }

    /**
     * 导出代理额度转换,信用订单列表
     */
    @RequiresPermissions("biz:agentcreditorder:export")
    @Log(title = "导出代理额度转换,信用订单", businessType = BusinessType.EXPORT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_CREDIT_ORDER))
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Agentcreditorder tAgentcreditorder)
    {
        List<Agentcreditorder> list = tAgentcreditorderService.selectAgentcreditorderList(tAgentcreditorder);
        ExcelUtil<Agentcreditorder> util = new ExcelUtil<Agentcreditorder>(Agentcreditorder.class);
        return util.exportExcel(list, "agentcreditorder");
    }

    /**
     * 新增代理额度转换,信用订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存代理额度转换,信用订单
     */
    @RequiresPermissions("biz:agentcreditorder:add")
    @Log(title = "新增代理额度转换,信用订单", businessType = BusinessType.INSERT,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_CREDIT_ORDER))
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Agentcreditorder tAgentcreditorder)
    {
        return toAjax(tAgentcreditorderService.insertAgentcreditorder(tAgentcreditorder));
    }

    /**
     * 修改保存代理额度转换,信用订单
     */
    @RequiresPermissions("biz:agentcreditorder:edit")
    @Log(title = "修改代理额度转换,信用订单", businessType = BusinessType.UPDATE,
            translateKeys = @TranslateKey(filedData = TranslateKeyConstant.AGENT_CREDIT_ORDER))
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Agentcreditorder tAgentcreditorder)
    {
        return toAjax(tAgentcreditorderService.updateAgentcreditorder(tAgentcreditorder));
    }
    /**
     * 修改代理额度转换,信用订单
     */
    @GetMapping("/edit/{orderindex}")
    @RequiresPermissions("biz:agentcreditorder:list")
    public String edit(@PathVariable("orderindex") Long orderindex, ModelMap mmap)
    {
        Agentcreditorder tAgentcreditorder = tAgentcreditorderService.selectAgentcreditorderById(orderindex);
        mmap.put("tAgentcreditorder", tAgentcreditorder);
        return prefix + "/edit";
    }

}
