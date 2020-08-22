package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.BankcardDaytradeReport;
import com.pay.typay.biz.mapper.BankcardDaytradeReportMapper;
import com.pay.typay.biz.service.IBankcardDaytradeReportService;
import com.pay.typay.common.annotation.DataScope;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 银行卡日交易报表Service业务层处理
 * 
 * @author oswald
 * @date 2020-01-09
 */
@Service
public class BankcardDaytradeReportServiceImpl implements IBankcardDaytradeReportService
{
    @Autowired
    private BankcardDaytradeReportMapper tBankcardsummaryMapper;

    /**
     * 查询银行卡日交易报表
     * 
     * @param bankid 银行卡日交易报表ID
     * @return 银行卡日交易报表
     */
    @Override
    @DataSource(DataSourceType.typayv2)
    public BankcardDaytradeReport selectBankcardDaytradeReportById(Long bankid)
    {
        BankcardDaytradeReport bankcardDaytradeReport = tBankcardsummaryMapper.selectBankcardDaytradeReportById(bankid);
        return bankcardDaytradeReport;
    }

    /**
     * 查询银行卡日交易报表列表
     * 
     * @param tBankcardsummary 银行卡日交易报表
     * @return 银行卡日交易报表
     */
    @Override
    @DataSource(DataSourceType.typayv2slave)

    public List<BankcardDaytradeReport> selectBankcardDaytradeReportList(BankcardDaytradeReport tBankcardsummary)
    {
        return tBankcardsummaryMapper.selectBankcardDaytradeReportList(tBankcardsummary);
    }

}
