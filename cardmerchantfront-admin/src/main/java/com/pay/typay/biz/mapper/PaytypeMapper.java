package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.Paytype;

import java.util.List;

/**
 * 支付方式Mapper接口
 * 
 * @author Warren
 * @date 2020-01-20
 */
public interface PaytypeMapper 
{
    /**
     * 查询支付方式
     * 
     * @param paytypeid 支付方式ID
     * @return 支付方式
     */
     Paytype selectPaytypeById(Long paytypeid);

    /**
     * 查询支付方式列表
     * 
     * @param tPaytype 支付方式
     * @return 支付方式集合
     */
     List<Paytype> selectPaytypeList(Paytype tPaytype);

    /**
     * 新增支付方式
     * 
     * @param tPaytype 支付方式
     * @return 结果
     */
     int insertPaytype(Paytype tPaytype);

    /**
     * 修改支付方式
     * 
     * @param tPaytype 支付方式
     * @return 结果
     */
     int updatePaytype(Paytype tPaytype);

    /**
     * 删除支付方式
     * 
     * @param paytypeid 支付方式ID
     * @return 结果
     */
     int deletePaytypeById(Long paytypeid);

    /**
     * 批量删除支付方式
     * 
     * @param paytypeids 需要删除的数据ID
     * @return 结果
     */
     int deletePaytypeByIds(String[] paytypeids);

    List<Paytype> paytypeBankcardpayOnlyList(Paytype paytype);
    List<Paytype> paytypeThirtyPartOnlyList(Paytype paytype);
}
