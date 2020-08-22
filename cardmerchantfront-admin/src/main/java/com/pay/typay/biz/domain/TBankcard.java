package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 银行卡对象 t_bankcard
 * 
 * @author Warren
 * @date 2020-01-05
 */
@Data
public class TBankcard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long transcount;
    /** 银行卡ID */
    private Long bankid;
    private Long applymoneytimes;
    /** 分部ID */
    private Long supplierbranchid;

    /** 银行卡号码 */
    private String banknum;

    /** 银行卡网银登录账号 */
    private String loginaccount;

    /** 银行卡编码 */
    private String bankcode;

    /** 所属银行卡池 */
    private Long poolid;

    /**
     * 多选银行卡池
     */
    private String poolids;

    /**
     * 多选渠道
     */
    private String paychannelids;


    /**
     * 状态类型多选
     */
    private  String statuses;

    /**
     * 支付类型多选
     */
    private  String paytypes;

    /** 查看员 */
    private Long auth;

    /** 银行卡类型代码 */
    private String banktype;

    /** 开户地址 */
    private String address;

    /** 充值地址 */
    private String depositaddress;

    /** 开户人 */
    private String ownername;

    /** 开户人手机预留号码 */
    private String ownerphone;

    /** 开户人身份证 */
    private String owneridentity;

    /** 工作模式1:收款,2:付款 */
    private Long worktype;

    private String clentWorkType;

    /** $column.columnComment */
    private String balance;

    /** $column.columnComment */
    private String maxbalance;

    /** $column.columnComment */
    private String minbalance;

    /** $column.columnComment */
    private String initbalance;

    /** 日限总额 */
    private String sumamountperdaytrans;

    /** 日笔数限额 */
    private String sumtimesperdaytrans;

    /** $column.columnComment */
    private String minamountpertrans;

    /** $column.columnComment */
    private String maxamountpertrans;

    /** $column.columnComment */
    private String alisumtimesperdaytrans;

    /** $column.columnComment */
    private String alisumamountperdaytrans;

    /** $column.columnComment */
    private String aliminamountpertrans;

    /** $column.columnComment */
    private String alimaxamountpertrans;

    /** 1：启用，0:停用，-1:软删除,-2:预启用, -3:交易限额停用 ,-4:余额超额停用,-5:交易笔数超额停用,-6:无转入转出停用,-7异常，-8临时停用，100：在线 */
    private Long status;

    /** 人工备注 */
    private String comments;

    /** $column.columnComment */
    private Long version;

    /** U盾登录密码 */
    private String uloginpwd;

    /** U盾密码 */
    private String ukeypwd;

    /** 登录密码，加密 */
    private String loginpwd;

    /** 支付密码，加密 */
    private String paypwd;

    /** 子账号 */
    private String ucode;

    /** 银行卡标签 */
    private Long tagid;

    /** 手机ID */
    private Long phoneid;

    /** 最后一次登录的自动机 */
    private String lastloginclientname;

    /** $column.columnComment */
    private Date lastupdatetime;
    private Date bslastupdatetime;

    /** 银行卡Index */
    private String cardindex;

    /** 银行卡池名称 */
    private String bankPoolName;

    /** redis 缓存余额 */
    private String cacheAmount;

    /**
     * 订单总数
     */
    private int allOrderCount;
    /**
     * 成功订单总数
     */
    private int successOrderCount;
    /**
     * 其他订单数
     */
    private int otherCount;
    /**
     * 成功率
     */
    private int successRate;

    /**
     * 今日收款额度
     */
    private String depositAmount;
    /**
     * 今日出款额度
     */
    private String withdrawAmount;
    /**
     * 今日收款次数
     */
    private String depositTimes;
    /**
     * 今日出款次数
     */
    private String withdrawTimes;

    private String clientName;

    private String clientStatus;

    private String historyDepositAmount;


    /**
     * 备注
     */
    private String remark;
    private String OfflineCard;

    //多类型查询
    private List<String> types;

    //银行卡池
    private  String BankPoolName;

    private Long Num;

    private Integer PayType;

    private BigDecimal AliDepositAmount;

    private Integer AliDepositTimes;

    private  String PayChannelName;
}
