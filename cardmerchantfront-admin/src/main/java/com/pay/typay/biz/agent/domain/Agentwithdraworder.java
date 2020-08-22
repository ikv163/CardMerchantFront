package com.pay.typay.biz.agent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pay.typay.biz.messages.ConstantsSelectUIToExcel;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 卡商提款订单对象 t_agentwithdraworder
 * 
 * @author oswald
 * @date 2020-05-14
 */
@Data
public class Agentwithdraworder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单索引 */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long orderindex;

    /** 订单id */
    @Excel(name = "订单id")
    @JsonSerialize(using= ToStringSerializer.class)
    private String orderid;


    @Excel(name = "代理账号")
    private String agent_name;

    /** 银行卡简码 */
    @Excel(name = "卡简码")
    private String bankcode;

    /** 财务分部id */
    private Long supplierbranchid;

    /** 付款方式,1：银行卡，2：第三方，3：其他方式 */
    @Excel(name = "付款方式",readConverterExp = ConstantsSelectUIToExcel.AgentPayType)
    private Long paytype;

    /** 提款金额 */
    @Excel(name = "申请金额",cellType = Excel.ColumnType.DOUBLE)
    private Double payamount;

    /** 实现到帐 */
    @Excel(name = "实际到账",cellType = Excel.ColumnType.DOUBLE)
    private Double paidamount;

    /** 订单状态 */
    @Excel(name = "订单状态",readConverterExp = ConstantsSelectUIToExcel.AgentOrderStatus)
    private Long orderstatus;



    /** 订单通知时间 */
    /**
     * 创建时间
     */
    @Excel(name = "创建时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date ordernotifytime;


    /**
     * 最后更新时间
     */
    @Excel(name = "更新时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date lastupdatetime;

    /** 通知地址 */
    private String notifyurl;

    /** 回调地址 */
    private String returnurl;

    /** 处理该订单的充值渠道 */
    private Long paychannelid;

    /** 银行帐号 */
//    @Excel(name = "银行帐号")
    private String bankaccount;



    /** 订单通知状态 */
    private Long ordernotifystatus;



    /** 银行卡号 */
//    @Excel(name = "银行卡号")
    private String banknum;

    /** 银行卡地址 */
    private String bankaddress;

    /** 银行卡持有者 */
    private String bankowner;

    /** md5订单加密信息 */
    private String md5orderid;

    /** digest */
    private String digest;

    /** 订单类型 */
//    @Excel(name = "订单类型")
    private Long ordertype;

    private String password;

    private Long agent_id;


}
