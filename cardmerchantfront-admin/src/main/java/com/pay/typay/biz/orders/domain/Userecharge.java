package com.pay.typay.biz.orders.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户充值订单对象 t_agentdepositorder
 * 
 * @author warren
 * @date 2020-05-18
 */
@Data
public class Userecharge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单索引 */
    private String orderindex;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderid;

    /** 分部ID */
    //@Excel(name = "分部ID",type = Ty)
    private Long supplierbranchid;

    /** 商户编号 */
//    @Excel(name = "商户编号")
    private Long merchantid;

    /** 商户订单号最长64位 */
//    @Excel(name = "商户订单号最长64位")
    private String merchantorderid;

    /** 商户会员等级 */
//    @Excel(name = "商户会员等级")
    private Long userlevel;

    /** 商户会员唯一ID */
//    @Excel(name = "商户会员唯一ID")
    private String userid;

    /** $column.columnComment */
//    @Excel(name = "商户会员唯一ID", width = 30, dateFormat = "yyyy-MM-dd")
    private Date merchantordertime;

    /** 充值方式,1:网银转账2:快捷支付3:银联支付4:微信5:支付宝6:QQ钱包7:京东支付8:云闪付 */
//    @Excel(name = "充值方式,1:网银转账2:快捷支付3:银联支付4:微信5:支付宝6:QQ钱包7:京东支付8:云闪付")
    private Long paytype;

    /** 客户端类型, 1：PC，2:WAP，4:APP */
//    @Excel(name = "客户端类型, 1：PC，2:WAP，4:APP")
    private Long clienttype;

    /** 充值金额 */
    @Excel(name = "充值金额")
    private Double payamount;

    /** 实际到账金额 */
    @Excel(name = "实际到账金额")
    private BigDecimal paidamount;

    /** 商户需付手续费 */
//    @Excel(name = "商户需付手续费")
    private Double paidchannelfee;

    /** 渠道提供方手续费，比如需向三方通道付手续费，比如平均银行卡磨损费 */
//    @Excel(name = "渠道提供方手续费，比如需向三方通道付手续费，比如平均银行卡磨损费")
    private Double supplierpaidchannelfee;

    /** 商户用于接收订单通知的地址 */
//    @Excel(name = "商户用于接收订单通知的地址")
    private String notifyurl;

    /** 订单提交后立即返回的商户页面地址 */
//    @Excel(name = "订单提交后立即返回的商户页面地址")
    private String returnurl;

    /** $column.columnComment */
//    @Excel(name = "订单提交后立即返回的商户页面地址")
    private String merchantremark;

    /** 平台提交给充值渠道的订单号, GUID32位 */
//    @Excel(name = "平台提交给充值渠道的订单号, GUID32位")
    private String channelorderid;

    /** 银行类型代码 */
//    @Excel(name = "银行类型代码")
    private String bankcode;

    /** MD5签名 */
//    @Excel(name = "MD5签名")
    private String md5orderid;

    /** 处理该订单的充值渠道 */
//    @Excel(name = "处理该订单的充值渠道")
    private Long paychannelid;

    /** 处理该订单的银行卡 */
//    @Excel(name = "处理该订单的银行卡")
    private String banknum;

    /** 处理该订单的银行卡简称 */
    @Excel(name = "银行卡简称")
    private String bankaccount;

    /** 订单状态0:创建，默认值;1：处理中;2:清算中;3:正常支付关闭;4：手工确认订单已完成关闭; 5:超时,6:超时关闭；7:取消关闭 */
    @Excel(name = "订单状态")
    private Long orderstatus;

    /** 订单状态最后修改时间 */
    @Excel(name = "更新时间")
    private String orderpaidstatuschangetime;

    /** 通知状态；0:未成功通知；1：已成功通知;2:通知超时异常 */
//    @Excel(name = "通知状态；0:未成功通知；1：已成功通知;2:通知超时异常")
    private Long ordernotifystatus;

    /** 订单通知状态最后修改时间 */
//    @Excel(name = "订单通知状态最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ordernotifytime;

    /** $column.columnComment */
//    @Excel(name = "订单通知状态最后修改时间")
    private String name;

    /** 用于标注是否被自动付款取走 */
//    @Excel(name = "用于标注是否被自动付款取走")
    private String readmark;

    /** 关闭原因 */
//    @Excel(name = "关闭原因")
    private String closereason;

    /** ip */
//    @Excel(name = "ip")
    private String userip;

    /** 操作员 */
//    @Excel(name = "操作员")
    private Long operator;

    /** 会员账号 */
    //@Excel(name = "会员账号")
    private String memberaccount;

    /** 用户层级，根据用户层级等级分配不同支付渠道;多个层级，使用‘,’分割，比如支持5_1,5_2层级，则是5_1,5_2 */
//    @Excel(name = "用户层级，根据用户层级等级分配不同支付渠道;多个层级，使用‘,’分割，比如支持5_1,5_2层级，则是5_1,5_2")
    private String usercreditlevel;

    private long createby;
    @Excel(name = "代理账号")
    private String agentName;
    @Excel(name = "利润")
    private Double profit;

    private Double ratio;

    private String createtime;

    private String beginTime;

    private String endTime;
    private List<String> supplierBranchIdGroupList;
}
