package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 卡池资金监控对象 t_bankcard
 * 
 * @author oswald
 * @date 2020-01-21
 */
@Data
public class MonitorsVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 银行卡ID */
    private Long bankid;

    /** 银行卡编码 */
    private String bankcode;

    /** 工作模式1:收款,2:付款 */
    private Long worktype;

    private BigDecimal balance;

    private BigDecimal maxbalance;

    private Long merchantid;

    private Long paypoolid;

    private Integer userlevel;

    private String creditlevel;
    private String merchantname;
}
