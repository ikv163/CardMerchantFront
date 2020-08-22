package com.pay.typay.common.core.domain;

import lombok.Data;
//import sun.nio.cs.ext.Big5_HKSCS;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 统计计Entity基类
 *
 * @author js-warren
 */
@Data
public class SubtotalEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 代理个数
     */
    private Integer agentNum = 0;
    /**
     * 平均成功率
     */
    private Double avgSuccessRate = 0.0;
    /**
     * 交易笔数
     */
    private Integer transNum = 0;

    /**
     * 账户金额
     */
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * 利润
     */
    private BigDecimal profit = BigDecimal.ZERO;

    /**
     * 已提金额
     */
    private BigDecimal withdrawAmount = BigDecimal.ZERO;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance = BigDecimal.ZERO;

    /**
     * 冻结余额
     */
    private BigDecimal  fronzenBalance = BigDecimal.ZERO;

}
