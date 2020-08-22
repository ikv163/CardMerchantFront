package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName IndexTaytypeRankingVo
 * @Description
 * @Author JS-oswald
 * @Date 2020/2/24 下午5:17
 **/
@Data
public class IndexTaytypeRankingVo  extends BaseEntity {
    private String PayType;
    private String TIME;
    private double gcount;
    private double percent;
    private double successRate;

    private Long supplierbranchid;
    private String querytime;
    private Date queryorderstatus;

}
