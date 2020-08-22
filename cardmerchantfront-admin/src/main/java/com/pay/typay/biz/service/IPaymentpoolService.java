package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.Paymentpool;
import com.pay.typay.biz.domain.Paypoolchannel;
import com.pay.typay.common.core.domain.Ztree;

import java.util.List;

/**
 * 财务分部支付池Service接口
 *
 * @author oswald
 * @date 2020-01-19
 */
public interface IPaymentpoolService {
    /**
     * 查询财务分部支付池
     *
     * @param paypoolid 财务分部支付池ID
     * @return 财务分部支付池
     */
    Paymentpool selectPaymentpoolById(Long paypoolid);

    List<Paypoolchannel> selectPaypoolchannelByPoolidlist(Long paypoolid);
    /**
     * 查询财务分部支付池列表
     *
     * @param tPaypool 财务分部支付池
     * @return 财务分部支付池集合
     */
    List<Paymentpool> selectPaymentpoolList(Paymentpool tPaypool);

    /**
     * 查询财务分部支付池列表
     *
     * @param tPaypool 财务分部支付池
     * @return 财务分部支付池集合
     */
    List<Paypoolchannel> selectPaymentpoolpaychannellist(Paymentpool tPaypool);

    /**
     * 新增财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    int insertPaymentpool(Paymentpool tPaypool);

    /**
     * 修改财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    int updatePaymentpool(Paymentpool tPaypool);

    int updatePaymentpoolStatus(Paypoolchannel tPaypool);

    /**
     * 批量删除财务分部支付池
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePaymentpoolByIds(String ids);

    /**
     * 删除财务分部支付池信息
     *
     * @param paypoolid 财务分部支付池ID
     * @return 结果
     */
    int deletePaymentpoolById(Long paypoolid);

    List<Ztree> selectTreepoolchanneltreedata();

    List<Ztree> selectTreepoolchanneltreedataChoose(Paymentpool paymentpool);

    int updatepoolchannel(Paypoolchannel tPaypool);
}
