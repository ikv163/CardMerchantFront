package com.pay.typay.biz.reports.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 银行卡日交易报表对象 t_bankcardsummary
 * 
 * @author oswald
 * @date 2020-01-09
 */
@Data
public class ReportsDayVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "代理账号")
    private String userName;

    private String agentName;
    /** 成功率 */
    @Excel(name = "成功率")
    private String successRate;
    /**交易次数*/
    @Excel(name = "交易笔数")
    private int transCount = 0;
    /** 账户金额 */
    @Excel(name = "账户金额")
    private BigDecimal accountAmount;
    /**今日交易*/
    @Excel(name = "今日交易")
    private BigDecimal transToday;
    /**利润*/
    @Excel(name = "利润")
    private BigDecimal profit;
    /** 分润 */
    @Excel(name = "分润")
    private BigDecimal splitProfit;
    /** 信用额度 */
    @Excel(name = "信用额度")
    private BigDecimal creditBalance;
    /** 可提额度 */
    @Excel(name = "可提额度")
    private BigDecimal withdrawOkAmount;
    /**已提额度*/
    @Excel(name = "已提额度")
    private BigDecimal withdrawAmount;
    private String id;
    /**代理编码*/
    private String agentId;
    /**父级代理编码*/
    private String parentAgentId;

    private String parentAgentCodes;
    /**财务分支*/
    private Long supplierBranchId;
    /**代理登录账号id*/
    private Long userId;
    /** 费率 */
    private BigDecimal ratio;
    /**代理层级*/
    private String agentLevel;
    /**总充值额度*/
    private BigDecimal depositAmount;
    /**总次数*/
    private int totalCount = 0;
    /** 交易金额 */
    private BigDecimal paidAmount;
    /** 交易类型 */
    private String transType;

    /**订单号*/
    private String orderId;
    private String merchantName;
    /**卡商agent_code*/
    private String merchantCode;
    /** 支付方式 */
    private String payType;
    private String payTypeStr;
    private String transTypeStr;
    /** 账户金额 */
    private String bankCode;
    /** 申请金额 */
    private BigDecimal transAmount;
    /**状态*/
    private String status;
    private String statusStr;

    private BigDecimal agentDepositAmount;
    private BigDecimal agentWithdrawAmount;
    private int agentTransCount;
    private int agentTotalCount;

    private String merchantId;
    /**
     * 创建时间
     */
    private String createDate;
}
