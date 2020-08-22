package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.Paypool;

import java.util.List;

/**
 * 财务分部支付池Mapper接口
 * 
 * @author Warren
 * @date 2020-01-16
 */
public interface PaypoolMapper 
{
    /**
     * 查询财务分部支付池
     * 
     * @param paypoolid 财务分部支付池ID
     * @return 财务分部支付池
     */
     Paypool selectPaypoolById(Long paypoolid);

    /**
     * 查询财务分部支付池列表
     * 
     * @param tPaypool 财务分部支付池
     * @return 财务分部支付池集合
     */
     List<Paypool> selectPaypoolList(Paypool tPaypool);

    /**
     * 修改财务分部支付池
     * 
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
     int updatePaypool(Paypool tPaypool);

    /**
     * 新增财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    int insertPaypool(Paypool tPaypool);
}
