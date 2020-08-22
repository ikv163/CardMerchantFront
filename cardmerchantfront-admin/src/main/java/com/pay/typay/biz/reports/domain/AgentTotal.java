package com.pay.typay.biz.reports.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;
import java.math.BigDecimal;
/**
 * 统计卡分销总额，今日充值和提款
 * 
 * @author warren
 * @date 2020-05-18
 */
@Data
public class AgentTotal extends BaseEntity
{
    //交易金额
    private Double totalAmount;
    //用户充值金额
    private BigDecimal depositAmount;
    //用户提款金额
    private BigDecimal withdrawAmount;
    //代理名称
    private String agentName;
    //交易笔数
    private BigDecimal transCount;
    //成功笔数
    private BigDecimal successTransCount;
    //成功率
    private double successRatio;
    //费率
    private double agentRatio;
    //账号余额
    private BigDecimal balance;
    //利润
    private double profit;
    //信用额度
    private double creditBalance;
    //可用额度
    private double availableBalance;
    //代理提款金额
    private BigDecimal agentWithdrawAmount;

}
