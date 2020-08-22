package com.pay.typay.biz.agent.mapper;

import com.pay.typay.biz.agent.domain.Agentcreditorder;

import java.util.List;

/**
 * 代理额度转换,信用订单Mapper接口
 * 
 * @author warren
 * @date 2020-05-26
 */
public interface AgentcreditorderMapper 
{
    /**
     * 查询代理额度转换,信用订单
     * 
     * @param orderindex 代理额度转换,信用订单ID
     * @return 代理额度转换,信用订单
     */
     Agentcreditorder selectAgentcreditorderById(Long orderindex,String supplierbranchid);

    /**
     * 查询代理额度转换,信用订单列表
     * 
     * @param tAgentcreditorder 代理额度转换,信用订单
     * @return 代理额度转换,信用订单集合
     */
     List<Agentcreditorder> selectAgentcreditorderList(Agentcreditorder tAgentcreditorder);

    /**
     * 新增代理额度转换,信用订单
     * 
     * @param tAgentcreditorder 代理额度转换,信用订单
     * @return 结果
     */
     int insertAgentcreditorder(Agentcreditorder tAgentcreditorder);

    /**
     * 修改代理额度转换,信用订单
     * 
     * @param tAgentcreditorder 代理额度转换,信用订单
     * @return 结果
     */
     int updateAgentcreditorder(Agentcreditorder tAgentcreditorder);

}
