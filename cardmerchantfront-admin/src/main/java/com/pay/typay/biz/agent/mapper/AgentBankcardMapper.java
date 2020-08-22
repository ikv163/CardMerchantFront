package com.pay.typay.biz.agent.mapper;

import com.pay.typay.biz.agent.domain.AgentBankcard;

import java.util.List;

/**
 * 代理银行卡Mapper接口
 * 
 * @author oswald
 * @date 2020-05-14
 */
public interface AgentBankcardMapper 
{
    /**
     * 查询代理银行卡
     * 
     * @param bankid 代理银行卡ID
     * @return 代理银行卡
     */
     AgentBankcard selectAgentBankcardById(Long bankid,String supplierbranchid);

    /**
     * 查询代理银行卡
     *
     * @param bankcode 代理银行卡简码
     * @return 代理银行卡
     */
    AgentBankcard selectAgentBankcardByBankCode(String bankcode);

    /**
     * 查询代理银行卡列表
     * 
     * @param tAgentbankcard 代理银行卡
     * @return 代理银行卡集合
     */
     List<AgentBankcard> selectAgentBankcardList(AgentBankcard tAgentbankcard);

    /**
     * 新增代理银行卡
     * 
     * @param tAgentbankcard 代理银行卡
     * @return 结果
     */
     int insertAgentBankcard(AgentBankcard tAgentbankcard);

    /**
     * 修改代理银行卡
     * 
     * @param tAgentbankcard 代理银行卡
     * @return 结果
     */
     int updateAgentBankcard(AgentBankcard tAgentbankcard);

    /**
     * 删除代理银行卡
     * 
     * @param bankid 代理银行卡ID
     * @return 结果
     */
     int deleteAgentBankcardById(Long bankid);

    /**
     * 批量删除代理银行卡
     * 
     * @param bankids 需要删除的数据ID
     * @return 结果
     */
     int deleteAgentBankcardByIds(String[] bankids);
}
