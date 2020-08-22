package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.TBanktype;

import java.util.List;

/**
 * 银行卡类型Mapper接口
 * 
 * @author Warren
 * @date 2020-01-06
 */
public interface TBanktypeMapper 
{
    /**
     * 查询银行卡类型
     *
     * @param banktypeid 银行卡类型ID
     * @return 银行卡类型
     */
    public TBanktype selectTBanktypeById(Long banktypeid);

    /**
     * 查询银行卡类型列表
     * 
     * @return 银行卡类型集合
     */
    List <TBanktype> selectTBanktypeList();

    /**
     * 新增银行卡类型
     *
     * @param tBanktype 银行卡类型
     * @return 结果
     */
    int insertTBanktype(TBanktype tBanktype);

    /**
     * 修改银行卡类型
     *
     * @param tBanktype 银行卡类型
     * @return 结果
     */
    int updateTBanktype(TBanktype tBanktype);

    /**
     * 删除银行卡类型
     *
     * @param banktypeid 银行卡类型ID
     * @return 结果
     */
    int deleteTBanktypeById(Long banktypeid);

    /**
     * 批量删除银行卡类型
     *
     * @param banktypeids 需要删除的数据ID
     * @return 结果
     */
    int deleteTBanktypeByIds(String[] banktypeids);
}
