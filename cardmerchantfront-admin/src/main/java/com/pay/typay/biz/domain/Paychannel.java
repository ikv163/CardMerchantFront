package com.pay.typay.biz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 支付渠道对象 t_paychannel
 *
 * @author ruoyi
 * @date 2020-01-14
 */
@Data
public class Paychannel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String googlecode;
    private String paypoolname;
    private Long paypoolid;
    private Long orderBy;
    /**
     * 支付渠道ID
     */
    private Long paychannelid;
    private Long parentId;

    /**
     * 财务分部ID
     */
    @Excel(name = "财务分部ID")
    private Long supplierbranchid;

    /**
     * 支付渠道名称
     */
    @Excel(name = "支付渠道名称")
    private String paychannelname;
    /**
     * 支付渠道名称
     */
    @Excel(name = "三方商户名称")
    private String thirdMerchantName;

    /**
     * 支付方式,默认1 银行卡转账
     */
    @Excel(name = "支付方式")
    private Long paytype;

    /**
     * 工作模式1:收款,2:付款, 3跨行付款(特用于银行卡), 4收付款，7收款和跨行付款
     */
    @Excel(name = "工作模式")
    private Long worktype;

    /**
     * $column.columnComment
     */
    @Excel(name = "每日交易总额限额")
    private Long sumamountperdaytrans;

    /**
     * 每笔交易最小额度,默认0不限制
     */
    @Excel(name = "每笔交易最小额度")
    private Long minamountpertrans;

    /**
     * 每笔交易最小额度,默认0不限制
     */
    @Excel(name = "每笔交易最大额度")
    private Long maxamountpertrans;

    /**
     * $column.columnComment
     */
    @Excel(name = "风控余额限额")
    private Long riskybalance;

    /**
     * $column.columnComment
     */
    @Excel(name = "余额")
    private Long balance;

    /**
     * 已下发总结金额
     */
    @Excel(name = "已下发总结金额")
    private Long withdrawbalance;

    /**
     * $column.columnComment
     */
    @Excel(name = "已收总额")
    private Long withdrawsummary;

    @Excel(name = "渠道费率Json格式")
    private String depositrate;
    @Excel(name = "渠道费率Json格式")
    private String withdrawrate;

    /**
     * 客户端类型,PC:1,WAP:2,APP:4,其他值建立在基础类型的和，比如5表示PC和APP，6表示WAP和APP
     */
    @Excel(name = "客户端类型")
    private String clienttype;

    /**
     * 1：启用，0:停用，-1:软删除,-2:预启用, -3:限额停用 ,-4：风控金额停用
     */
    @Excel(name = "服务状态")
    private Long status;
    @Excel(name = "服务状态")
    private Long statusfilter;

    /**
     * 同等支付方式下的优先顺序,越小越优先使用,默认为0
     */
    @Excel(name = "优先顺序")
    private Long channellevel;

    /**
     * 银行类型代码冗余字段,固码渠道=GM
     */
    @Excel(name = "银行类型代码")
    private String bankcode;

    /**
     * 渠道商户号
     */
    @Excel(name = "渠道商户号")
    private String channelmerchantid;

    /**
     * 加密Key
     */
    @Excel(name = "加密Key")
    private String thirdmerchantkey;

    /**
     * 加密公共key
     */
    @Excel(name = "加密公共key")
    private String thirdmerchantpublickeys;

    /**
     * 对应 thirdmerchant表
     */
    @Excel(name = "商户id")
    private Long merchantid;

    /**
     * 支付接口地址
     */
    @Excel(name = "支付接口地址")
    private String paymenturl;

    /**
     * 不可用时间段说明, HH:MM-HH:MM时间段格式集合Json格式化后存储
     */
    @Excel(name = "不可用时间段说明")
    private String unavailabletime;

    /**
     * $column.columnComment
     */
    @Excel(name = "版本号")
    private Long version;

    /**
     *  银行卡id
     * */
    private String bankids;

    /**
     * $column.columnComment
     */
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date lastupdatetime;

    /**
     * 是否写入t_thirddeposit表  1：写入   2：不写入
     */
    @Excel(name = "是否写入t_thirddeposit表")
    private Integer writethirdtable;

    /**  下发卡池 */
    private String withdrawBankPoolName;

    /** 充值卡池 */
    private String depositBankPoolName;

    private String userName;

    /** 充值地址 */
    private String depositUrl;
    /** 下发地址 */
    private String withdrawUrl;
    /** 最大保留额度 */
    private String maxBalance;
    /** 最小保留额度 */
    private String minBalance;

    private String remark;

    private String withdrawBalanceOk;
    private String depositAmount;
    private String withdrawAmount;
    private int allOrderCount;
    private int successOrderCount;
    private String successRate;

    /** 第三方商户号 */
    private String merchantName;

    /** 渠道可用开始时间 */
    private String availableTimeStart;
    /** 渠道可用结束时间 */
    private String availableTimeEnd;

    /** 下发充值卡池**/
    public Long withdrawbankpoolid;

    /** 充值充值卡池**/
    private Long depositbankpoolid;

    /** 充值方式**/
    private Integer depositstatus;

    /** 下发方式**/
    private Integer withdrawstatus;

    private Boolean ChannelIsOwn;
    private Boolean BanklIsOwn;

    private List<Bankinchannel> TBankinchannelList;

    private Long ServiceStatus;

    private Integer surplierType = 1;

    private String agentId;
}