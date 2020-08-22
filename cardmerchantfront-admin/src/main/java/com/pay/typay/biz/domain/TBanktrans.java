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
public class TBanktrans extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流水ID */
    @Excel(name = "流水编号")
    private Long id;

    /** 商户订单号 */
    @Excel(name = "商户订单号")
    private String merchantOrderID;

    /** 银行简记码 */
    @Excel(name = "银行卡简码")
    private String bankaccount;
    /** 财务分部ID */
    private Long supplierbranchid;

    /** 客户端编号 */
    private Long clientid;

    /** 银行类型代码 */
    private String bankcode;

    /** 转账金额 */
    private BigDecimal transamount;

    /** 付款方式:0支出,1:收入 */
    @Excel(name = "付款方式",readConverterExp="0=支出,1=收入")
    private Long transtype;

    @Excel(name = "收入",cellType= Excel.ColumnType.DOUBLE)
    private BigDecimal deposit;

    @Excel(name = "支出",cellType= Excel.ColumnType.DOUBLE)
    private BigDecimal withdraw;

    @Excel(name = "手续费",cellType= Excel.ColumnType.DOUBLE)
    private BigDecimal fee;

    private String transtypeStr;

    /** 余额 */
    @Excel(name = "余额",cellType= Excel.ColumnType.DOUBLE)
    private BigDecimal balance;

    /** 处理状态，0：创建，1：处理中，2：已成功处理，3：超时移入异常收支 */
    @Excel(name = "流水状态",readConverterExp="0=创建,1=处理中,2=成功,3=异常")
    private Long status;

    @Excel(name = "交易类型", readConverterExp = ConstantsSelectUIToExcel.TransType)
    private String transOrderType;

    private Long statusfilter;

    private String statusStr;

    /** 交易姓名 */
    @Excel(name = "交易姓名")
    private String name;

    @Excel(name = "银行卡简码")
    private String depostBankAcount;

    private String bankNum;
    @Excel(name = "卡池名称")
    private String bankPoolName;

    @Excel(name = "备注")
    private String Remark;

    /** 交易时间 */
    @Excel(name = "交易时间", width = 30, dateFormat = "yyyy/M/d h:mm")
    private Date transtime;


    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy/M/d h:mm")
    private Date createtime;

    private String depositOrderBankCode;


    /** 银行卡编号 */
    private Long bankid;

    /** 渠道编号,内部卡转卡为0 */
    private Long paychannelid;

    /** 转账手续费 */
    private BigDecimal transfee;

    /** 交易前余额 */
    private BigDecimal prebalance;

    /** 交易银行卡 */
    private String transaccount;

    /** 加锁标记 */
    private String readmark;

    /** 被抓取时间 */
    private Date picktime;

    /** md5后的唯一编码 */
    private String md5sign;

    /** 商户id */
    private Integer merchantid;

    private String banknum;


    private Double minpayamount;
    private Double maxpayamount;

    private Integer WorkType;

    /**
     * 统计记录条数
     */
    private BigDecimal transCount;

    /**
     * 总计
     */
    private BigDecimal transSumAmount;

    //卡池类型
    private Integer roletype;

    /**
     * 卡池workType
     */
    private Integer bankcardworktype;
}
