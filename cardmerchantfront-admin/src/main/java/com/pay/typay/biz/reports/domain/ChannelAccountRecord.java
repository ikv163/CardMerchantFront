package com.pay.typay.biz.reports.domain;

import lombok.Data;

import java.util.Date;

/**
 * 银行卡渠道账变对象 t_channelaccountrecord
 * 
 * @author js-bucky
 * @date 2020-01-07
 */
@Data
public class ChannelAccountRecord
{
    private static final long serialVersionUID = 1L;

    /** 平台订单索引 */
    private Long orderIndex;

    /** 渠道ID */
    private Long payChannelID;

    /** 平台订单号 */
    private String orderID;

    /** 渠道订单号 */
    private String channelOrderID;

    /** 交易方式 */
    private Integer transType;

    /** 交易金额 */
    private Double transAmount;

    /** 实际金额 */
    private Double paidAmount;

    /** null */
    private Double supplierPaidChannelFee;

    /** 实际手续费 */
    private Double payChannelFee;

    /** null */
    private Double surpplierFee;

    /** 交易前余额 */
    private Double preBalance;

    /** 余额 */
    private Double balance;

    /** null */
    private Long supplierBranchID;

    private Date createTime;

}
