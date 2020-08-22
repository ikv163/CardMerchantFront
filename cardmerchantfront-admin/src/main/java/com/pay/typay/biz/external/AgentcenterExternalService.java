package com.pay.typay.biz.external;

import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.service.IAgentcenterService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 卡商管理对外服务
 * @date : 2020年05月26日 17:35
 */
@Controller
@RequestMapping("/external/agentcenter")
public class AgentcenterExternalService extends BaseExternalService {

    @Autowired
    private IAgentcenterService tBankcardAgentService;

    /**
     * 查询卡商代理列表
     */
    @PostMapping("/list")
    @ResponseBody
    public  List<Agentcenter> list(Agentcenter tBankcardAgent) {
        List<Agentcenter> list = tBankcardAgentService.selectAgentcenterList(tBankcardAgent);
        return list;
    }
    /**
     * 查看卡商代理
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public Agentcenter get(@PathVariable("id") Long id) {
        return tBankcardAgentService.selectAgentcenterById(id);
    }
    /**
     * 新增保存卡商代理
     */
    @Log(title = "卡商代理-外部接口新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(Agentcenter tBankcardAgent) {
        return toAjax(tBankcardAgentService.insertAgent(tBankcardAgent));
    }
}
