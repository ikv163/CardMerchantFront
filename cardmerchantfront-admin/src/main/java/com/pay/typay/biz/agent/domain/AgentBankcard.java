package com.pay.typay.biz.agent.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 代理银行卡对象 t_agentbankcard
 *
 * @author oswald
 * @date 2020-05-14
 */
@Data
public class AgentBankcard extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 银行卡id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long bankid;

    /**
     * 分部id
     */
    private Long supplierbranchid;

    /**
     * 银行卡号码
     */
    @Excel(name = "银行卡号码")
    private String banknum;

    /**
     * 银行卡编码
     */
    private String bankcode;

    /**
     * 银行卡类型代码
     */
    @Excel(name = "银行卡类型代码")
    private String banktype;

    /**
     * 开户地址
     */
    @Excel(name = "开户地址")
    private String address;

    /**
     * 充值地址
     */
    private String depositaddress;

    /**
     * 开户人
     */
    @Excel(name = "开户人")
    private String ownername;

    /**
     * 开户人手机预留号码
     */
    private String ownerphone;

    /**
     * 开户人身份证
     */
    private String owneridentity;

    /**
     * 工作模式1:收款,2:付款
     */
    private Long worktype;

    /**
     * 余额
     */
    private Double balance;

    /**
     * 1：启用，0:停用，-1:软删除,-2:预启用, -3:交易限额停用 ,-4:余额超额停用,-5:交易笔数超额停用,-6:无转入转出停用,-7异常，-8临时停用，100：在线
     */
    private Long status;

    /**
     * 更新时间
     */
    private Date lasttime;

    /**
     * 银行卡index
     */
    private String cardindex;

    /**
     * 修改时间
     */
    private Date time;

    private String createby;

    private String agentId;

    private String password;
}
