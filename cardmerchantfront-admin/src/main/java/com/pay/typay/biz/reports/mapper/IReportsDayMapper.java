package com.pay.typay.biz.reports.mapper;

import com.pay.typay.biz.reports.domain.ReportsDayChildVO;
import com.pay.typay.biz.reports.vo.ReportsDayVO;
import com.pay.typay.biz.reports.vo.ReportsVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IReportsDayMapper {

    /**
     * 查询银行卡日交易报表列表
     *
     * @param reportsDayVO 银行卡日交易报表
     * @return 银行卡日交易报表集合
     */
    List<ReportsDayVO> selectReportsDayList(ReportsDayVO reportsDayVO);

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
     * 查询当前登录用户代理编码
     * @param userId
     * @return 代理编码
     */
    String selectAgentCodeByUserId(Long userId);

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

    List<ReportsDayChildVO> selectReportsDayChildList(com.pay.typay.biz.reports.domain.ReportsDayVO reportsDayVO);

    /**
     * 根据代理id查询用户总充值金额 总提款金额
     * @param agentId 代理id
     * @return
     */
    com.pay.typay.biz.reports.domain.ReportsDayVO selectDepositAmount(@Param("agentId") String agentId);
}
