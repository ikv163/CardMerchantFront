package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @ClassName Merchant
 * @Description
 * @Author JS-bucky
 **/
@Data
public class Admin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签ID */
    private String id;

    /** 标签名称 */
    private String username;


}