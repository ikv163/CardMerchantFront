package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.BankClient;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;

import java.util.List;
import java.util.Map;

/**
 * 银行卡客户端Service接口
 * 
 * @author js-bucky
 * @date 2020-01-08
 */

public interface IBankClientService 
{
    /**
     * 查询银行卡客户端
     * 
     * @param clientId 银行卡客户端ID
     * @return 银行卡客户端
     */
    public BankClient selectBankClientById(Long clientId);

    /**
     * 查询银行卡客户端列表
     * 
     * @param bankClient 银行卡客户端
     * @return 银行卡客户端集合
     */
    public List<BankClient> selectBankClientList(BankClient bankClient);
    public List<BankClient> check(BankClient bankClient);

    /**
     * 查询客户端名称
     * @param bankAccount
     * @return
     */
    BankClient selectClientByBanckAccount(String bankAccount);
    /**
     * 新增银行卡客户端
     * 
     * @param bankClient 银行卡客户端
     * @return 结果
     */
    public int insertBankClient(BankClient bankClient);

    /**
     * 修改银行卡客户端
     * 
     * @param bankClient 银行卡客户端
     * @return 结果
     */
    public int updateBankClient(BankClient bankClient);

    /**
     * 批量删除银行卡客户端
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBankClientByIds(String ids);

    public int updateStatusByIds(String ids);

    /**
     * 删除银行卡客户端信息
     * 
     * @param clientId 银行卡客户端ID
     * @return 结果
     */
    public int deleteBankClientById(Long clientId);


    int batchUpdateClient(String bankcodes,Integer status);
}
