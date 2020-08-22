package com.pay.typay.biz.orders.service;


import com.pay.typay.biz.orders.domain.Userwithdrew;

import java.util.List;

/**
 * 用户提款订单Service接口
 * 
 * @author warren
 * @date 2020-05-18
 */
public interface IUserwithdrewService 
{
    /**
     * 查询用户提款订单
     * 
     * @param orderindex 用户提款订单ID
     * @return 用户提款订单
     */
     Userwithdrew selectUserwithdrewById(Long orderindex);

    /**
     * 查询用户提款订单列表
     * 
     * @param agentwithdraworder 用户提款订单
     * @return 用户提款订单集合
     */
     List<Userwithdrew> selectUserwithdrewList(Userwithdrew agentwithdraworder);

     Userwithdrew getSummary(Userwithdrew agentwithdraworder);

    Userwithdrew getSuccessTotal(Userwithdrew agentwithdraworder);

    Userwithdrew getAllTotal(Userwithdrew agentwithdraworder);

}
