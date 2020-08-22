package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.BankcardDaytradeReport;

import java.util.List;

/**
 * 银行卡日交易报表Mapper接口
 * 
 * @author oswald
 * @date 2020-01-09
 */
public interface BankcardDaytradeReportMapper 
{
    /**
     * 查询银行卡日交易报表
     * 
     * @param bankid 银行卡日交易报表ID
     * @return 银行卡日交易报表
     */
     BankcardDaytradeReport selectBankcardDaytradeReportById(Long bankid);

    /**
     * 查询银行卡日交易报表列表
     * 
     * @param tBankcardsummary 银行卡日交易报表
     * @return 银行卡日交易报表集合
     */
    List <BankcardDaytradeReport> selectBankcardDaytradeReportList(BankcardDaytradeReport tBankcardsummary);

}
