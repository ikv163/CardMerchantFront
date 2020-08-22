package com.pay.typay.biz.reports.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行卡账变对象 t_bankaccountrecord
 * 
 * @author js-bucky
 * @date 2020-01-06
 */
@Data
public class BankAccountRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 银行卡ID */
    private Long bankid;

    /** 交易流水ID */
    private Long transid;

    /** 平台订单号索引 */
    private Long orderindex;

    /** 商户订单号索引 */
    private Long merchantorderindex;

    /** 平台订单号 */
    private String orderid;

    /** 商户订单号 */
    private String merchantorderid;

    /** 交易方式 */
    private Integer transtype;

    /** 交易金额 */
    private BigDecimal transamount;

    /** 实际金额 */
    private BigDecimal paidamount;

    /** 实际手续费 */
    private BigDecimal paychannelfee;

    /** 商户手续费 */
    private BigDecimal supplierpaidchannelfee;

    /** 交易前余额 */
    private Double prebalance;

    /** 余额 */
    private Double balance;

    /** null */
    private Long supplierbranchid;

    private Date createtime;

}
