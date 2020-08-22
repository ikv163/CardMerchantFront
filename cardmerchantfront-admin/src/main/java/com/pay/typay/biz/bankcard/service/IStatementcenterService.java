package com.pay.typay.biz.bankcard.service;

import com.pay.typay.biz.bankcard.domain.Statementcenter;
import com.pay.typay.biz.domain.TBankcardcenter;

import java.util.List;

/**
 * 银行卡流水Service接口
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IStatementcenterService 
{
    /**
     * 查询银行卡流水
     * 
     * @param id 银行卡流水ID
     * @return 银行卡流水
     */
     Statementcenter selectStatementcenterById(Long id);

    List<Statementcenter> getTotal(Statementcenter tBanktrans);

    /**
     * 查询银行卡流水列表
     * 
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水集合
     */
     List<Statementcenter> selectStatementcenterList(Statementcenter tBanktrans);

    /**
     * 新增银行卡流水
     * 
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
     int insertStatementcenter(Statementcenter tBanktrans);

    /**
     * 修改银行卡流水
     * 
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
     int updateStatementcenter(Statementcenter tBanktrans);

    /**
     * 批量删除银行卡流水
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteStatementcenterByIds(String ids);

    /**
     * 删除银行卡流水信息
     * 
     * @param id 银行卡流水ID
     * @return 结果
     */
     int deleteStatementcenterById(Long id);
}
