package com.pay.typay.biz.reports.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 银行卡日交易报表对象 t_bankcardsummary
 * 
 * @author oswald
 * @date 2020-01-09
 */
@Data
public class ReportsDayChildVO implements TreeEntity<ReportsDayChildVO>
{
    private static final long serialVersionUID = 1L;

    /**交易次数*/
    private int transCount = 0;
    /** 账户金额 */
    private BigDecimal accountAmount;
    /**今日交易*/
    private BigDecimal transToday;
    private String agentId;
    private String id;
    /**父级代理编码*/
    private String parentAgentId;
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
    /** 申请金额 */
    private BigDecimal transAmount;
    /**总提款额度*/
    private BigDecimal withdrawAmount;

    private List<ReportsDayChildVO> childList;
}
