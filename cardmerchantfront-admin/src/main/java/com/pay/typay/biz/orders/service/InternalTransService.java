package com.pay.typay.biz.orders.service;


import com.pay.typay.biz.orders.domain.InternalTrans;
import com.pay.typay.biz.orders.domain.Userecharge;

import java.util.List;

/**
 * 内转订单Service接口
 * 
 * @author warren
 * @date 2020-05-18
 */
public interface InternalTransService
{
    /**
     * 查询内转订单
     * 
     * @param orderIndex 内转订单ID
     * @return 内转订单
     */
    InternalTrans selectInternalTransById(Long orderIndex);

    /**
     * 查询内转订单列表
     * 
     * @param internalOrder 内转订单
     * @return 内转订单集合
     */
    List<InternalTrans> selectInternalTransList(InternalTrans internalOrder);

//     Userecharge getSummary(Userecharge agentdepositorder);
//
//     Userecharge getSuccessTotal(Userecharge agentdepositorder);
//
//     Userecharge getAllTotal(Userecharge agentdepositorder);

}
