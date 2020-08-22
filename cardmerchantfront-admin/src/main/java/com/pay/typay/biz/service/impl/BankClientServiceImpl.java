package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.BankClient;
import com.pay.typay.biz.mapper.BankClientMapper;
import com.pay.typay.biz.service.IBankClientService;
import com.pay.typay.common.annotation.DataScope;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;

import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 银行卡客户端Service业务层处理
 *
 * @author js-bucky
 * @date 2020-01-08
 */
@Service
@DataSource(value = DataSourceType.tyadmin)
public class BankClientServiceImpl implements IBankClientService {
    @Autowired
    private BankClientMapper bankClientMapper;

    /**
     * 查询银行卡客户端
     *
     * @param clientId 银行卡客户端ID
     * @return 银行卡客户端
     */
    @Override
    public BankClient selectBankClientById(Long clientId) {
        return bankClientMapper.selectBankClientById(clientId,ShiroUtils.getSupplierbranchid().toString());
    }

    /**
     * 查询银行卡客户端列表
     *
     * @param bankClient 银行卡客户端
     * @return 银行卡客户端
     */
    @Override

    public List<BankClient> selectBankClientList(BankClient bankClient) {
        return bankClientMapper.selectBankClientList(bankClient);
    }

    @Override
    public List<BankClient> check(BankClient bankClient) {
        //bankClient.setSupplierBranchID( ShiroUtils.getSysUser().getSupplierbranchid());
        return bankClientMapper.check(bankClient);
    }

    @Override
    public BankClient selectClientByBanckAccount(String bankAccount) {
        return bankClientMapper.selectClientByBanckAccount(bankAccount);
    }

    /**
     * 新增银行卡客户端
     *
     * @param bankClient 银行卡客户端
     * @return 结果
     */
    @Override
    public int insertBankClient(BankClient bankClient) {
            bankClient.setCreateTime(DateUtils.getNowDate());
        return bankClientMapper.insertBankClient(bankClient);
    }

    /**
     * 修改银行卡客户端
     *
     * @param bankClient 银行卡客户端
     * @return 结果
     */
    @Override
    public int updateBankClient(BankClient bankClient) {
        return bankClientMapper.updateBankClient(bankClient);
    }

    /**
     * 删除银行卡客户端对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBankClientByIds(String ids) {
        return bankClientMapper.deleteBankClientByIds(Convert.toStrArray(ids));
    }

    @Override
    public int updateStatusByIds(String ids) {
        return bankClientMapper.updateStatusByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除银行卡客户端信息
     *
     * @param clientId 银行卡客户端ID
     * @return 结果
     */
    @Override
    public int deleteBankClientById(Long clientId) {
        return bankClientMapper.deleteBankClientById(clientId);
    }

    @Override
    public int batchUpdateClient(String bankcodes, Integer status) {
        return bankClientMapper.batchUpdateClient(status, Convert.toStrArray(bankcodes));
    }
}
