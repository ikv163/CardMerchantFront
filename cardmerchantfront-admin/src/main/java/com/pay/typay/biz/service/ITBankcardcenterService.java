package com.pay.typay.biz.service;


import com.pay.typay.biz.domain.TBankcardcenter;
import com.pay.typay.biz.domain.TBankcardsummary;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 银行卡Service接口
 * 
 * @author oswald
 * @date 2020-01-06
 */
public interface ITBankcardcenterService
{
    /**
     * 查询银行卡
     * 
     * @param bankid 银行卡ID
     * @return 银行卡
     */
    public TBankcardcenter selectTBankcardById(Long bankid);

    List<TBankcardcenter> getSummary(TBankcardcenter tBankcard);

    /**
     * 查询银行卡列表
     * 
     * @param tBankcard 银行卡
     * @return 银行卡集合
     */
    public List<TBankcardcenter> selectTBankcardList(TBankcardcenter tBankcard);
    List<TBankcardcenter> selectCheck(TBankcardcenter tBankcard);



    public List<TBankcardcenter> selectListByWorktype(TBankcardcenter tBankcard);

    public List<TBankcardcenter> selectTBankcardListsum(TBankcardcenter tBankcard);

    /**
     * 新增银行卡
     * 
     * @param tBankcard 银行卡
     * @return 结果
     */
    public int insertTBankcard(TBankcardcenter tBankcard);

    /**
     * 修改银行卡
     *
     * @param tBankcard 银行卡
     * @return 结果
     */
    public int updateTBankcard(TBankcardcenter tBankcard);

    @Transactional
    int updateTBankcardmutiple(TBankcardcenter tBankcard);//1

    @Transactional
    int updateCardDepositAmount(TBankcardcenter tBankcard);//2

    /**
     * 修改银行卡
     *
     * @param tBankcard 银行卡
     * @return 结果
     */
    public int distupdateTBankcard(TBankcardcenter tBankcard);

    /**
     * 批量绑定卡池
     * @param tBankcard
     * @return
     */
    int distributionCardpool(TBankcardcenter tBankcard);

    /**
     * 批量删除银行卡
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTBankcardByIds(String ids);

    /**
     * 删除银行卡信息
     * 
     * @param bankid 银行卡ID
     * @return 结果
     */
    public int deleteTBankcardById(Long bankid);

    List<TBankcardcenter> selectTBankcardListByPool(TBankcardcenter tBankcard);


    /**
     * 修改银行卡财务分支
     *
     * @param tBankcard 银行卡
     * @return 结果
     */
    public int updateCardSupplierBranch(TBankcardcenter tBankcard);

    List<TBankcardcenter> getBankcardTotal(TBankcardcenter tBankcard);

    List<TBankcardsummary> selectSummaryByBank(Long bankid);

    List<TBankcardcenter> selectListByPool(TBankcardcenter tBankcard);

    int openAndClose(String bankids, Long status);
}
