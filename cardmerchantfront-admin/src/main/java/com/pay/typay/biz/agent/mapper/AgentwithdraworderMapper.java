package com.pay.typay.biz.agent.mapper;

import com.pay.typay.biz.agent.domain.Agentwithdraworder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡商提款订单Mapper接口
 * 
 * @author oswald
 * @date 2020-05-14
 */
public interface AgentwithdraworderMapper 
{
    /**
     * 查询卡商提款订单
     * 
     * @param orderindex 卡商提款订单ID
     * @return 卡商提款订单
     */
     Agentwithdraworder selectAgentwithdraworderById(Long orderindex,String supplierbranchid);

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
     * 删除卡商提款订单
     * 
     * @param orderindex 卡商提款订单ID
     * @return 结果
     */
     int deleteAgentwithdraworderById(Long orderindex);

    /**
     * 批量删除卡商提款订单
     * 
     * @param orderindexs 需要删除的数据ID
     * @return 结果
     */
     int deleteAgentwithdraworderByIds(String[] orderindexs);

    /**
     *
     * @param agentId
     * @param startTime
     * @param endTime
     * @return
     */
     Agentwithdraworder getSuccessSummary(@Param(value="agentId")String agentId, @Param(value="startTime")String startTime, @Param(value="endTime")String endTime);
}
