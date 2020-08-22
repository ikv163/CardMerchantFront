package com.pay.typay.biz.agent.mapper;

import com.pay.typay.biz.agent.domain.Agentdepositorder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代理银行卡充值订单Mapper接口
 * 
 * @author oswald
 * @date 2020-05-14
 */
public interface AgentdepositorderMapper 
{
    /**
     * 查询代理银行卡充值订单
     * 
     * @param orderindex 代理银行卡充值订单ID
     * @return 代理银行卡充值订单
     */
     Agentdepositorder selectAgentdepositorderById(Long orderindex,String supplierbranchid);

    /**
     * 查询代理银行卡充值订单列表
     * 
     * @param tAgentdepositorder 代理银行卡充值订单
     * @return 代理银行卡充值订单集合
     */
     List<Agentdepositorder> selectAgentdepositorderList(Agentdepositorder tAgentdepositorder);

    /**
     * 总计
     * @param tAgentdepositorder
     * @return
     */
     BigDecimal calcAgentdepositAmount(Agentdepositorder tAgentdepositorder);
    /**
     * 新增代理银行卡充值订单
     * 
     * @param tAgentdepositorder 代理银行卡充值订单
     * @return 结果
     */
     int insertAgentdepositorder(Agentdepositorder tAgentdepositorder);

    /**
     * 修改代理银行卡充值订单
     * 
     * @param tAgentdepositorder 代理银行卡充值订单
     * @return 结果
     */
     int updateAgentdepositorder(Agentdepositorder tAgentdepositorder);

    /**
     * 删除代理银行卡充值订单
     * 
     * @param orderindex 代理银行卡充值订单ID
     * @return 结果
     */
     int deleteAgentdepositorderById(Long orderindex);

    /**
     * 批量删除代理银行卡充值订单
     * 
     * @param orderindexs 需要删除的数据ID
     * @return 结果
     */
     int deleteAgentdepositorderByIds(String[] orderindexs);
}
