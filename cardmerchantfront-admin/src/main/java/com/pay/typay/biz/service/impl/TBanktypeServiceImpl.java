package com.pay.typay.biz.service.impl;

import java.util.List;

import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.typay.biz.mapper.TBanktypeMapper;
import com.pay.typay.biz.domain.TBanktype;
import com.pay.typay.biz.service.ITBanktypeService;
import com.pay.typay.common.core.text.Convert;

/**
 * 银行卡类型Service业务层处理
 * 
 * @author Warren
 * @date 2020-01-06
 */
@Service
public class TBanktypeServiceImpl implements ITBanktypeService
{
    @Autowired
    private TBanktypeMapper tBanktypeMapper;

    /**
     * 查询银行卡类型
     * 
     * @param banktypeid 银行卡类型ID
     * @return 银行卡类型
     */
    @Override
    public TBanktype selectTBanktypeById(Long banktypeid)
    {
        return tBanktypeMapper.selectTBanktypeById(banktypeid);
    }

    /**
     * 查询银行卡类型列表
     * 
     * @return 银行卡类型
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public List<TBanktype> selectTBanktypeList()
    {
        return tBanktypeMapper.selectTBanktypeList();
    }

    /**
     * 新增银行卡类型
     * 
     * @param tBanktype 银行卡类型
     * @return 结果
     */
    @Override
    public int insertTBanktype(TBanktype tBanktype)
    {
        return tBanktypeMapper.insertTBanktype(tBanktype);
    }

    /**
     * 修改银行卡类型
     * 
     * @param tBanktype 银行卡类型
     * @return 结果
     */
    @Override
    public int updateTBanktype(TBanktype tBanktype)
    {
        return tBanktypeMapper.updateTBanktype(tBanktype);
    }

    /**
     * 删除银行卡类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTBanktypeByIds(String ids)
    {
        return tBanktypeMapper.deleteTBanktypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除银行卡类型信息
     * 
     * @param banktypeid 银行卡类型ID
     * @return 结果
     */
    @Override
    public int deleteTBanktypeById(Long banktypeid)
    {
        return tBanktypeMapper.deleteTBanktypeById(banktypeid);
    }
}
