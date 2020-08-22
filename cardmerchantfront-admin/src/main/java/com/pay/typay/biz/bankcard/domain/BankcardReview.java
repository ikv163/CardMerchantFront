package com.pay.typay.biz.bankcard.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 代理银行卡审核对象 t_agent_bankcard_review
 * 
 * @author ruoyi
 * @date 2020-07-19
 */
@Data
public class BankcardReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 代理id */
    @Excel(name = "代理id")
    private Long agentId;

    /** 财务分支 */
    @Excel(name = "财务分支")
    private Long supplierbranchid;

    /** 审核状态 0:申请中，1:通过，2:拒绝 */
    @Excel(name = "0=申请中,1=通过,2=拒绝")
    private Integer reviewStatus;

    /** 申请人id */
    @Excel(name = "申请人id")
    private Long applicantId;

    /** 申请人名称 */
    @Excel(name = "申请人名称")
    private String applicantName;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long reviewerId;

    /** 审核人名称 */
    @Excel(name = "审核人名称")
    private String reviewer;

    /** 1:收款,2:出款,3:中转,4:下发,5:手动出款,6:风云出款,7:风云入款,8:结汇, 9:第三方,10:第四方,11:技术支持,12:U盾 */
    @Excel(readConverterExp = "1=收款,2=出款,3=中转,4=下发,5=手动出款,6=风云出款,7=风云入款,8=结汇, 9=第三方,10=第四方,11=技术支持,12=U盾")
    private Integer beforWorktype;

    /** 1:收款,2:出款,3:中转,4:下发,5:手动出款,6:风云出款,7:风云入款,8:结汇, 9:第三方,10:第四方,11:技术支持,12:U盾 */
    @Excel(readConverterExp = "1=收款,2=出款,3=中转,4=下发,5=手动出款,6=风云出款,7=风云入款,8=结汇, 9=第三方,10=第四方,11=技术支持,12=U盾")
    private Integer afterWorktype;

    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;

    /** 银行卡id */
    @Excel(name = "银行卡id")
    private Long bankcardId;

    private List<String> supplierBranchIdGroupList;

    private String agentName;

    private String bankAcount;

}
