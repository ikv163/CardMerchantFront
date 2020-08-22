package com.pay.typay.biz.reports.service;

import com.pay.typay.biz.reports.vo.ReportsDayVO;
import com.pay.typay.biz.reports.vo.ReportsVO;
import com.pay.typay.system.domain.SysDictData;

import java.math.BigDecimal;
import java.util.List;

/**
 * 银行卡日交易报表Service接口
 * 
 * @author oswald
 * @date 2020-01-09
 */
public interface IReportsDayService
{

    /**
     * 查询日交易报表列表
     * 
     * @param reportsDayVO 银行卡日交易报表
     * @param dictDataList 层级字典
     * @return 日交易报表集合
     */
    List<ReportsDayVO> selectReportsDayList(ReportsDayVO reportsDayVO,List<SysDictData> dictDataList);

    /**
     * 个人中心 -- 代理汇总
     * @param reportsDayVO
     * @return
     */
    List<ReportsDayVO> selectAgentReportsDayList(ReportsDayVO reportsDayVO);

    /**
     * 代理已提金额
     * @param reportsDayVO
     * @return
     */
    BigDecimal getAgentWithdraw(ReportsDayVO reportsDayVO);

    /**
     * 查询收支报表
     *
     * @param reportsVO 收支报表
     * @return 收支报表集合
     */
    List<ReportsDayVO> selectReportsList(ReportsDayVO reportsVO);

    /**
     * 收支报表详情
     * @param reportsVO
     * @return
     */
    List<ReportsDayVO> selectChildList(ReportsDayVO reportsVO);
}
