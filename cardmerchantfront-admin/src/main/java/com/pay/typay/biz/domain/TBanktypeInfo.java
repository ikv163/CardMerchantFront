package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 银行卡类型对象 t_banktype
 * 
 * @author oswald
 * @date 2020-01-06
 */
@Data
public class TBanktypeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 银行卡类型ID */
    private Long banktypeid;

    /** 银行卡类型名称 */
    @Excel(name = "银行卡类型名称")
    private String banktypename;

    /** 银行卡类型代码 */
    @Excel(name = "银行卡类型代码")
    private String banktypecode;

    /** null */
    @Excel(name = "lastupdatetime", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;

    @Excel(name = "remark")
    private String remark;
}
