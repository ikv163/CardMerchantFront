package com.pay.typay.biz.reports.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 统计卡分销充值和提款的详细数据
 * 
 * @author warren
 * @date 2020-05-18
 */
@Data
public class TransDetails extends BaseEntity
{
    //工作模式
    private String workType;

    //交易笔数
    private BigDecimal transCount;

    //成功笔数
    private BigDecimal successTransCount;

    //平均金额
    private double avgAmount;

    //交易总额
    private BigDecimal transAmount;

    //成功率
    private double successRatio;


}
