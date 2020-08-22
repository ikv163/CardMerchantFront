package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 卡池资金监控对象 t_bankcard
 * 
 * @author oswald
 * @date 2020-01-21
 */
@Data
public class Monitorsfundspool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 银行卡ID */
    private Long bankid;

    /** 分部ID */
    @Excel(name = "分部ID")
    private Long supplierbranchid;

    /** 银行卡号码 */
    @Excel(name = "银行卡号码")
    private String banknum;

    /** 登录账号 */
    @Excel(name = "登录账号")
    private String loginaccount;

    /** 银行卡编码 */
    @Excel(name = "银行卡编码")
    private String bankcode;

    /** 所属银行卡池 */
    @Excel(name = "所属银行卡池")
    private Long poolid;

    /** 查看员 */
    @Excel(name = "查看员")
    private Long auth;

    /** 银行卡类型代码 */
    @Excel(name = "银行卡类型代码")
    private String banktype;

    /** 开户地址 */
    @Excel(name = "开户地址")
    private String address;

    /** 充值地址 */
    @Excel(name = "充值地址")
    private String depositaddress;

    /** 开户人 */
    @Excel(name = "开户人")
    private String ownername;

    /** 开户人手机预留号码 */
    @Excel(name = "开户人手机预留号码")
    private String ownerphone;

    /** 开户人身份证 */
    @Excel(name = "开户人身份证")
    private String owneridentity;

    /** 工作模式1:收款,2:付款 */
    @Excel(name = "工作模式1:收款,2:付款")
    private Long worktype;

    /** null */
    @Excel(name = "null")
    private BigDecimal balance;

    /** null */
    @Excel(name = "null")
    private BigDecimal maxbalance;

    /** null */
    @Excel(name = "null")
    private BigDecimal minbalance;

    /** null */
    @Excel(name = "null")
    private BigDecimal initbalance;

    /** null */
    @Excel(name = "null")
    private BigDecimal sumamountperdaytrans;

    /** null */
    @Excel(name = "null")
    private BigDecimal sumtimesperdaytrans;

    /** null */
    @Excel(name = "null")
    private BigDecimal minamountpertrans;

    /** null */
    @Excel(name = "null")
    private BigDecimal maxamountpertrans;

    /** null */
    @Excel(name = "null")
    private BigDecimal alisumtimesperdaytrans;

    /** null */
    @Excel(name = "null")
    private BigDecimal alisumamountperdaytrans;

    /** null */
    @Excel(name = "null")
    private BigDecimal aliminamountpertrans;

    /** null */
    @Excel(name = "null")
    private BigDecimal alimaxamountpertrans;

    /** 服务状态 */
    @Excel(name = "服务状态")
    private Long status;

    /** 人工备注 */
    @Excel(name = "人工备注")
    private String comments;

    /**  */
    @Excel(name = "")
    private Long version;

    /** U盾登录密码 */
    @Excel(name = "U盾登录密码")
    private String uloginpwd;

    /** U盾密码 */
    @Excel(name = "U盾密码")
    private String ukeypwd;

    /** 登录密码，加密 */
    @Excel(name = "登录密码，加密")
    private String loginpwd;

    /** 支付密码，加密 */
    @Excel(name = "支付密码，加密")
    private String paypwd;

    /** 子账号 */
    @Excel(name = "子账号")
    private String ucode;

    /** 银行卡标签 */
    @Excel(name = "银行卡标签")
    private Long tagid;

    /** 手机ID */
    @Excel(name = "手机ID")
    private Long phoneid;

    /** 最后一次登录的自动机 */
    @Excel(name = "最后一次登录的自动机")
    private String lastloginclientname;

    /**  */
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;

    /** 银行卡Index */
    @Excel(name = "银行卡Index")
    private String cardindex;

    private String merchantname;
}
