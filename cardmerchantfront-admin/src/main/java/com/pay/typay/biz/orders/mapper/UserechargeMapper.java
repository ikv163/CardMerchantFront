package com.pay.typay.biz.orders.mapper;

import com.pay.typay.biz.orders.domain.Userecharge;

import java.util.List;

/**
 * 用户充值订单Mapper接口
 * 
 * @author warren
 * @date 2020-05-18
 */
public interface UserechargeMapper 
{
    /**
     * 查询用户充值订单
     * 
     * @param orderindex 用户充值订单ID
     * @return 用户充值订单
     */
     Userecharge selectUserechargeById(Long orderindex,String supplierBranchId);

    /**
     * 查询用户充值订单列表
     * 
     * @param agentdepositorder 用户充值订单
     * @return 用户充值订单集合
     */
     List<Userecharge> selectUserechargeList(Userecharge agentdepositorder);

    /**
     * 统计申请金额，充值金额数据
     * @param agentdepositorder
     * @return
     */
     Userecharge getSummary(Userecharge agentdepositorder);
     Userecharge getSuccessTotal(Userecharge agentdepositorder);
     Userecharge getAllTotal(Userecharge agentdepositorder);

}
