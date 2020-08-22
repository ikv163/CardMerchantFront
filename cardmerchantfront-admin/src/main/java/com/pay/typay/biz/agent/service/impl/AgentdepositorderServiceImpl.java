package com.pay.typay.biz.agent.service.impl;

import com.pay.typay.biz.agent.domain.Agentdepositorder;
import com.pay.typay.biz.agent.mapper.AgentdepositorderMapper;
import com.pay.typay.biz.agent.service.IAgentdepositorderService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.utils.SnowflakeIdWorker;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
/**
 * 代理银行卡充值订单Service业务层处理
 * 
 * @author oswald
 * @date 2020-05-14
 */
@Service
@DataSource(DataSourceType.typayv2)
public class AgentdepositorderServiceImpl implements IAgentdepositorderService 
{
    @Autowired
    private AgentdepositorderMapper tAgentdepositorderMapper;

    /**
     * 查询代理银行卡充值订单
     * 
     * @param orderindex 代理银行卡充值订单ID
     * @return 代理银行卡充值订单
     */
    @Override
    public Agentdepositorder selectAgentdepositorderById(Long orderindex)
    {
        return tAgentdepositorderMapper.selectAgentdepositorderById(orderindex,ShiroUtils.getSupplierbranchid().toString());
    }

    /**
     * 查询代理银行卡充值订单列表
     * 
     * @param tAgentdepositorder 代理银行卡充值订单
     * @return 代理银行卡充值订单
     */
    @Override
    public List<Agentdepositorder> selectAgentdepositorderList(Agentdepositorder tAgentdepositorder)
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        tAgentdepositorder.setSupplierbranchid(sysUser.getSupplierbranchid());
        if (!sysUser.isAdmin()){
            tAgentdepositorder.setAgentId(Long.parseLong(sysUser.getAgentId()));
        }
       // tAgentdepositorder.setAgentId(Long.parseLong(sysUser.getAgentId()));
//        tAgentdepositorder.setAgentCode(sysUser.getUserId());
        return tAgentdepositorderMapper.selectAgentdepositorderList(tAgentdepositorder);
    }

    /**
     * 总计
     * @param tAgentdepositorder
     * @return
     */
    @Override
    public BigDecimal calcAgentdepositAmount(Agentdepositorder tAgentdepositorder)
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        tAgentdepositorder.setSupplierbranchid(sysUser.getSupplierbranchid());
        if (!sysUser.isAdmin()){
            tAgentdepositorder.setAgentId(Long.parseLong(sysUser.getAgentId()));
        }
        return tAgentdepositorderMapper.calcAgentdepositAmount(tAgentdepositorder);
    }

    /**
     * 新增代理银行卡充值订单
     * 
     * @param tAgentdepositorder 代理银行卡充值订单
     * @return 结果
     */
    @Override
    public int insertAgentdepositorder(Agentdepositorder tAgentdepositorder)
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        tAgentdepositorder.setSupplierbranchid(sysUser.getSupplierbranchid());
        Long nextId = SnowflakeIdWorker.getNextId();
        tAgentdepositorder.setOrderid(nextId+"");
        tAgentdepositorder.setOrderindex(nextId);
        tAgentdepositorder.setAgentId(Long.parseLong(ShiroUtils.getAgentId()));
        return tAgentdepositorderMapper.insertAgentdepositorder(tAgentdepositorder);
    }

    /**
     * 修改代理银行卡充值订单
     * 
     * @param tAgentdepositorder 代理银行卡充值订单
     * @return 结果
     */
    @Override
    public int updateAgentdepositorder(Agentdepositorder tAgentdepositorder)
    {
        return tAgentdepositorderMapper.updateAgentdepositorder(tAgentdepositorder);
    }

    /**
     * 删除代理银行卡充值订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAgentdepositorderByIds(String ids)
    {
        return tAgentdepositorderMapper.deleteAgentdepositorderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除代理银行卡充值订单信息
     * 
     * @param orderindex 代理银行卡充值订单ID
     * @return 结果
     */
    @Override
    public int deleteAgentdepositorderById(Long orderindex)
    {
        return tAgentdepositorderMapper.deleteAgentdepositorderById(orderindex);
    }
}
