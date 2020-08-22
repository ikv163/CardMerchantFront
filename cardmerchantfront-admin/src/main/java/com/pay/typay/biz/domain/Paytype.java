package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 支付方式对象 t_paytype
 * 
 * @author Warren
 * @date 2020-01-20
 */
@Data
public class Paytype extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付方式ID */
    private Long paytypeid;

    /** 支付方式名称 */
    @Excel(name = "支付方式名称")
    private String paytypename;

    /** 支付方式代码 */
    @Excel(name = "支付方式代码")
    private String paycode;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 最后修改时间 */
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;


}
