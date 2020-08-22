package com.pay.typay.biz.agent.service;

import com.pay.typay.biz.agent.domain.Agentwithdraworder;

import java.util.List;

/**
 * 卡商提款订单Service接口
 * 
 * @author oswald
 * @date 2020-05-14
 */
public interface IAgentwithdraworderService 
{
    /**
     * 查询卡商提款订单
     * 
     * @param orderindex 卡商提款订单ID
     * @return 卡商提款订单
     */
     Agentwithdraworder selectAgentwithdraworderById(Long orderindex);

    /**
     * 查询卡商提款订单列表
     * 
     * @param tAgentwithdraworder 卡商提款订单
     * @return 卡商提款订单集合
     */
     List<Agentwithdraworder> selectAgentwithdraworderList(Agentwithdraworder tAgentwithdraworder);

    /**
     * 新增卡商提款订单
     * 
     * @param tAgentwithdraworder 卡商提款订单
     * @return 结果
     */
     int insertAgentwithdraworder(Agentwithdraworder tAgentwithdraworder);

    /**
     * 修改卡商提款订单
     * 
     * @param tAgentwithdraworder 卡商提款订单
     * @return 结果
     */
     int updateAgentwithdraworder(Agentwithdraworder tAgentwithdraworder);

    /**
     * 取消订单
     * @param tAgentwithdraworder
     * @return
     */
     int cancelOrder(Agentwithdraworder tAgentwithdraworder);
    /**
     * 批量删除卡商提款订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteAgentwithdraworderByIds(String ids);

    /**
     * 删除卡商提款订单信息
     * 
     * @param orderindex 卡商提款订单ID
     * @return 结果
     */
     int deleteAgentwithdraworderById(Long orderindex);

    /**
     * 获取出款成功的统计数据
     * @param agentId
     * @param startTime
     * @param endTime
     * @return
     */
     Agentwithdraworder getSuccessSummary(String agentId,String startTime,String endTime);
}
