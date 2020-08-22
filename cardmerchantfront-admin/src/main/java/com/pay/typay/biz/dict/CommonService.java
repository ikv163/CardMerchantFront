package com.pay.typay.biz.dict;

import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.service.IAgentcenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("common")
public class CommonService {

    @Autowired
    private IAgentcenterService agentcenterService;

    /**
     * 查询卡商
     * @return
     */
    public List selectAgentcenterList(){
        Agentcenter tBankcardAgent = new Agentcenter();
        tBankcardAgent.setAgentLevel("0");
        return agentcenterService.selectAgentcenterList(tBankcardAgent);
    }
}
