package com.pay.typay.biz.orders.service;


import com.pay.typay.biz.orders.domain.Userecharge;
import org.apache.catalina.User;

import java.util.List;

/**
 * 用户充值订单Service接口
 * 
 * @author warren
 * @date 2020-05-18
 */
public interface IUserechargeService 
{
    /**
     * 查询用户充值订单
     * 
     * @param orderindex 用户充值订单ID
     * @return 用户充值订单
     */
     Userecharge selectUserechargeById(Long orderindex);

    /**
     * 查询用户充值订单列表
     * 
     * @param agentdepositorder 用户充值订单
     * @return 用户充值订单集合
     */
     List<Userecharge> selectUserechargeList(Userecharge agentdepositorder);

     Userecharge getSummary(Userecharge agentdepositorder);

     Userecharge getSuccessTotal(Userecharge agentdepositorder);

     Userecharge getAllTotal(Userecharge agentdepositorder);

}
