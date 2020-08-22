package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 支付池支付渠道配置对象 t_paypoolchannel
 *
 * @author Warren
 * @date 2020-01-16
 */
@Data
public class Paypoolchannel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "paychannelname")
    private String paychannelname;

    /** 支付池ID */
    private Long paypoolid;
    private String mutiplepaychannelid;
    /** 支付渠道ID */
    private Long paychannelid;

    /** 渠道手续费 */
    @Excel(name = "渠道手续费",cellType = Excel.ColumnType.DOUBLE)
    private String channelrate;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 最后修改时间 */
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;


}