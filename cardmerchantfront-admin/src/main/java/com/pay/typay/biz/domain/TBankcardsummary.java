package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行卡记录对象 t_bankcardsummary
 * 
 * @author Warren
 * @date 2020-01-07
 */
@Data
public class TBankcardsummary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 银行卡ID */
    private Long bankid;

    /** 订单金额 */
    @Excel(name = "订单金额",cellType = Excel.ColumnType.DOUBLE)
    private BigDecimal depositamount;

    /** 历史存款金额 */
    @Excel(name = "历史存款金额")
    private BigDecimal historydepositamount;

    /** 取款金额 */
    @Excel(name = "取款金额")
    private BigDecimal withdrawamount;

    /** 创建时间 */
    private Date calendar;

    /** 最后更新时间 */
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;

    /** 存款次数 */
    @Excel(name = "存款次数")
    private Integer deposittimes;

    /** 取款次数 */
    @Excel(name = "取款次数")
    private Integer withdrawtimes;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long version;

    /** 支付宝日次数 */
    @Excel(name = "支付宝日次数")
    private Integer alideposittimes;

    /** 支付宝存款金额 */
    @Excel(name = "支付宝存款金额")
    private BigDecimal alidepositamount;

}
