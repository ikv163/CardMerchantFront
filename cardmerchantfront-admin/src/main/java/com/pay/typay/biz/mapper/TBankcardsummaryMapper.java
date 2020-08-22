package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.TBankcardsummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 银行卡记录Mapper接口
 * 
 * @author Warren
 * @date 2020-01-07
 */
public interface TBankcardsummaryMapper 
{
    /**
     * 查询银行卡记录
     * 
     * @param bankcards 银行卡记录ID
     * @return 银行卡记录
     */
    List <TBankcardsummary> selectTBankcardsummaryById(@Param("bankcards") List bankcards);

    /**
     * 查询银行卡记录列表
     * 
     * @param tBankcardsummary 银行卡记录
     * @return 银行卡记录集合
     */
    List <TBankcardsummary> selectTBankcardsummaryList(TBankcardsummary tBankcardsummary);

    /**
     * 修改银行卡记录
     * 
     * @param tBankcardsummary 银行卡记录
     * @return 结果
     */
    int updateTBankcardsummary(TBankcardsummary tBankcardsummary);

    /**
     * 批量修改
     * @param bankcardsummarys
     * @return
     */
    int updateTBankcardsummarys(@Param("bankcardsummarys") List bankcardsummarys);

}
