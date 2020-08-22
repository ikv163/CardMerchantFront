package com.pay.typay.biz.reports.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 三方渠道下发账变对象 t_thirdchannelissuedrecord
 * 
 * @author js-bucky
 * @date 2020-01-22
 */
@Data
public class ThirdChannelIssuedRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 平台订单索引 */
    private Long orderindex;

    /** 渠道ID */
    @Excel(name = "渠道ID")
    private Long paychannelid;

    /** null */
    @Excel(name = "null")
    private String orderid;

    /** 渠道订单号 */
    @Excel(name = "渠道订单号")
    private String channelorderid;

    /** 交易方式,0：充值，1：提款 */
    @Excel(name = "交易方式,0：充值，1：提款")
    private Long transtype;

    /** null */
    @Excel(name = "null")
    private Double transamount;

    /** null */
    @Excel(name = "null")
    private Double paidamount;

    /** null */
    @Excel(name = "null")
    private Double supplierpaidchannelfee;

    /** null */
    @Excel(name = "null")
    private Double paychannelfee;

    /** null */
    @Excel(name = "null")
    private Double surpplierfee;

    /** null */
    @Excel(name = "null")
    private Double prebalance;

    /** null */
    @Excel(name = "null")
    private Double balance;

    /** null */
    @Excel(name = "null")
    private Long supplierbranchid;

    /** 商户ID */
    @Excel(name = "商户ID")
    private Long merchantid;


}
