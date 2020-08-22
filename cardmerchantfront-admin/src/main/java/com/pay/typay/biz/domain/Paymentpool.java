package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 财务分部支付池对象 t_paypool
 * 
 * @author oswald
 * @date 2020-01-19
 */
@Data
public class Paymentpool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付池ID */
    private Long paypoolid;

    /** 财务分部ID */
    @Excel(name = "财务分部ID")
    private Long supplierbranchid;

    /** 支付池名称 */
    @Excel(name = "支付池名称")
    private String paypoolname;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 最后更改时间 */
    @Excel(name = "最后更改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;

    private String paychannelids;
    private Long createby;

}
