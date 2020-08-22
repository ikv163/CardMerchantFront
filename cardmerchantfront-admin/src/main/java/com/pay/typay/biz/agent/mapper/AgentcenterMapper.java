package com.pay.typay.biz.agent.mapper;

import com.pay.typay.biz.agent.domain.Agentcenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡商代理Mapper接口
 * 
 * @author oswald
 * @date 2020-05-13
 */
public interface AgentcenterMapper 
{
    /**
     * 查询卡商代理
     * 
     * @param id 卡商代理ID
     * @return 卡商代理
     */
     Agentcenter selectAgentcenterById(Long id,String supplierbranchId,String createBy);

    /**
     * 通过卡商名称查询卡商代理
     * @param agentName
     * @return
     */
     Agentcenter selectAgentCenterByAgentName(String agentName);

    /**
     * 查询卡商代理列表
     * 
     * @param tBankcardAgent 卡商代理
     * @return 卡商代理集合
     */
     List<Agentcenter> selectAgentcenterList(Agentcenter tBankcardAgent);
     List<Agentcenter> selectAgentcenterListContrant(Agentcenter tBankcardAgent);

    /**
     * 新增卡商代理
     * 
     * @param tBankcardAgent 卡商代理
     * @return 结果
     */
     int insertAgentcenter(Agentcenter tBankcardAgent);

    /**
     * 修改卡商代理
     * 
     * @param tBankcardAgent 卡商代理
     * @return 结果
     */
     int updateAgentcenter(Agentcenter tBankcardAgent);

    /**
     * 删除卡商代理
     * 
     * @param id 卡商代理ID
     * @return 结果
     */
     int deleteAgentcenterById(Long id);

    /**
     * 批量删除卡商代理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteAgentcenterByIds(String[] ids);


    Agentcenter selectAgentCenterByParentAgentId(@Param("parentAgentId") String parentAgentId);

    Agentcenter selectAgentCenterByAgentCode(@Param("agentCode") String agentCode);
}
