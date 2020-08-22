package com.pay.typay.biz.agent.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 卡商代理对象 t_bankcard_agent
 *
 * @author oswald
 * @date 2020-05-13
 */
@Data
public class Agentcenter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 代理ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 财务分支
     */
    @Excel(name = "财务分支")
    private Long supplierBranchId;

    /**
     * 代理名称
     */
    @Excel(name = "代理名称")
    private String agentName;
    private String password;
    private String loginName;

    private String parentAgentId;
    /**
     * 总余额
     */
    @Excel(name = "总余额")
    private Double balance;

    /**
     * 信用余额
     */
    @Excel(name = "信用余额")
    private Double creditBalance;

    /**
     * 可用余额
     */
    @Excel(name = "可用余额")
    private Double availableBalance;

    /**
     * 冻结余额
     */
    @Excel(name = "冻结余额")
    private Double fronzenBalance;

    /**
     * 费率
     */
    @Excel(name = "费率")
    private Double ratio;

    /**
     * 收益
     */
    @Excel(name = "收益")
    private Double profitBalance;

    /**
     * 1：启用，0:停用，-1:软删除
     */
    @Excel(name = "1：启用，0:停用，-1:软删除")
    private String status;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateTime;

    /**
     * 版本
     */
    @Excel(name = "版本")
    private Long version;

    /**
     * 卡商代理登录账号
     */
    @Excel(name = "卡商代理登录账号")
    private Long userId;

    /**
     * 支付密码
     */
    private String payCode;

    /**
     * 代理层级：0为卡商（总代），1为一级代理
     */
    @Excel(name = "代理层级：0为卡商", readConverterExp = "总=代")
    private String agentLevel;
    private String createby;

    private String agentCode;

    private String parentSupplierBranchId;

    private String supplierBranchIdGroupStr;
    private List<String> supplierBranchIdGroupList;
}