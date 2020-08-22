package com.pay.typay.biz.reports.vo;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
    @Excel(name = "卡商")
    private String branchName;

    @Excel(name = "代理账号")
    private String agentName;
    /** 交易类型 */
    @Excel(name = "交易类型", readConverterExp = "1=用户充值,2=用户提款")
    private String transType;
    private String userName;
    @Excel(name = "订单金额")
    private BigDecimal totalPaidamount = BigDecimal.ZERO;
    @Excel(name = "支付金额")
    private BigDecimal totalPayamount = BigDecimal.ZERO;
    @Excel(name = "利润")
    private BigDecimal totalProfitAmount = BigDecimal.ZERO;

    /**级别/费率*/
//    @Excel(name = "级别/费率")
    private String levelRatio;
    /**级别*/
//    @Excel(name = "级别")
    private String level;
    /** 成功率 */
//    @Excel(name = "成功率")
    private String successRate = "0";
    /**交易次数*/
//    @Excel(name = "成功交易笔数")
    private int transCount = 0;
    /** 账户金额 */
//    @Excel(name = "账户金额")
    private BigDecimal accountAmount;

//    @Excel(name = "账户金额")
    private BigDecimal balance;

    /**今日交易*/
//    @Excel(name = "今日交易")
    private BigDecimal transToday = BigDecimal.ZERO;
    /**利润*/
//    @Excel(name = "利润")
    private BigDecimal profit = BigDecimal.ZERO;
    /** 分润 */
//    @Excel(name = "分润")
    private BigDecimal splitProfit = BigDecimal.ZERO;
    /** 信用额度 */
//    @Excel(name = "信用额度")
    private BigDecimal creditBalance = BigDecimal.ZERO;
    /** 可用额度 */
//    @Excel(name = "可用额度")
    private BigDecimal available_Balance = BigDecimal.ZERO;
//    @Excel(name = "利润")
    private BigDecimal profit_balance = BigDecimal.ZERO;
    /** 可提额度 */
//    @Excel(name = "可提额度")
    private BigDecimal withdrawOkAmount = BigDecimal.ZERO;
    /**已提额度*/
//    @Excel(name = "已提额度")
    private BigDecimal withdrawAmount = BigDecimal.ZERO;

    /**代理编码*/
    private String id;
    /**代理编码*/
    private String agentId;
    /**父级代理编码*/
    private String parentAgentid;
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


    /**订单号*/
    private String OrderId;
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

    private String beginTime;
    private String endTime;

    private String createDate;

    private BigDecimal depositAmountDay;
    private BigDecimal withdrawAmountDay;
    private Integer depositCount;
    private Integer withdrawCount;

}

