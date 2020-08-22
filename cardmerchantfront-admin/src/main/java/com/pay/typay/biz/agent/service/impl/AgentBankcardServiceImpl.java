package com.pay.typay.biz.agent.service.impl;

import com.pay.typay.biz.agent.domain.AgentBankcard;
import com.pay.typay.biz.agent.domain.Agentcenter;
import com.pay.typay.biz.agent.mapper.AgentBankcardMapper;
import com.pay.typay.biz.agent.mapper.AgentcenterMapper;
import com.pay.typay.biz.agent.service.IAgentBankcardService;
import com.pay.typay.biz.reports.domain.*;
import com.pay.typay.biz.reports.mapper.IReportsDayMapper;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.utils.SnowflakeIdWorker;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 代理银行卡Service业务层处理
 *
 * @author oswald
 * @date 2020-05-14
 */
@Service
@DataSource(DataSourceType.typayv2)
public class AgentBankcardServiceImpl implements IAgentBankcardService {
    @Autowired
    private AgentBankcardMapper tAgentbankcardMapper;

    /**
     * 查询代理银行卡
     *
     * @param bankid 代理银行卡ID
     * @return 代理银行卡
     */
    @Override
    public AgentBankcard selectAgentBankcardById(Long bankid) {
        return tAgentbankcardMapper.selectAgentBankcardById(bankid,ShiroUtils.getSupplierbranchid().toString());
    }

    /**
     * 查询代理银行卡
     *
     * @param bankcode 代理银行卡简码
     * @return 代理银行卡
     */
    @Override
    public AgentBankcard selectAgentBankcardByBankCode(String bankcode) {
        return tAgentbankcardMapper.selectAgentBankcardByBankCode(bankcode);
    }

    /**
     * 查询代理银行卡列表
     *
     * @param tAgentbankcard 代理银行卡
     * @return 代理银行卡
     */
    @Override
    public List<AgentBankcard> selectAgentBankcardList(AgentBankcard tAgentbankcard) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {  //如果不是管理员账号
            tAgentbankcard.setCreateby(sysUser.getLoginName());
        }

        tAgentbankcard.setSupplierbranchid(sysUser.getSupplierbranchid());
        AgentBankcard a = tAgentbankcard;
        return tAgentbankcardMapper.selectAgentBankcardList(tAgentbankcard);
    }

    /**
     * 查询代理银行卡列表
     *
     * @param tAgentbankcard 代理银行卡
     * @return 代理银行卡
     */
    @Override
    public List<AgentBankcard> selectAgentBankcardListAll(AgentBankcard tAgentbankcard) {
        return tAgentbankcardMapper.selectAgentBankcardList(tAgentbankcard);
    }

    /**
     * 新增代理银行卡
     *
     * @param tAgentbankcard 代理银行卡
     * @return 结果
     */
    @Override
    public int insertAgentBankcard(AgentBankcard tAgentbankcard) {
        Long nextId = SnowflakeIdWorker.getNextId();
        tAgentbankcard.setBankid(nextId);
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        tAgentbankcard.setSupplierbranchid(userSupplierbranchid);
        SysUser sysUser = ShiroUtils.getSysUser();
        tAgentbankcard.setCreateby(sysUser.getLoginName());
        tAgentbankcard.setAgentId(ShiroUtils.getAgentId());
        return tAgentbankcardMapper.insertAgentBankcard(tAgentbankcard);
    }

    /**
     * 修改代理银行卡
     *
     * @param tAgentbankcard 代理银行卡
     * @return 结果
     */
    @Override
    public int updateAgentBankcard(AgentBankcard tAgentbankcard) {
        return tAgentbankcardMapper.updateAgentBankcard(tAgentbankcard);
    }

    /**
     * 删除代理银行卡对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAgentBankcardByIds(String ids) {
        return tAgentbankcardMapper.deleteAgentBankcardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除代理银行卡信息
     *
     * @param bankid 代理银行卡ID
     * @return 结果
     */
    @Override
    public int deleteAgentBankcardById(Long bankid) {
        return tAgentbankcardMapper.deleteAgentBankcardById(bankid);
    }

    @Autowired
    private IReportsDayMapper reportsDayMapper;

    @Autowired
    private AgentcenterMapper agentcenterMapper;

    /**
     * 计算额度转换可提额度
     * @return
     */
    @Override
    public BigDecimal calcWithdrawOkAmount() {
        String agentId = ShiroUtils.getAgentId();
        Agentcenter agentcenter = agentcenterMapper.selectAgentcenterById(Long.valueOf(agentId),ShiroUtils.getSupplierbranchid().toString(),"");
        com.pay.typay.biz.reports.domain.ReportsDayVO reports = reportsDayMapper.selectDepositAmount(agentId);
        ReportsDayVO report = new ReportsDayVO();
        report.setParentAgentId(agentId);
        report.setSupplierBranchId(ShiroUtils.getSupplierbranchid());
        List<ReportsDayChildVO> childReports = reportsDayMapper.selectReportsDayChildList(report);
        List<ReportsDayChildVO> childList = TreeParser.getTreeList(report.getParentAgentId(),childReports);
        TempEntity tempEntity = ReportUtil.buildTempData(childList);
        Map<BigDecimal,BigDecimal> ratioMap = tempEntity.getMap();
        BigDecimal profit = reports.getDepositAmount().multiply(BigDecimal.valueOf(agentcenter.getRatio()).divide(new BigDecimal(100)));
        BigDecimal splitProfit = ReportUtil.getSplitProfit(BigDecimal.valueOf(agentcenter.getRatio()),ratioMap);
        return reports.getDepositAmount().subtract(reports.getWithdrawAmount()).add(profit).add(splitProfit);
    }
}
