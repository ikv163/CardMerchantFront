package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.Bankinchannel;
import java.util.List;

/**
 * 银行卡渠道关联表Service接口
 * 
 * @author js-bucky
 * @date 2020-02-12
 */
public interface IBankinchannelService 
{
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
     * 新增银行卡渠道关联表
     * 
     * @param bankinChannel 银行卡渠道关联表
     * @return 结果
     */
     int insertBankinchannel(Bankinchannel bankinChannel);

    /**
     * 修改银行卡渠道关联表
     * 
     * @param bankinChannel 银行卡渠道关联表
     * @return 结果
     */
     int updateBankinchannel(Bankinchannel bankinChannel);

    /**
     * 批量删除银行卡渠道关联表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteBankinchannelByIds(String ids);

    /**
     * 删除银行卡渠道关联表信息
     * 
     * @param bankid 银行卡渠道关联表ID
     * @return 结果
     */
     int deleteBankinchannelById(Long bankid);
}
