package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.Paychannel;
import com.pay.typay.biz.domain.RateVO;
import com.pay.typay.common.core.domain.AjaxResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc: 支付渠道service接口
 * @author： Warren
 * @createtime： 2020/1/15
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
public interface IPayChannelService {

    /**
     * 查询支付渠道
     *
     * @param paychannelid 支付渠道ID
     * @param type 1、三方   2、普通
     * @return 支付渠道
     */
    Paychannel selectPaychannelById(Long paychannelid,Long type);

    /**
     * 根据id集合查询渠道集合
     * @param ids
     * @return
     */
    List<Paychannel> selectPayChannelByIds(List ids,Long type);

    /**
     * 查询银行卡情况监控
     *
     * @param tPaychannel 支付渠道
     * @return 支付渠道集合
     */
    List<Paychannel> selectBankPaychannelList(Paychannel tPaychannel);


    /**
     * 新增支付渠道
     *
     * @param tPaychannel 支付渠道
     * @return 结果
     */
    int insertPaychannel(Paychannel tPaychannel);

    /**
     * 修改支付渠道
     *
     * @param paychannels 支付渠道
     * @return 结果
     */
    int updatePaychannels(List<Paychannel> paychannels);

    int updatePaychannel(Paychannel paychannel);

}
