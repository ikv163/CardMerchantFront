package com.pay.typay.biz.reports.service.impl;

import com.pay.typay.biz.reports.vo.ReportsDayVO;
import com.pay.typay.biz.reports.mapper.IReportsDayMapper;
import com.pay.typay.biz.reports.service.IReportsDayService;
import com.pay.typay.biz.reports.vo.ReportsVO;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysDictData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 银行卡日交易报表Service业务层处理
 * 
 * @author oswald
 * @date 2020-01-09
 */
@Service
@DataSource
public class ReportsDayServiceImpl implements IReportsDayService
{

    @Autowired
    private IReportsDayMapper reportsDayMapper;

    @Override
    public List<ReportsDayVO> selectReportsDayList(ReportsDayVO reportsDayVO,List<SysDictData> dictDataList) {
        reportsDayVO.setSupplierBranchId(ShiroUtils.getSupplierbranchid());
        reportsDayVO.setAgentId(ShiroUtils.getAgentId());

        List<ReportsDayVO> reports = reportsDayMapper.selectReportsDayList(reportsDayVO);
        return reports;
    }

    @Override
    public List<ReportsDayVO> selectAgentReportsDayList(ReportsDayVO reportsDayVO) {
        reportsDayVO.setSupplierBranchId(ShiroUtils.getSupplierbranchid());
        reportsDayVO.setAgentId(ShiroUtils.getAgentId());
        return reportsDayMapper.selectAgentReportsDayList(reportsDayVO);
    }

    @Override
    public BigDecimal getAgentWithdraw(ReportsDayVO reportsDayVO){
        return reportsDayMapper.getAgentWithdraw(reportsDayVO);
    }

    @Override
    public List<ReportsDayVO> selectReportsList(ReportsDayVO reportsVO) {
        if(reportsVO.getSupplierBranchId() == null){
            reportsVO.setSupplierBranchId(ShiroUtils.getSupplierbranchid());
        }
        return reportsDayMapper.selectReportsList(reportsVO);
    }

    @Override
    public List<ReportsDayVO> selectChildList(ReportsDayVO reportsVO) {
        reportsVO.setSupplierBranchId(ShiroUtils.getSupplierbranchid());
        return reportsDayMapper.selectChildList(reportsVO);
    }


    /**
     * 计算分润
     * @param report
     * @param reports
     */
    private BigDecimal getSplitProfit(ReportsDayVO report,List<ReportsDayVO> reports){
        List<ReportsDayVO> list = reports.stream().filter(v -> StringUtils.equals(report.getId(),v.getParentAgentid())).collect(Collectors.toList());
        if(!list.isEmpty()){
            //分润 = 充值金额 * （父级代理费率 - 子集代理费率）
            return list.stream().map(o -> {return o.getDepositAmount().multiply(report.getRatio().subtract(o.getRatio()));}).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }
}
