package com.pay.typay.biz.orders.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import com.pay.typay.common.utils.DateUtils;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户提款订单对象 t_agentwithdraworder
 * 
 * @author warren
 * @date 2020-05-18
 */
@Data
public class Userwithdrew extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 索引 */
    private String orderindex;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderid;
    @Excel(name = "代理账号")
    private String userName;

    /** 分部id */
    private Long supplierbranchid;

    /** 充值方式,1:网银转账2:快捷支付3:银联支付4:微信5:支付宝6:qq钱包7:京东支付8:云闪付 */
    private Integer paytype;
    /** 银行卡简码 */
    @Excel(name = "卡简码")
    private String bankcode;
    @Excel(name = "附言")
    private String remark;

    /** 提款金额 */
    @Excel(name = "申请金额")
    private BigDecimal payamount;

    /** 实现到帐金额 */
    @Excel(name = "充值金额")
    private BigDecimal paidamount;

    /** 渠道手续费 */
    private BigDecimal paidchannelfee;

    /** 财务分支渠道费用 */
    private BigDecimal supplierpaidchannelfee;

    /** 商户用于接收订单通知的地址 */
    private String notifyurl;

    /** 订单提交后立即返回的商户页面地址 */
    private String returnurl;

    /** md5签名 */
    private String md5orderid;

    /** 处理该订单的充值渠道 */
    private Long paychannelid;

    /** 处理该订单的银行卡 */
    private String banknum;

    /** 处理该订单的银行卡简称 */
    private String bankaccount;
    /** 代理账号 */
    @Excel(name = "用户账号")
    private String loginame;
    @Excel(name = "利润")
    private BigDecimal profit;

    /** 订单状态0:创建，默认值;1：处理中;2:清算中;3:正常支付关闭;4：手工确认订单已完成关闭; 5:超时,6:超时关闭；7:取消关闭 */
    @Excel(name = "状态")
    private Integer orderstatus;

    /** 订单状态最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date orderpaidstatuschangetime;

    /** 订单通知状态 */
    private Integer ordernotifystatus;

    /** 订单通知状态最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date ordernotifytime;

    /** 名称 */
    private String name;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date lasttime;

    /**订单状态 0、出款  1、中转 2、额度转换*/
    private Integer ordertype;

    private long createby;

    private String agentName;

    private String memberaccount;

    private String operator;

    private String merchantorderid;

    private Double ratio;

    private String beginTime;

    private String endTime;

    private List<String> supplierBranchIdGroupList;
}
