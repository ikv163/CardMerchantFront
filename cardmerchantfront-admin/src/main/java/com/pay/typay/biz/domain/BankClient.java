package com.pay.typay.biz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 银行卡客户端对象 t_bankclient
 * 
 * @author js-bucky
 * @date 2020-01-08
 */
@Data
public class BankClient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户端编号 */
    private Long clientId;

    /** supplierbranchid */
    private Long supplierBranchID;

    /** 银行类型 */
    private String bankType;

    /** 客户端名称 */
    @Excel(name = "客户端名称")
    private String clientName;

    private String bankcode;

    private Integer bankcardStatus;
    /** 服务状态 */
    @Excel(name = "服务状态")
    private Integer status;

    private Long filterstatus;
    /** 工作模式 */
    @Excel(name = "工作模式")
    private Long workType;

    /** 识别码 */
    @Excel(name = "识别码")
    private String identity;

    /** 银行卡简码 */
    @Excel(name = "银行卡简码")
    private String bankAccount;

    /** 心跳 */
    private Integer heartbeat;

    /** 当前卡余额 */
    private BigDecimal balance;
    /**
     * 备注
     */
    private String remark;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String realremark;

    /** LogoutMsg */
    private String logoutMsg;

    /** 最后更新时间 */
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date lastUpDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date createtime;
    private Long createby;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date updatetime;
    private Long updateby;

    /** 卡类型（0为天下渠道，1为卡商渠道，默认0） */
    private Integer surplierType = 1;

    private List<String> supplierBranchIdGroupList;


}
