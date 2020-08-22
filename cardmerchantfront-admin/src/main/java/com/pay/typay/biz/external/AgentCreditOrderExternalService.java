package com.pay.typay.biz.external;
import com.pay.typay.biz.agent.domain.Agentcreditorder;
import com.pay.typay.biz.agent.service.IAgentcreditorderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 信用、额度转换订单对外服务
 * @date : 2020年05月26日 17:35
 */
@Controller
@RequestMapping("/external/order/agentcredit")
public class AgentCreditOrderExternalService extends BaseExternalService {
    @Autowired
    private IAgentcreditorderService agentcreditorderService;

    /**
     * 查询信用、额度转换订单列表
     * orderType  1为信用订单，2为转额度订单
     */
    @PostMapping("/list")
    @ResponseBody
    public List<Agentcreditorder> list(Agentcreditorder agentcreditorder) {
        return agentcreditorderService.selectAgentcreditorderList(agentcreditorder);
    }

    /**
     * 查看信用、额度转换订单
     */
    @GetMapping("/get/{orderIndex}")
    @ResponseBody
    public Agentcreditorder get(@PathVariable("orderIndex") Long orderIndex) {
        return agentcreditorderService.selectAgentcreditorderById(orderIndex);
    }
}
