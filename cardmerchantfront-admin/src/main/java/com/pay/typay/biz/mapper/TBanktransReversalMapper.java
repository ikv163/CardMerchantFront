package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.TBanktransReversal;
import com.pay.typay.biz.reports.domain.BankAccountRecord;

import java.util.List;

/**
 * 银行卡流水Mapper接口
 * 
 * @author Warren
 * @date 2020-01-08
 */
public interface TBanktransReversalMapper
{

    /**
     *  出款卡流水列表
     * @param reversal
     * @return
     */
    List<TBanktransReversal> selectReversalTransList(TBanktransReversal reversal);

    TBanktransReversal selectMainTransList(TBanktransReversal reversal);



    int insertBankAccountRecord(BankAccountRecord record);
}
