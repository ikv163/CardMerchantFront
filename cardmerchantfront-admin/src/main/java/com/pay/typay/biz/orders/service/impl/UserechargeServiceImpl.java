package com.pay.typay.biz.orders.service.impl;

import com.pay.typay.biz.orders.domain.Userecharge;
import com.pay.typay.biz.orders.mapper.UserechargeMapper;
import com.pay.typay.biz.orders.service.IUserechargeService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 用户充值订单Service业务层处理
 * 
 * @author warren
 * @date 2020-05-18
 */
@Service
@DataSource(DataSourceType.typayv2)
public class UserechargeServiceImpl implements IUserechargeService
{
    @Autowired
    private UserechargeMapper agentdepositorderMapper;

    /**
     * 查询用户充值订单
     * 
     * @param orderindex 用户充值订单ID
     * @return 用户充值订单
     */
    @Override
    public Userecharge selectUserechargeById(Long orderindex)
    {
        return agentdepositorderMapper.selectUserechargeById(orderindex, UtilsUser.getUserSupplierbranchid().toString());
    }

    /**
     * 查询用户充值订单列表
     * 
     * @param agentdepositorder 用户充值订单
     * @return 用户充值订单
     */
    @Override
    public List<Userecharge> selectUserechargeList(Userecharge agentdepositorder)
    {
        return agentdepositorderMapper.selectUserechargeList(agentdepositorder);
    }

    /**
     *  统计申请金额，充值金额数据
     * @param agentdepositorder
     * @return
     */
    @Override
    public Userecharge getSummary(Userecharge agentdepositorder) {
        return agentdepositorderMapper.getSummary(agentdepositorder);
    }

    /**
     *  统计申请金额，充值金额数据
     * @param agentdepositorder
     * @return
     */
    @Override
    public Userecharge getSuccessTotal(Userecharge agentdepositorder) {
        return agentdepositorderMapper.getSuccessTotal(agentdepositorder);
    }

    /**
     *  统计申请金额，充值金额数据
     * @param agentdepositorder
     * @return
     */
    @Override
    public Userecharge getAllTotal(Userecharge agentdepositorder) {
        return agentdepositorderMapper.getAllTotal(agentdepositorder);
    }
}
