package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.Bankinchannel;
import com.pay.typay.biz.domain.Paychannel;

import java.util.List;

/**
 * 银行卡渠道关联表Mapper接口
 *
 * @author js-bucky
 * @date 2020-02-12
 */
public interface BankinchannelMapper {
    /**
     * 查询银行卡渠道关联表
     *
     * @param bankid 银行卡渠道关联表ID
     * @return 银行卡渠道关联表
     */
    Bankinchannel selectBankinchannelById(Long bankid);

    /**
     * 查询银行卡渠道关联表列表
     *
     * @param bankinChannel 银行卡渠道关联表
     * @return 银行卡渠道关联表集合
     */
    List<Bankinchannel> selectBankinchannelList(Bankinchannel bankinChannel);

    /**
     * 查询银行卡渠道关联表列表
     *
     * @param bankpoolid 银行卡渠道关联表
     * @return 银行卡渠道关联表集合
     */
    List<Bankinchannel> selectBankinchannelListBybankpoolid(Long bankpoolid);
    List<String> selectChannelidsListBybankpoolid(Long bankpoolid);

    /**
     * 新增银行卡渠道关联表
     *
     * @param bankinChannel 银行卡渠道关联表
     * @return 结果
     */
    int insertBankinchannel(Bankinchannel bankinChannel);

    int insertBankinchannelByBatch(List<Bankinchannel> list);

    /**
     * 修改银行卡渠道关联表
     *
     * @param bankinChannel 银行卡渠道关联表
     * @return 结果
     */
    int updateBankinchannel(Bankinchannel bankinChannel);

    /**
     * 删除银行卡渠道关联表
     *
     * @param bankid 银行卡渠道关联表ID
     * @return 结果
     */
    int deleteBankinchannelById(Long bankid);

    /**
     * 批量删除银行卡渠道关联表
     *
     * @param bankids 需要删除的数据ID
     * @return 结果
     */
    int deleteBankinchannelByIds(String[] bankids);

    int deleteBankinchannelByChannelIds(String[] channelids);

    List<Bankinchannel> selectBankinchannelListByChannelids(String[] channelids);

    List<Bankinchannel> selectBankids(Paychannel paychannel);
}
