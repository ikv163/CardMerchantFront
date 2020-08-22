package com.pay.typay.biz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pay.typay.biz.messages.ConstantsSelectUIToExcel;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 银行卡对象 t_bankcard
 *
 * @author oswald
 * @date 2020-01-06
 */
@Data
public class TBankcardcenter extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** redis 缓存余额 */
    private String cacheAmount;
    private String OfflineCard;
    /**
     * 成功订单总数
     */
    private int successOrderCount;
    /**
     * 订单总数
     */
    private int allOrderCount;
    /**
     * 成功率
     */
    private int successRate;
    /**
     * 其他订单数
     */
    private int otherCount;
    private String createbyname;
    private Long createby;
    private Long updateby;
    private String updatebyname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updatetime;

    private String googlecode;
    /**
     * 银行卡ID
     */
    private Long bankid;
    private String bankids;
    private Long parentId;

    /**
     * 银行卡编码
     */
    @Excel(name = "银行卡简码")
    private String bankcode;

    /**
     * 银行卡号码
     */
    @Excel(name = "银行卡号")
    private String banknum;
    /**
     * 子账号
     */
    @Excel(name = "子账号")
    private String ucode;

    /**
     * 多选银行卡池
     */
    private String poolids;

    /**
     * 状态类型多选
     */
    private  String statuses;

    /**
     * 支付类型多选
     */
    private  String paytypes;

    /**
     * 工作类型多选
     */
    private String worktypes;

    /**
     * 银行类型
     */
    private String banktypes;


    /**
     * 工作模式1:收款,2:付款
     */
    @Excel(name = "工作模式",readConverterExp = ConstantsSelectUIToExcel.WorkType)
    private Long worktype;

    /**
     * 所属银行卡池
     */
    private Long poolid;
    @Excel(name = "银行卡池")
    private String bankPoolName;
    /**
     * null
     */
    @Excel(name = "余额")
    private Double balance;


    /**
     * 开户人
     */
    @Excel(name = "开户人")
    private String ownername;
    /**
     * 服务状态
     */
    @Excel(name = "服务状态",readConverterExp = ConstantsSelectUIToExcel.Statussearch)
    private Long status;
    /**
     * 查看员
     */
    private Long auth;
    private Long[] authes;
    private String authestr;

//    @Excel(name = "所属角色")
    private String authname;

    @Excel(name = "保留额度")
    private Double initbalance;
    @Excel(name = "最大保留额度")
    private Double maxbalance;

    @Excel(name = "最小保留额度")
    private Double minbalance;
    @Excel(name = "日总限额")
    private Double sumamountperdaytrans;
    @Excel(name = "日笔数限定")
    private Double sumtimesperdaytrans;

    @Excel(name = "每笔最大")
    private Double maxamountpertrans;

    @Excel(name = "每笔最小")
    private Double minamountpertrans;

    @Excel(name = "支付宝日总限额")
    private Double alisumamountperdaytrans;

    @Excel(name = "支付宝日笔数限制")
    private Double alisumtimesperdaytrans;

    @Excel(name = "支付宝每笔最大")
    private Double alimaxamountpertrans;

    @Excel(name = "支付宝每笔最小")
    private Double aliminamountpertrans;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /**
     * 分部ID
     */
    private Long supplierbranchid;

   // @Excel(name = "已充总额")
    private BigDecimal depositamount;
    private BigDecimal withdrawamount;


    /**
     * 登录账号
     */
    private String loginaccount;

    /**
     * 银行卡类型代码
     */
    private String banktype;

    /**
     * 开户地址
     */
    private String address;

    /**
     * 充值地址
     */
    private String depositaddress;


    /**
     * 开户人手机预留号码
     */
    private String ownerphone;

    /**
     * 开户人身份证
     */
    private String owneridentity;
    /**
     * 服务状态
     */
    private Long filterstatus;

    /**
     * 人工备注
     */
    private String comments;

    /**
     *
     */
    private Long version;

    /**
     * U盾登录密码
     */

    private String uloginpwd;

    /**
     * U盾密码
     */

    private String ukeypwd;

    /**
     * 登录密码，加密
     */

    private String loginpwd;

    /**
     * 支付密码，加密
     */

    private String paypwd;

    /**
     * 银行卡标签
     */
    private Long tagid;

    /**
     * 手机ID
     */
    private Long phoneid;

    /**
     * 最后一次登录的自动机
     */
    private String lastloginclientname;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date lastupdatetime;


    /**
     * 银行卡Index
     */
    private String cardindex;


    private Boolean bankIsOwn;


    private long clientID;

    private  long channelid;

    /**
     * 是否搜索未分配的卡
     */
    private String unassigned;

    private Long roletype;

    private BigDecimal historyDepositAmount;
    //1.为卡商的卡,0为天下卡
    private Long surplier_type = 1L;

    private String agentId;

    private List<String> supplierBranchIdGroupList;

    private int beforWorkType;
}
