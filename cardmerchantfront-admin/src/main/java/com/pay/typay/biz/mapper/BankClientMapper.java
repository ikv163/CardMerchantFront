package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.BankClient;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 银行卡客户端Mapper接口
 * 
 * @author js-bucky
 * @date 2020-01-08
 */
public interface BankClientMapper 
{
    /**
     * 查询银行卡客户端
     * 
     * @param clientId 银行卡客户端ID
     * @return 银行卡客户端
     */
    public BankClient selectBankClientById(Long clientId,String supplierBranchId);

    /**
     * 查询银行卡客户端列表
     * 
     * @param bankClient 银行卡客户端
     * @return 银行卡客户端集合
     */
    List <BankClient> selectBankClientList(BankClient bankClient);

    List <BankClient> check(BankClient bankClient);

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
    int insertBankClient(BankClient bankClient);

    /**
     * 修改银行卡客户端
     * 
     * @param bankClient 银行卡客户端
     * @return 结果
     */
    int updateBankClient(BankClient bankClient);

    /**
     * 删除银行卡客户端
     * 
     * @param clientId 银行卡客户端ID
     * @return 结果
     */
    int deleteBankClientById(Long clientId);

    /**
     * 批量删除银行卡客户端
     * 
     * @param clientIds 需要删除的数据ID
     * @return 结果
     */
    int deleteBankClientByIds(String[] clientIds);
    int updateStatusByIds(String[] clientIds);

    int batchUpdateClient(@Param("status") Integer status,@Param("bankcodes")  String[]  bankcodes);
}
