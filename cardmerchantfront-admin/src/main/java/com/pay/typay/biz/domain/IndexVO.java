package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @desc: 首页VO
 * @author： jsbucky
 * @createtime： 2020/2/2
 * @modify by： Warren
 * @throws:
 */
@Data
public class IndexVO  extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private BigDecimal succeedpayamount;
    private Long succeedcount;
    private Long failcount;
    private Long totalcount;

    private Long supplierbranchid;
    private Date querytime;
    private Date queryorderstatus;


}
