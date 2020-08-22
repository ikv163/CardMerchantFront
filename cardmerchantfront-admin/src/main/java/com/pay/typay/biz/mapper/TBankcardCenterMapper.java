package com.pay.typay.biz.mapper;


import com.pay.typay.biz.domain.TBankcardcenter;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 银行卡Mapper接口
 * 
 * @author oswald
 * @date 2020-01-06
 */
public interface TBankcardCenterMapper
{
    /**
     * 查询银行卡
     * 
     * @param bankid 银行卡ID
     * @return 银行卡
     */
    public TBankcardcenter selectTBankcardById(Long bankid,String supplierbranchid);

    public List<TBankcardcenter> getSummary(TBankcardcenter tBankcard);

    /**
     * 查询银行卡列表
     * 
     * @param tBankcard 银行卡
     * @return 银行卡集合
     */
    List <TBankcardcenter> selectTBankcardList(TBankcardcenter tBankcard);
    List <TBankcardcenter> selectListByWorktype(TBankcardcenter tBankcard);
    List<TBankcardcenter> selectTBankcardListsum(TBankcardcenter tBankcard);
    /**
     * 新增银行卡
     * 
     * @param tBankcard 银行卡
     * @return 结果
     */
    int insertTBankcard(TBankcardcenter tBankcard);

    /**
     * 修改银行卡
     * 
     * @param tBankcard 银行卡
     * @return 结果
     */
    int updateTBankcard(TBankcardcenter tBankcard);
    int updateTBankcardmutiple(TBankcardcenter tBankcard);//1
    int updateCardSupplierBranch(TBankcardcenter tBankcard);
    int updateCardDepositAmount(TBankcardcenter tBankcard);

    /**
     * 删除银行卡
     * 
     * @param bankid 银行卡ID
     * @return 结果
     */
    int deleteTBankcardById(Long bankid);

    /**
     * 批量删除银行卡
     * 
     * @param bankids 需要删除的数据ID
     * @return 结果
     */
    int deleteTBankcardByIds(String[] bankids);


    List<TBankcardcenter> selectTBankcardListByPool(TBankcardcenter tBankcard);

    List<TBankcardcenter> getBankcardTotal(TBankcardcenter tBankcard);

    List<TBankcardcenter> selectListByPool(TBankcardcenter tBankcard);

    int resetBankcardPool(@PathVariable("poolid") Long bankpooid);
}
