package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.BankcardDaytradeReport;
import java.util.List;

/**
 * 银行卡日交易报表Service接口
 * 
 * @author oswald
 * @date 2020-01-09
 */
public interface IBankcardDaytradeReportService 
{
    /**
     * 查询银行卡日交易报表
     * 
     * @param bankid 银行卡日交易报表ID
     * @return 银行卡日交易报表
     */
    public BankcardDaytradeReport selectBankcardDaytradeReportById(Long bankid);

    /**
     * 查询银行卡日交易报表列表
     * 
     * @param tBankcardsummary 银行卡日交易报表
     * @return 银行卡日交易报表集合
     */
    public List<BankcardDaytradeReport> selectBankcardDaytradeReportList(BankcardDaytradeReport tBankcardsummary);


}
