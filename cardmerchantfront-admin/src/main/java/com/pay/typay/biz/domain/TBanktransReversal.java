package com.pay.typay.biz.domain;

import com.pay.typay.biz.messages.ConstantsSelectUIToExcel;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行卡流水对象 t_banktrans
 *
 * @author Warren
 * @date 2020-01-08
 */
@Data
public class TBanktransReversal extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "流水类型")
    private String transordertype;

    @Excel(name = "商户订单号")
    private String merchantorderid;

    @Excel(name = "银行卡简码")
    private String bankaccount;

    @Excel(name = "收入", cellType = Excel.ColumnType.DOUBLE)
    private BigDecimal deposit;

    @Excel(name = "支出", cellType = Excel.ColumnType.DOUBLE)
    private BigDecimal withdraw;

    @Excel(name = "手续费", cellType = Excel.ColumnType.DOUBLE)
    private BigDecimal transfee;

    @Excel(name = "余额", cellType = Excel.ColumnType.DOUBLE)
    private BigDecimal balance;

    @Excel(name = "服务状态", readConverterExp = "0=创建,1=处理中,2=成功,3=异常")
    private Long status;

    @Excel(name = "交易类型", readConverterExp = ConstantsSelectUIToExcel.TransType)
    private Long transtype;

    @Excel(name = "交易姓名")
    private String name;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "交易时间", width = 30)
    private String transtime;

    @Excel(name = "创建时间", width = 30)
    private String createtime;


    /**
     * 流水ID
     */
    private Long id;
    /**
     * 财务分部ID
     */
    private Long supplierbranchid;
    /**
     * 客户端编号
     */
    private Long clientid;
    /**
     * 银行类型代码
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
     * 转账金额
     */
    private BigDecimal transamount;
    /**
     * 交易前余额
     */
    private BigDecimal prebalance;
    /**
     * 交易银行卡
     */
    private String transaccount;
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


    //业务字段
    private Double minpayamount;
    private Double maxpayamount;
    private Integer translevel;
    private String merchantorderidfilter;
    private String queryTime;
    //统计字段
    private int mateCount;
    private BigDecimal mateAmount;
    private int mateCountw;
    private BigDecimal mateAmountw;

}
