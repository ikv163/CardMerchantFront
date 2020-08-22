package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @ClassName Merchant
 * @Description
 * @Author JS-bucky
 **/
@Data
public class Merchant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签ID */
    private String merchantid;

    /** 标签名称 */
    private String merchantname;

    /** 新银行卡资金监控专用 */
    private String merchantids;

    private Long supplierbranchid;
}