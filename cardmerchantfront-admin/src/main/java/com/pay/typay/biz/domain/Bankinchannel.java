package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 银行卡渠道关联表对象 t_bankinchannel
 *
 * @author js-bucky
 * @date 2020-02-12
 */
@Data
public class Bankinchannel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 银行卡id
     */
    private Long bankid;

    /**
     * 渠道id
     */
    private Long channelid;


}
