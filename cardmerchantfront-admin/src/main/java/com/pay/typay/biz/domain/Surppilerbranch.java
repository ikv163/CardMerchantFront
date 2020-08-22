package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 供应商公司信息对象 t_surppilerbranch
 *
 * @author oswald
 * @date 2020-02-07
 */
@Data
public class Surppilerbranch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long companyid;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String branchname;

    /** 状态,1:启用，0：停用，-1：删除 */
    @Excel(name = "状态,1:启用，0：停用，-1：删除")
    private Long status;

}