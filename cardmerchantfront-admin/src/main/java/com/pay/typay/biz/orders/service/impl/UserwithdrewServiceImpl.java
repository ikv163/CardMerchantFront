package com.pay.typay.biz.orders.service.impl;

import com.pay.typay.biz.orders.domain.Userwithdrew;
import com.pay.typay.biz.orders.mapper.UserwithdrewMapper;
import com.pay.typay.biz.orders.service.IUserwithdrewService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 用户提款订单Service业务层处理
 * 
 * @author warren
 * @date 2020-05-18
 */
@Service
@DataSource(DataSourceType.typayv2)
public class UserwithdrewServiceImpl implements IUserwithdrewService
{
    @Autowired
    private UserwithdrewMapper agentwithdraworderMapper;

    /**
     * 查询用户提款订单
     * 
     * @param orderindex 用户提款订单ID
     * @return 用户提款订单
     */
    @Override
    public Userwithdrew selectUserwithdrewById(Long orderindex)
    {
        String supplierbranchid;
        //如果是管理员账号
        if (ShiroUtils.getSysUser().isAdmin()) {
            supplierbranchid = UtilsUser.getSupplierBranchIdGroup();
        } else {
            supplierbranchid = ShiroUtils.getSupplierbranchid().toString();
        }
        return agentwithdraworderMapper.selectUserwithdrewById(orderindex, supplierbranchid);
    }

    /**
     * 查询用户提款订单列表
     * 
     * @param agentwithdraworder 用户提款订单
     * @return 用户提款订单
     */
    @Override
    public List<Userwithdrew> selectUserwithdrewList(Userwithdrew agentwithdraworder)
    {
        return agentwithdraworderMapper.selectUserwithdrewList(agentwithdraworder);
    }

    @Override
    public Userwithdrew getSummary(Userwithdrew agentwithdraworder)
    {
        return agentwithdraworderMapper.getSummary(agentwithdraworder);
    }

    public Userwithdrew getSuccessTotal(Userwithdrew agentwithdraworder)
    {
        return agentwithdraworderMapper.getSuccessTotal(agentwithdraworder);
    }

    public Userwithdrew getAllTotal(Userwithdrew agentwithdraworder)
    {
        return agentwithdraworderMapper.getAllTotal(agentwithdraworder);
    }



}
