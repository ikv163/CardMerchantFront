package com.pay.typay.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.typay.biz.mapper.BankinchannelMapper;
import com.pay.typay.biz.domain.Bankinchannel;
import com.pay.typay.biz.service.IBankinchannelService;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
/**
 * 银行卡渠道关联表Service业务层处理
 * 
 * @author js-bucky
 * @date 2020-02-12
 */
@Service
@DataSource(DataSourceType.typayv2)
public class BankinchannelServiceImpl implements IBankinchannelService 
{
    @Autowired
    private BankinchannelMapper bankinChannelMapper;

    /**
     * 查询银行卡渠道关联表
     * 
     * @param bankid 银行卡渠道关联表ID
     * @return 银行卡渠道关联表
     */
    @Override
    public Bankinchannel selectBankinchannelById(Long bankid)
    {
        return bankinChannelMapper.selectBankinchannelById(bankid);
    }

    /**
     * 查询银行卡渠道关联表列表
     * 
     * @param bankinChannel 银行卡渠道关联表
     * @return 银行卡渠道关联表
     */
    @Override
    public List<Bankinchannel> selectBankinchannelList(Bankinchannel bankinChannel)
    {
        return bankinChannelMapper.selectBankinchannelList(bankinChannel);
    }

    /**
     * 新增银行卡渠道关联表
     * 
     * @param bankinChannel 银行卡渠道关联表
     * @return 结果
     */
    @Override
    public int insertBankinchannel(Bankinchannel bankinChannel)
    {
        return bankinChannelMapper.insertBankinchannel(bankinChannel);
    }

    /**
     * 修改银行卡渠道关联表
     * 
     * @param bankinChannel 银行卡渠道关联表
     * @return 结果
     */
    @Override
    public int updateBankinchannel(Bankinchannel bankinChannel)
    {
        return bankinChannelMapper.updateBankinchannel(bankinChannel);
    }

    /**
     * 删除银行卡渠道关联表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBankinchannelByIds(String ids)
    {
        return bankinChannelMapper.deleteBankinchannelByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除银行卡渠道关联表信息
     * 
     * @param bankid 银行卡渠道关联表ID
     * @return 结果
     */
    @Override
    public int deleteBankinchannelById(Long bankid)
    {
        return bankinChannelMapper.deleteBankinchannelById(bankid);
    }
}
