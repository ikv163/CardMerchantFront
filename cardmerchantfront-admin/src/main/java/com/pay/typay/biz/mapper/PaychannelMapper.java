package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.Paychannel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc: 支付渠道Mapper接口
 * @author： Warren
 * @createtime： 2020/1/15
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
public interface PaychannelMapper {

    /**
     * 查询支付渠道
     *
     * @param paychannelid 支付渠道ID
     * @return 支付渠道
     */
//    Paychannel selectPaychannelById(Long paychannelid);

    /**
     * 查询支付渠道
     *
     * @param paychannelid 支付渠道ID
     * @param type 1、三方   2、普通
     * @return 支付渠道
     */
    Paychannel selectPaychannelById(@Param("paychannelid") Long paychannelid,@Param("type")Long type);

    /**
     * 根据id集合查询渠道集合
     * @param ids
     * @return
     */
    List<Paychannel> selectPayChannelByIds(@Param("ids") List ids,@Param("type")Long type);

    /**
     * 查询支付渠道列表
     *
     * @param tPaychannel 支付渠道
     * @return 支付渠道集合
     */
    List<Paychannel> selectMonitorBankCardList(Paychannel tPaychannel);

    /**
     * 查询银行卡情况监控
     *
     * @param tPaychannel 支付渠道
     * @return 支付渠道集合
     */
    List<Paychannel> selectBankPaychannelList(Paychannel tPaychannel);
    List<Paychannel> selectBankPaychannelListsetup(Paychannel tPaychannel);
    /**
     * 新增支付渠道
     *
     * @param tPaychannel 支付渠道
     * @return 结果
     */
    int insertPaychannel(Paychannel tPaychannel);

    /**
     * 批量修改状态
     * @param payChannels
     * @return
     */
    int updatePayChannels(@Param("payChannels") List<Paychannel> payChannels);

    int updatePayChannel(Paychannel paychannel);

    int updateRate(@Param("payChannels") List<Paychannel> payChannels);

    List<Paychannel> selectPayPoolChannelList(Paychannel paychannel);

    int insertBankInChannel(@Param("channelid")Long channelId,@Param("bankIds")List<Long> bankIds);

    int deleteByChannelId(@Param("channelId")Long channelId);

    List<Long> selectBankIdsByPoolId(@Param("poolId")Long poolId);

    Paychannel selectChannelAndSettingById(Long paychannelids);

    void updatePayChannelSetting(Paychannel tpaychannel);

    void updatePayChannelData(Paychannel tpaychannel);

    void deleteByBankId(@Param("bankids")List<Long> bankids);

    List<Paychannel> getMerchantWithdrawChannelFromBank(@Param("SupplierBranchID") Long SupplierBranchID,
                                                        @Param("payAmount") BigDecimal payAmount);

    List<Paychannel> getPayChannel(@Param("payAmount") BigDecimal payAmount,
                                   @Param("SupplierBranchID") Long SupplierBranchID);
}
