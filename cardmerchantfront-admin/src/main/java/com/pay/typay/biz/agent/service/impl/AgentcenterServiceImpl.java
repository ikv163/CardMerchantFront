package com.pay.typay.biz.agent.service.impl;

import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.domain.Agentcreditorder;
import com.pay.typay.biz.agent.mapper.AgentcenterMapper;
import com.pay.typay.biz.agent.service.IAgentcenterService;
import com.pay.typay.biz.agent.service.IAgentcreditorderService;
import com.pay.typay.biz.dict.ITDictBankCommonService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.config.GlobalSetup;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.SnowflakeIdWorker;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.util.GoogleAuthenticator;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysUserService;
import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 卡商代理Service业务层处理
 *
 * @author oswald
 * @date 2020-05-13
 */
@Service
@DataSource(DataSourceType.typayv2)
public class AgentcenterServiceImpl implements IAgentcenterService {
    @Autowired
    private AgentcenterMapper tBankcardAgentMapper;
    @Autowired
    private ITDictBankCommonService dictBankCommonService;

    @Autowired
    private IAgentcreditorderService agentcreditorderService;

    /**
     * 查询卡商代理
     *
     * @param id 卡商代理ID
     * @return 卡商代理
     */
    @Override
    public Agentcenter selectAgentcenterById(Long id) {
        SysUser sysUser = ShiroUtils.getSysUser();
        String createBy = "";
        //如果是管理员账号
        if (!sysUser.isAdmin()) {
            createBy = UtilsUser.getLoginName();
        }
        return tBankcardAgentMapper.selectAgentcenterById(id,UtilsUser.getUserSupplierbranchid().toString(),createBy);
    }

    public Agentcenter selectAgentCenterByAgentName(String agentName) { return tBankcardAgentMapper.selectAgentCenterByAgentName(agentName);}

    /**
     * 查询卡商代理列表
     *
     * @param tBankcardAgent 卡商代理
     * @return 卡商代理
     */
    @Override
    public List<Agentcenter> selectAgentcenterList(Agentcenter tBankcardAgent) {
        return tBankcardAgentMapper.selectAgentcenterList(tBankcardAgent);
    }

    @Override
    public List<Agentcenter> checkLoginNameUnique(Agentcenter tBankcardAgent) {
        return tBankcardAgentMapper.selectAgentcenterListContrant(tBankcardAgent);
    }

    @Override
    public Agentcenter selectAgentCenterByParentAgentId(String parentAgentId) {
        return tBankcardAgentMapper.selectAgentCenterByParentAgentId(parentAgentId);
    }

    @Override
    public Agentcenter selectAgentCenterByAgentCode(String agentCode) {
        return tBankcardAgentMapper.selectAgentCenterByAgentCode(agentCode);
    }

    /**
     * 查询卡商代理列表
     *
     * @param tBankcardAgent 卡商代理
     * @return 卡商代理
     */
    @Override
    public List<Agentcenter> profilesinfo(Agentcenter tBankcardAgent) {
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        tBankcardAgent.setSupplierBranchId(userSupplierbranchid);
        tBankcardAgent.setAgentName(UtilsUser.getLoginName());
        return tBankcardAgentMapper.selectAgentcenterList(tBankcardAgent);
    }

    /**
     * 新增卡商代理
     *
     * @param tBankcardAgent 卡商代理
     * @return 结果
     */
    @Autowired
    private ISysUserService userService;
    @Autowired
    GlobalSetup globalSetup;

    @Override
    @Transactional
    public int insertAgentcenter(Agentcenter tBankcardAgent) {
//        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        String agentName = tBankcardAgent.getAgentName();
        String curentsloginName = ShiroUtils.getLoginName();
        Long nextId = SnowflakeIdWorker.getNextId();
        Long branchId = tBankcardAgent.getSupplierBranchId();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String currentTime = ft.format(new Date());
//        String parentId = tBankcardAgent.getParentSupplierBranchId();
        //如果应用户不存在,就添加添加用户表
        SysUser sysUser = userService.selectUserByLoginName(agentName);
        if (sysUser == null) {
            SysUser user = new SysUser();
            user.setSupplierbranchid(branchId);
            user.setLoginName(agentName);
            user.setUserName(agentName);
            user.setRoleIds(new Long[]{3L});
            user.setPassword(PHPpassword.PHPpasswordHash(tBankcardAgent.getPassword()));
            user.setCreateBy(curentsloginName);
            user.setUpdateTime(new Date());
            String topcodetype = globalSetup.getTopcodetype();
            if ("googlecode".equals(topcodetype)) {
                user.setGooglecode(GoogleAuthenticator.generateSecretKey());
            }
            userService.insertUser(user);
        }
//        if (!StringUtils.isEmpty(branchName)){
//            int companyId = dictBankCommonService.getCompanyIdBySupplierBranchId(Long.parseLong(parentId));
//            //写入财务分支表
//            dictBankCommonService.insertSupplierBranch(branchId,companyId,branchName,1,ShiroUtils.getUserId(),currentTime,ShiroUtils.getUserId(),currentTime,parentId);
//        }

        sysUser = userService.selectUserByLoginName(agentName);
        //添加代理表
        tBankcardAgent.setId(nextId);
      //  tBankcardAgent.setSupplierBranchId(userSupplierbranchid);
        tBankcardAgent.setAgentName(agentName);
        tBankcardAgent.setCreateby(curentsloginName);
        tBankcardAgent.setUserId(sysUser.getUserId());
        tBankcardAgent.setParentAgentId(ShiroUtils.getAgentId());
        String parentId = dictBankCommonService.selectParentIdByBranchId(tBankcardAgent.getSupplierBranchId());
        tBankcardAgent.setParentSupplierBranchId(parentId);
        return tBankcardAgentMapper.insertAgentcenter(tBankcardAgent);
    }

    @Override
    @Transactional
    public int insertAgent(Agentcenter tBankcardAgent) {
        String agentName = tBankcardAgent.getAgentName();
        Long nextId = SnowflakeIdWorker.getNextId();
        //如果应用户不存在,就添加添加用户表
        SysUser sysUser = userService.selectUserByLoginName(agentName);
        if (sysUser == null) {
            SysUser user = new SysUser();
            user.setSupplierbranchid(tBankcardAgent.getSupplierbranchid());
            tBankcardAgent.setUserId(nextId);
            user.setLoginName(agentName);
            user.setUserName(agentName);
            user.setRoleIds(new Long[]{2L});
            user.setPassword(PHPpassword.PHPpasswordHash(tBankcardAgent.getPassword()));
            user.setCreateBy(tBankcardAgent.getCreateby());
            user.setUpdateTime(new Date());
            String topcodetype = globalSetup.getTopcodetype();
            if ("googlecode".equals(topcodetype)) {
                user.setGooglecode(GoogleAuthenticator.generateSecretKey());
            }
            userService.insertUser(user);
        }
        sysUser = userService.selectUserByLoginName(agentName);
        //添加代理表
        tBankcardAgent.setSupplierBranchId(tBankcardAgent.getSupplierbranchid());
        tBankcardAgent.setAgentName(agentName);
        tBankcardAgent.setCreateby(tBankcardAgent.getCreateby());
        tBankcardAgent.setUserId(sysUser.getUserId());
        tBankcardAgent.setParentAgentId(tBankcardAgent.getParentAgentId());
        return tBankcardAgentMapper.insertAgentcenter(tBankcardAgent);
    }

    /**
     * 修改卡商代理
     *
     * @param tBankcardAgent 卡商代理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateAgentcenter(Agentcenter tBankcardAgent) {
        String password = tBankcardAgent.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            SysUser user = userService.selectUserByLoginName(tBankcardAgent.getAgentName());

            if (user != null) {
                user.setRoleIds(new Long[]{user.getRoleId()});
                user.setPassword(PHPpassword.PHPpasswordHash(tBankcardAgent.getPassword()));
                userService.updateUser(user);
            }
        }
        return tBankcardAgentMapper.updateAgentcenter(tBankcardAgent);
    }

    /**
     *
     * @param tBankcardAgent
     * @return
     */
    @Override
    @Transactional
    public int transMoney(Agentcenter tBankcardAgent,Agentcreditorder agentcreditorder, double transMoney){
        //写入信用订单
        final double d = Math.random();
        final int i = (int)(d*100);
        String orderId = "ct" + new Date().getTime() + i;  //生成订单号
        agentcreditorder.setOrderid(orderId);
        agentcreditorder.setPayamount(transMoney);
        agentcreditorder.setPaidamount(transMoney);
        agentcreditorder.setAgentId(tBankcardAgent.getId());
        agentcreditorder.setAgentCode(tBankcardAgent.getAgentCode());
        agentcreditorder.setOrderType(2);
        agentcreditorder.setOrderstatus(Long.parseLong("3"));
        agentcreditorderService.insertAgentcreditorder(agentcreditorder);
        return tBankcardAgentMapper.updateAgentcenter(tBankcardAgent);
    }

    /**
     * 删除卡商代理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAgentcenterByIds(String ids) {

        return tBankcardAgentMapper.deleteAgentcenterByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除卡商代理信息
     *
     * @param id 卡商代理ID
     * @return 结果
     */
    @Override
    public int deleteAgentcenterById(Long id) {
        return tBankcardAgentMapper.deleteAgentcenterById(id);
    }


}
