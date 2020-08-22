package com.pay.typay.biz.domain;

import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 服务地址配置表
 * </p>
 *
 */
@Data
public class TServiceAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long ID;
    /**
     * 服务关键字
     */
    private String ServiceKey;

    /**
     * 服务名称
     */
    private String ServiceName;

    /**
     * 服务地址
     */
    private String ServiceAddress;

    /**
     * 服务状态，1：启用，0：停用
     */
    private Integer Status;

    /**
     * 创建时间
     */
    private Date CreateTime;

    /**
     * 更新时间
     */
    private Date UpdateTime;


}
