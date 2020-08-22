package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @ClassName Banktag
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/14 上午11:35
 **/
@Data
public class Banktag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签ID */
    private Integer tagid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long supplierbranchid;

    /** 标签名称 */
    @Excel(name = "标签名称")
    private String tagname;


}