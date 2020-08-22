package com.pay.typay.biz.orders.mapper;

import com.pay.typay.biz.orders.domain.InternalTrans;
import com.pay.typay.biz.orders.domain.Userecharge;

import java.util.List;

/**
 * 内转订单Mapper接口
 * 
 * @author warren
 * @date 2020-05-18
 */
public interface InternalTransMapper
{
    /**
     * 查询用户充值订单
     * 
     * @param orderIndex 内转订单ID
     * @return 用户充值订单
     */
     InternalTrans selectInternalTransById(Long orderIndex);

    /**
     * 查询用户充值订单列表
     * 
     * @param internalOrder 用户充值订单
     * @return 用户充值订单集合
     */
     List<InternalTrans> selectInternalTransList(InternalTrans internalOrder);



}
