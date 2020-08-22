package com.pay.typay.biz.agent.service.impl;

import com.pay.typay.biz.agent.domain.Agentcreditorder;
import com.pay.typay.biz.agent.mapper.AgentcreditorderMapper;
import com.pay.typay.biz.agent.service.IAgentcreditorderService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
/**
 * 代理额度转换,信用订单Service业务层处理
 * 
 * @author warren
 * @date 2020-05-26
 */
@Service
@DataSource(DataSourceType.typayv2)
public class AgentcreditorderServiceImpl implements IAgentcreditorderService
{
    @Autowired
    private AgentcreditorderMapper tAgentcreditorderMapper;

    /**
     * 查询代理额度转换,信用订单
     * 
     * @param orderindex 代理额度转换,信用订单ID
     * @return 代理额度转换,信用订单
     */
    @Override
    public Agentcreditorder selectAgentcreditorderById(Long orderindex)
    {
        String supplierbranchid;
        //如果是管理员账号
        if (ShiroUtils.getSysUser().isAdmin()) {
            supplierbranchid = UtilsUser.getSupplierBranchIdGroup();
        } else {
            supplierbranchid = ShiroUtils.getSupplierbranchid().toString();
        }
        return tAgentcreditorderMapper.selectAgentcreditorderById(orderindex, supplierbranchid);
    }

    /**
     * 查询代理额度转换,信用订单列表
     * 
     * @param tAgentcreditorder 代理额度转换,信用订单
     * @return 代理额度转换,信用订单
     */
    @Override
    public List<Agentcreditorder> selectAgentcreditorderList(Agentcreditorder tAgentcreditorder)
    {
        return tAgentcreditorderMapper.selectAgentcreditorderList(tAgentcreditorder);
    }

    /**
     * 新增代理额度转换,信用订单
     * 
     * @param tAgentcreditorder 代理额度转换,信用订单
     * @return 结果
     */
    @Override
    public int insertAgentcreditorder(Agentcreditorder tAgentcreditorder)
    {
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        tAgentcreditorder.setSupplierbranchid(userSupplierbranchid);
        return tAgentcreditorderMapper.insertAgentcreditorder(tAgentcreditorder);
    }

    /**
     * 修改代理额度转换,信用订单
     * 
     * @param tAgentcreditorder 代理额度转换,信用订单
     * @return 结果
     */
    @Override
    public int updateAgentcreditorder(Agentcreditorder tAgentcreditorder)
    {
        return tAgentcreditorderMapper.updateAgentcreditorder(tAgentcreditorder);
    }

}
