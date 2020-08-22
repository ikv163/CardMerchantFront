package com.pay.typay.biz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pay.typay.biz.messages.ConstantsSelectUIToExcel;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 银行卡日交易报表对象 t_bankcardsummary
 * 
 * @author oswald
 * @date 2020-01-09
 */
@Data
public class BankcardDaytradeReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "银行卡简码")
    private String bankcode;


    @Excel(name = "工作模式", readConverterExp = ConstantsSelectUIToExcel.WorkType)
    private String worktype;

    /** 订单金额 */
    @Excel(name = "收款总额",cellType = Excel.ColumnType.DOUBLE)
    private Double depositamount;
    /** 存款次数 */
    @Excel(name = "收款次数")
    private Integer deposittimes;
    /** 取款金额 */
    @Excel(name = "出款总额")
    private Double withdrawamount;
    /** 取款次数 */
    @Excel(name = "出款次数")
    private Integer withdrawtimes;
    @Excel(name = "总次数")
    private String counttime;

    /** 创建时间 */
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date calendar;




    private Long supplierbranchid;

    private String sumamountperdaytrans;


    private String banktype;


    private String banknum;

    /** 银行卡ID */
    private Long bankid;

    /** 历史存款金额 */
    private Double historydepositamount;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date lastupdatetime;


    /** 版本号 */
    private Long version;

    /** 支付宝日次数 */
    private Integer alideposittimes;

    /** 支付宝存款金额 */
    private Double alidepositamount;
}
