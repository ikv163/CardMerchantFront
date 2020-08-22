package com.pay.typay.biz.orders.service.impl;

import com.pay.typay.biz.orders.domain.InternalTrans;
import com.pay.typay.biz.orders.mapper.InternalTransMapper;
import com.pay.typay.biz.orders.service.InternalTransService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内转订单Service业务层处理
 * 
 * @author warren
 * @date 2020-05-18
 */
@Service
@DataSource(DataSourceType.typayv2)
public class InternalTransServiceImpl implements InternalTransService
{
    @Autowired
    private InternalTransMapper internalTransMapper;

    /**
     * 查询用户充值订单
     * 
     * @param orderIndex 用户充值订单ID
     * @return 用户充值订单
     */
    @Override
    public InternalTrans selectInternalTransById(Long orderIndex)
    {
        return internalTransMapper.selectInternalTransById(orderIndex);
    }

    /**
     * 查询用户充值订单列表
     * 
     * @param internalOrder 用户充值订单
     * @return 用户充值订单
     */
    @Override
    public List<InternalTrans> selectInternalTransList(InternalTrans internalOrder)
    {
        return internalTransMapper.selectInternalTransList(internalOrder);
    }
}
