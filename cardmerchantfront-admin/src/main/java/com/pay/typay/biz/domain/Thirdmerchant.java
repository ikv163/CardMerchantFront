package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 三方商户对象 t_thirdmerchant
 * 
 * @author Warren
 * @date 2020-01-26
 */
@Data
public class Thirdmerchant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 三方商户ID */
    private Long merchantid;

    /** 财务分部ID */
    @Excel(name = "财务分部ID")
    private Long supplierbranchid;

    /** 三方名称 */
    @Excel(name = "三方名称")
    private String merchantname;

    /** 三方代码 */
    @Excel(name = "三方代码")
    private String merchantcode;

    /** 下发银行卡池 */
    @Excel(name = "下发银行卡池")
    private Long withdrawbankpoolid;

    /** 充值银行卡池 */
    @Excel(name = "充值银行卡池")
    private Long depositbankpoolid;

    /** 充值地址 */
    @Excel(name = "充值地址")
    private String depositurl;

    /** 下发地址 */
    @Excel(name = "下发地址")
    private String withdrawurl;

    /** 1：自动充值，0:手工充值，-1：系统外充值 */
    @Excel(name = "1：自动充值，0:手工充值，-1：系统外充值")
    private Long depositstatus;

    /** 1：自动下发，0:手工下发，-1：系统外下发 */
    @Excel(name = "1：自动下发，0:手工下发，-1：系统外下发")
    private Long withdrawstatus;

    /** 1：启用，0:停用，-1:软删除 */
    @Excel(name = "1：启用，0:停用，-1:软删除")
    private Long status;

    /** 最后更新时间 */
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;


}
