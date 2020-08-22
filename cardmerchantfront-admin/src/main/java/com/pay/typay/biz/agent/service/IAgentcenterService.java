package com.pay.typay.biz.agent.service;

import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.domain.Agentcreditorder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 卡商代理Service接口
 * 
 * @author oswald
 * @date 2020-05-13
 */
public interface IAgentcenterService 
{
    /**
     * 查询卡商代理
     * 
     * @param id 卡商代理ID
     * @return 卡商代理
     */
     Agentcenter selectAgentcenterById(Long id);

    /**
     * 通过代理名称找卡商代理
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

    List<Agentcenter> profilesinfo(Agentcenter tBankcardAgent);

    /**
     * 新增卡商代理
     * 
     * @param tBankcardAgent 卡商代理
     * @return 结果
     */
     int insertAgentcenter(Agentcenter tBankcardAgent);

     int insertAgent(Agentcenter tBankcardAgent);
    /**
     * 修改卡商代理
     * 
     * @param tBankcardAgent 卡商代理
     * @return 结果
     */
     int updateAgentcenter(Agentcenter tBankcardAgent);

     int transMoney(Agentcenter tBankcardAgent , Agentcreditorder agentcreditorder,double transMoney);



    /**
     * 批量删除卡商代理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteAgentcenterByIds(String ids);

    /**
     * 删除卡商代理信息
     * 
     * @param id 卡商代理ID
     * @return 结果
     */
     int deleteAgentcenterById(Long id);

    List<Agentcenter> checkLoginNameUnique(Agentcenter tBankcardAgent);


    Agentcenter selectAgentCenterByParentAgentId(String parentAgentId);

    Agentcenter selectAgentCenterByAgentCode(String agentCode);

}
