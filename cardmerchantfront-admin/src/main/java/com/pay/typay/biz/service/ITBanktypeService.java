package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.TBanktype;
import java.util.List;

/**
 * 银行卡类型Service接口
 * 
 * @author Warren
 * @date 2020-01-06
 */
public interface ITBanktypeService 
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
    public List<TBanktype> selectTBanktypeList();

    /**
     * 新增银行卡类型
     * 
     * @param tBanktype 银行卡类型
     * @return 结果
     */
    public int insertTBanktype(TBanktype tBanktype);

    /**
     * 修改银行卡类型
     * 
     * @param tBanktype 银行卡类型
     * @return 结果
     */
    public int updateTBanktype(TBanktype tBanktype);

    /**
     * 批量删除银行卡类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTBanktypeByIds(String ids);

    /**
     * 删除银行卡类型信息
     * 
     * @param banktypeid 银行卡类型ID
     * @return 结果
     */
    public int deleteTBanktypeById(Long banktypeid);
}
