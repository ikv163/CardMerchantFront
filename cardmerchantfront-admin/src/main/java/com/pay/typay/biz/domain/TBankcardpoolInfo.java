package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName TBankcardpoolInfo
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/7 上午11:05
 **/
@Data
public class TBankcardpoolInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 银行卡池ID */
    private Long bankpooid;

    /** 财务分部ID */
    @Excel(name = "财务分部ID")
    private Long supplierbranchid;

    /** 银行卡池名称 */
    @Excel(name = "银行卡池名称")
    private String bankpoolname;

    /** null */
    @Excel(name = "null")
    private Long worktype;

    /** 1：启用，0:停用，-1:软删除 */
    @Excel(name = "1：启用，0:停用，-1:软删除")
    private Long status;

    /** 0入款卡池 1出款卡池 2下发卡池 3其他卡池 4中转卡池 */
    @Excel(name = "0入款卡池 1出款卡池 2下发卡池 3其他卡池 4中转卡池")
    private Long roletype;

    /** 向本池转入资金的卡池 */
    @Excel(name = "向本池转入资金的卡池")
    private Long inputpoolid;

    /** 从本池转出资金的卡池 */
    @Excel(name = "从本池转出资金的卡池")
    private Long outputpoolid;

    /** null */
    @Excel(name = "null", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;
}
