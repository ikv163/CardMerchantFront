package com.pay.typay.biz.agent.service;

import com.pay.typay.biz.agent.domain.AgentBankcard;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代理银行卡Service接口
 * 
 * @author oswald
 * @date 2020-05-14
 */
public interface IAgentBankcardService 
{
    /**
     * 查询代理银行卡
     * 
     * @param bankid 代理银行卡ID
     * @return 代理银行卡
     */
     AgentBankcard selectAgentBankcardById(Long bankid);

    /**
     * 通过银行卡简码查询代理银行卡
     * @param bankcode
     * @return
     */
     AgentBankcard selectAgentBankcardByBankCode(String bankcode);

    /**
     * 查询代理银行卡列表
     * 
     * @param tAgentbankcard 代理银行卡
     * @return 代理银行卡集合
     */
     List<AgentBankcard> selectAgentBankcardList(AgentBankcard tAgentbankcard);

    List<AgentBankcard> selectAgentBankcardListAll(AgentBankcard tAgentbankcard);

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
     * 批量删除代理银行卡
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteAgentBankcardByIds(String ids);

    /**
     * 删除代理银行卡信息
     * 
     * @param bankid 代理银行卡ID
     * @return 结果
     */
     int deleteAgentBankcardById(Long bankid);

    /**
     * 额度转换计算可提金额
     * @return
     */
     BigDecimal calcWithdrawOkAmount();
}
