package com.pay.typay.biz.bankcard.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 银行卡流水对象 t_banktrans
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@Data
public class Statementcenter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 流水ID
     */
    @Excel(name = "流水ID")
    private Long id;

    /**
     * 商户订单号
     */
    @Excel(name="商户订单号")
    private String merchantOrderID;

    /**
     * 银行简码
     */
    @Excel(name = "银行简码")
    private String bankaccount;

    /**
     * 交易方式
     */
    @Excel(name = "交易方式", readConverterExp = "0=支出,1=收入")
    private Long transtype;

    /**
     * 交易金额
     */
    @Excel(name="交易金额")
    private Double transamount;

    /**
     * 余额
     */
    @Excel(name="余额")
    private Double balance;

    /**
     * 交易银行卡
     */
    private String transaccount;

    /**
     * 交易姓名
     */
    //@Excel(name = "交易姓名")
    private String name;

    @Excel(name="备注")
    private String remark;

    /**
     * 处理状态
     */
    @Excel(name = "处理状态", readConverterExp = "0=创建,1=处理中,2=成功,3=超时异常,4=补单")
    private Long status;

    /**
     * 交易时间
     */
    @Excel(name = "交易时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transtime;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;

    /**
     * 财务分部ID
     */
    private Long supplierbranchid;

    /**
     * 客户端编号
     */
    private Long clientid;

    /**
     * null
     */
    private String bankcode;

    /**
     * 银行卡编号
     */
    private Long bankid;

    /**
     * 渠道编号,内部卡转卡为0
     */
    private Long paychannelid;

    /**
     * null
     */
    private Double transfee;

    /**
     * null
     */
    private Double prebalance;

    /**
     * 加锁标记
     */
    private String readmark;

    /**
     * 被抓取时间
     */
    private Date picktime;

    /**
     * md5后的唯一编码
     */
    private String md5sign;

    /**
     * 商户id
     */
    private Integer merchantid;

    private int surplier_type = 1;

    private Long createby = 1L;

    private List<String> supplierBranchIdGroupList;
}
