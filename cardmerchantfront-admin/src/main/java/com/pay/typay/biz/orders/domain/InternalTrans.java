package com.pay.typay.biz.orders.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 内转订单对象 t_b2binternaltransorder
 * 
 * @author warren
 * @date 2020-05-18
 */
@Data
public class InternalTrans extends BaseEntity
{
    private static final long serialVersionUID = 1L;

//    /** 订单索引 */
//    private BigInteger orderIndex;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderId;

    /** 分部ID */
    private Long supplierBranchID;

    //转出银行卡ID
//    @Excel(name = "转出银行卡ID")
    private Integer outputBankID;

    //转出银行卡ID
    @Excel(name = "转出银行卡简码")
    private String outputBankAccount;

    //转入银行卡ID
//    @Excel(name = "转入银行卡ID")
    private Integer inputBankID;

    //转入银行卡号
    @Excel(name = "转入银行卡号")
    private String inputBankNum;

    //转入银行卡姓名
    @Excel(name = "收款人姓名")
    private String inputBankOwner;

    //转入银行卡地址
//    @Excel(name = "转入银行卡地址")
    private String inputBankAddress;

    //交易金额
    @Excel(name = "交易金额")
    private BigDecimal tradeAmount;

    //状态
    @Excel(name = "状态")
    private Integer status;

    //银行卡类型代码
//    @Excel(name = "银行卡类型代码")
    private String bankCode;

    //订单类型,1:超额内转订单，2低额内转订单，3，充值测试转账订单,4提款测试转账订单,5卡异常转出金额,100父订单
//    @Excel(name = "订单类型")
    private String orderType;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "创建时间")
    private String orderCreateTime;

    @Excel(name = "创建时间")
    private String lastUpDateTime;

    private String beginTime;
    private String endTime;
    private List<String> supplierBranchIdGroupList;
}
