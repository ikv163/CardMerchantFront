package com.pay.typay.biz.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.TreeEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 财务分部支付池对象 t_paypool
 * 
 * @author Warren
 * @date 2020-01-16
 */
@Data
public class Paypool extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付池ID */
    private Long paypoolid;

    /** 财务分部ID */
    @Excel(name = "财务分部ID")
    private Long supplierbranchid;

    /** 支付池名称 */
    @Excel(name = "支付池名称")
    private String paypoolname;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 最后更改时间 */
    @Excel(name = "最后更改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastupdatetime;

    /** 渠道集合 */
    private List<ChannelVO> childen;

    private Long payChannelId;

    /** 渠道名称 */
    private String payChannelName;

    /** 支付方式 */
    private String payType;

    /** 工作模式 */
    private String workType;

    /** 服务状态 */
    private String channelStatus;

    /** 渠道属性 */
    private String channeltype;

    /** 所属人员 */
    private String userName;

    /** 父节点ID */
    private Long parentId;

    /** 父节点名称 */
    private String parentName;
}
