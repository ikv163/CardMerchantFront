package com.pay.typay.biz.bankcard.service.impl;

import java.util.List;

import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.typay.biz.bankcard.mapper.StatementcenterMapper;
import com.pay.typay.biz.bankcard.domain.Statementcenter;
import com.pay.typay.biz.bankcard.service.IStatementcenterService;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
/**
 * 银行卡流水Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
@Service
@DataSource(DataSourceType.typayv2)
public class StatementcenterServiceImpl implements IStatementcenterService 
{
    @Autowired
    private StatementcenterMapper tBanktransMapper;

    /**
     * 查询银行卡流水
     * 
     * @param id 银行卡流水ID
     * @return 银行卡流水
     */
    @Override
    public Statementcenter selectStatementcenterById(Long id)
    {
        return tBanktransMapper.selectStatementcenterById(id);
    }

    public List<Statementcenter> getTotal(Statementcenter tBanktrans) {
        return tBanktransMapper.getTotal(tBanktrans);
    }

    /**
     * 查询银行卡流水列表
     * 
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水
     */
    @Override
    public List<Statementcenter> selectStatementcenterList(Statementcenter tBanktrans)
    {
        return tBanktransMapper.selectStatementcenterList(tBanktrans);
    }

    /**
     * 新增银行卡流水
     * 
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
    @Override
    public int insertStatementcenter(Statementcenter tBanktrans)
    {
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        tBanktrans.setSupplierbranchid(userSupplierbranchid);
        return tBanktransMapper.insertStatementcenter(tBanktrans);
    }

    /**
     * 修改银行卡流水
     * 
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
    @Override
    public int updateStatementcenter(Statementcenter tBanktrans)
    {
        return tBanktransMapper.updateStatementcenter(tBanktrans);
    }

    /**
     * 删除银行卡流水对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStatementcenterByIds(String ids)
    {
        return tBanktransMapper.deleteStatementcenterByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除银行卡流水信息
     * 
     * @param id 银行卡流水ID
     * @return 结果
     */
    @Override
    public int deleteStatementcenterById(Long id)
    {
        return tBanktransMapper.deleteStatementcenterById(id);
    }
}
