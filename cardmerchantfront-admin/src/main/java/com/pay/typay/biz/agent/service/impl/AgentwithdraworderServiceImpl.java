package com.pay.typay.biz.agent.service.impl;

import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.domain.Agentwithdraworder;
import com.pay.typay.biz.agent.mapper.AgentcenterMapper;
import com.pay.typay.biz.agent.mapper.AgentwithdraworderMapper;
import com.pay.typay.biz.agent.service.IAgentwithdraworderService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.utils.SnowflakeIdWorker;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
/**
 * 卡商提款订单Service业务层处理
 * 
 * @author oswald
 * @date 2020-05-14
 */
@Service
@DataSource(DataSourceType.typayv2)
public class AgentwithdraworderServiceImpl implements IAgentwithdraworderService 
{
    @Autowired
    private AgentwithdraworderMapper tAgentwithdraworderMapper;

    @Autowired
    private AgentcenterMapper agentcenterMapper;

    /**
     * 查询卡商提款订单
     * 
     * @param orderindex 卡商提款订单ID
     * @return 卡商提款订单
     */
    @Override
    public Agentwithdraworder selectAgentwithdraworderById(Long orderindex)
    {
        return tAgentwithdraworderMapper.selectAgentwithdraworderById(orderindex,ShiroUtils.getSupplierbranchid().toString());
    }

    /**
     * 查询卡商提款订单列表
     * 
     * @param tAgentwithdraworder 卡商提款订单
     * @return 卡商提款订单
     */
    @Override
    public List<Agentwithdraworder> selectAgentwithdraworderList(Agentwithdraworder tAgentwithdraworder)
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        tAgentwithdraworder.setSupplierbranchid(sysUser.getSupplierbranchid());
//        tAgentwithdraworder.setAgentId(sysUser.getUserId());
        return tAgentwithdraworderMapper.selectAgentwithdraworderList(tAgentwithdraworder);
    }

    /**
     * 新增卡商提款订单
     * 
     * @param tAgentwithdraworder 卡商提款订单
     * @return 结果
     */
    @Override
    public int insertAgentwithdraworder(Agentwithdraworder tAgentwithdraworder)
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        tAgentwithdraworder.setSupplierbranchid(sysUser.getSupplierbranchid());
        tAgentwithdraworder.setPassword(PHPpassword.PHPpasswordHash(tAgentwithdraworder.getPassword()));
        Long nextId = SnowflakeIdWorker.getNextId();
        tAgentwithdraworder.setOrderid(nextId+"");
        tAgentwithdraworder.setOrderindex(nextId);
//        tAgentwithdraworder.setAgentCode(sysUser.getUserId());
        return tAgentwithdraworderMapper.insertAgentwithdraworder(tAgentwithdraworder);
    }

    /**
     * 修改卡商提款订单
     * 
     * @param tAgentwithdraworder 卡商提款订单
     * @return 结果
     */
    @Override
    public int updateAgentwithdraworder(Agentwithdraworder tAgentwithdraworder)
    {
        return tAgentwithdraworderMapper.updateAgentwithdraworder(tAgentwithdraworder);
    }

    /**
     * 取消订单
     * @param tAgentwithdraworder
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelOrder(Agentwithdraworder tAgentwithdraworder){
        Agentwithdraworder current = tAgentwithdraworderMapper.selectAgentwithdraworderById(tAgentwithdraworder.getOrderindex(),ShiroUtils.getSupplierbranchid().toString());
        if (current.getOrderstatus() != 0){
            return 0;
        }

        Agentcenter agentcenter = agentcenterMapper.selectAgentcenterById(current.getAgent_id(),ShiroUtils.getSupplierbranchid().toString(),"");
        Double availableBalance = agentcenter.getAvailableBalance();
        Double fronzenBalance = agentcenter.getFronzenBalance();
        Double paidamount = current.getPayamount();
        //取消订单可用余额增加
        agentcenter.setAvailableBalance(availableBalance  + paidamount);
        //取消订单冻结余额减少
        agentcenter.setFronzenBalance(fronzenBalance - paidamount);
        agentcenterMapper.updateAgentcenter(agentcenter);
        return tAgentwithdraworderMapper.updateAgentwithdraworder(tAgentwithdraworder);
    }

    /**
     * 删除卡商提款订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAgentwithdraworderByIds(String ids)
    {
        return tAgentwithdraworderMapper.deleteAgentwithdraworderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除卡商提款订单信息
     * 
     * @param orderindex 卡商提款订单ID
     * @return 结果
     */
    @Override
    public int deleteAgentwithdraworderById(Long orderindex)
    {
        return tAgentwithdraworderMapper.deleteAgentwithdraworderById(orderindex);
    }

    /**
     * 统计成功提款的订单金额
     * @param agentId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Agentwithdraworder getSuccessSummary(String agentId,String startTime,String endTime)
    {
        return tAgentwithdraworderMapper.getSuccessSummary(agentId,startTime,endTime);
    }


}
