package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 银行卡池对象 t_bankcardpool
 *
 * @author Warren
 * @date 2020-01-05
 */
@Data
public class TBankcardpool extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 银行卡池ID
     */
    private Long bankpooid;

    /**
     * 财务分部ID
     */
    @Excel(name = "财务分部ID")
    private Long supplierbranchid;

    /**
     * 银行卡池名称
     */
    @NotBlank(message = "银行卡池名称不能为空")
    @Excel(name = "银行卡池名称")
    private String bankpoolname;
    private String bankcode;
    private String sumdepositamount;
    private String sumwithdrawamount;

    private String sumwithdrawtimes;
    private String sumdeposittimes;
    private String sumbalance;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "工作类型不能为空")
    @Excel(name = "工作类型")
    private Long worktype;

    /**
     * 1：启用，0:停用，-1:软删除
     */
    @NotBlank(message = "不能为空")
    @Excel(name = "1：启用，0:停用，-1:软删除")
    private Long status;
    @Excel(name = "服务状态")
    private Long filterstatus;
    /**
     * 0入款卡池 1出款卡池 2下发卡池 3其他卡池 4中转卡池
     */
    @Excel(name = "0入款卡池 1出款卡池 2下发卡池 3其他卡池 4中转卡池")
    @NotBlank(message = "不能为空")
    private Long roletype;

    /**
     * 向本池转入资金的卡池
     */
    @NotBlank(message = "不能为空")
    @Excel(name = "向本池转入资金的卡池")
    private Long inputpoolid;

    /**
     * 从本池转出资金的卡池
     */
    @NotBlank(message = "不能为空")
    @Excel(name = "从本池转出资金的卡池")
    private String outputpoolid;

    /**
     * $column.columnComment
     */
    @Excel(name = "从本池转出资金的卡池", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;

    @Excel(name = "createtime", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;

    @Excel(name = "银行卡集合")
    private String groupoolname;

    private String bankcardids;

    private Integer  bindnumm;

}
