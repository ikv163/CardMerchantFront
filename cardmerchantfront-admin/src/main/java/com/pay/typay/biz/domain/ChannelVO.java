package com.pay.typay.biz.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @desc: 渠道VO
 * @author： Warren
 * @createtime： 2020/1/16
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
@Data
public class ChannelVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long payChannelId;

    /** 渠道名称 */
    private String payChannelName;

    /** 支付方式 */
    private String payType;

    /** 工作模式 */
    private String workType;

    /** 服务状态 */
    private String status;

    private Long payPoolId;

    /** 所属支付池 */
    private String payPoolName;

    /** 渠道属性 */
    private String channeltype;

    /** 所属人员 */
    private String userName;
}
