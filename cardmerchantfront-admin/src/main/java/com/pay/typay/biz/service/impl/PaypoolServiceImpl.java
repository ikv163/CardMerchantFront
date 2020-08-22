package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.Paypool;
import com.pay.typay.biz.mapper.PaypoolMapper;
import com.pay.typay.biz.service.IPaypoolService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.domain.Ztree;
import com.pay.typay.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * 财务分部支付池Service业务层处理
 * 
 * @author Warren
 * @date 2020-01-16
 */
@Service
@DataSource(DataSourceType.typayv2)
public class PaypoolServiceImpl implements IPaypoolService 
{
    @Autowired
    private PaypoolMapper tPaypoolMapper;

    /**
     * 查询财务分部支付池
     * 
     * @param paypoolid 财务分部支付池ID
     * @return 财务分部支付池
     */
    @Override
    public Paypool selectPaypoolById(Long paypoolid)
    {
        return tPaypoolMapper.selectPaypoolById(paypoolid);
    }

    /**
     * 查询财务分部支付池列表
     * 
     * @param tPaypool 财务分部支付池
     * @return 财务分部支付池
     */
    @Override
    public List<Paypool> selectPaypoolList(Paypool tPaypool)
    {

        return tPaypoolMapper.selectPaypoolList(tPaypool);
    }

    /**
     * 修改财务分部支付池
     * 
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    @Override
    public int updatePaypool(Paypool tPaypool)
    {
        return tPaypoolMapper.updatePaypool(tPaypool);
    }

    /**
     * 查询财务分部支付池树列表
     * 
     * @return 所有财务分部支付池信息
     */
    @Override
    public List<Ztree> selectPaypoolTree()
    {
        List<Paypool> tPaypoolList = tPaypoolMapper.selectPaypoolList(new Paypool());
        List<Ztree> ztrees = new ArrayList<>();
        for (Paypool tPaypool : tPaypoolList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(tPaypool.getPayChannelId());
            ztree.setpId(tPaypool.getParentId());
            ztree.setName(tPaypool.getPaypoolname());
            ztree.setTitle(tPaypool.getPaypoolname());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     * 新增财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    @Override
    public int insertPaypool(Paypool tPaypool) {

        return tPaypoolMapper.insertPaypool(tPaypool);
    }
}
