package com.pay.typay.biz.reports.vo;

import com.pay.typay.biz.messages.ConstantsSelectUIToExcel;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

import static com.pay.typay.biz.messages.ConstantsSelectUIToExcel.*;

/**
 * 银行卡日交易报表对象 t_bankcardsummary
 * 
 * @author oswald
 * @date 2020-01-09
 */
@Data
public class ReportsVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**订单号*/
    @Excel(name = "订单号")
    private String orderId;
    @Excel(name = "卡商")
    private String merchantName;
    /**卡商agent_code*/
    private String merchantCode;
    /**代理账号*/
    @Excel(name = "代理账号")
    private String userName;
    /** 支付方式 */
    private Integer payType;
    @Excel(name = "支付方式")
    private String payTypeStr;
    /**交易类型*/
    private Integer transType;
    @Excel(name = "交易类型")
    private String transTypeStr;
    /** 账户金额 */
    @Excel(name = "卡简码")
    private String bankCode;
    /** 申请金额 */
    @Excel(name = "申请金额")
    private BigDecimal transAmount;
    @Excel(name = "实际金额")
    private BigDecimal paidAmount;
    /**利润*/
    @Excel(name = "利润")
    private BigDecimal profit;
    /** 分润 */
    @Excel(name = "分润")
    private BigDecimal splitProfit;
    /**状态*/
    private Integer status;
    @Excel(name = "状态")
    private String statusStr;

    private Long supplierBranchId;
}
