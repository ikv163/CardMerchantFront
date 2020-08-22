package com.pay.typay.biz.reports.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商户账变对象 t_merchantaccountrecord
 *
 * @author js-bucky
 * @date 2020-01-07
 */
public class MerchantAccountRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * null
     */
    @Excel(name = "null")
    private Long merchantID;

    /**
     * 平台订单号
     */
    @Excel(name = "平台订单号")
    private String orderID;

    /**
     * 商户订单号
     */
    @Excel(name = "商户订单号")
    private String merchantOrderID;

    /**
     * 交易方式
     */
    @Excel(name = "交易方式")
    private Integer transType;

    /**
     * 交易金额
     */
    @Excel(name = "交易金额")
    private Double transAmount;

    /**
     * 实际金额
     */
    @Excel(name = "实际金额",cellType = Excel.ColumnType.DOUBLE)
    private Double paidAmount;

    /**
     * 实际手续费
     */
    @Excel(name = "实际手续费",cellType = Excel.ColumnType.DOUBLE)
    private Double payChannelFee;

    /**
     * 商户手续费
     */
    @Excel(name = "商户手续费",cellType = Excel.ColumnType.DOUBLE)
    private Double supplierPaidChannelFee;

    /**
     * 交易前余额
     */
    @Excel(name = "交易前余额")
    private Double preBalance;

    /**
     * 余额
     */
    @Excel(name = "余额")
    private Double balance;

    /**
     * null
     */
    private Long supplierBranchID;

    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    public Long getMerchantID() {
        return merchantID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setMerchantOrderID(String merchantOrderID) {
        this.merchantOrderID = merchantOrderID;
    }

    public String getMerchantOrderID() {
        return merchantOrderID;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPayChannelFee(Double payChannelFee) {
        this.payChannelFee = payChannelFee;
    }

    public Double getPayChannelFee() {
        return payChannelFee;
    }

    public void setSupplierPaidChannelFee(Double supplierPaidChannelFee) {
        this.supplierPaidChannelFee = supplierPaidChannelFee;
    }

    public Double getSupplierPaidChannelFee() {
        return supplierPaidChannelFee;
    }

    public void setPreBalance(Double preBalance) {
        this.preBalance = preBalance;
    }

    public Double getPreBalance() {
        return preBalance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setSupplierBranchID(Long supplierBranchID) {
        this.supplierBranchID = supplierBranchID;
    }

    public Long getSupplierBranchID() {
        return supplierBranchID;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("merchantID", getMerchantID())
                .append("orderID", getOrderID())
                .append("merchantOrderID", getMerchantOrderID())
                .append("transType", getTransType())
                .append("transAmount", getTransAmount())
                .append("paidAmount", getPaidAmount())
                .append("payChannelFee", getPayChannelFee())
                .append("supplierPaidChannelFee", getSupplierPaidChannelFee())
                .append("preBalance", getPreBalance())
                .append("balance", getBalance())
                .append("createTime", getCreateTime())
                .append("supplierBranchID", getSupplierBranchID())
                .toString();
    }
}
