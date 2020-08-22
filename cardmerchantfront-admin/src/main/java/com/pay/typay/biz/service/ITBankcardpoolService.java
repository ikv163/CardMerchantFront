package com.pay.typay.biz.service;


import com.pay.typay.biz.domain.TBankcardpool;

import java.util.List;

/**
 * 银行卡池Service接口
 *
 * @author Warren
 * @date 2020-01-05
 */

public interface ITBankcardpoolService
{

    /**
     * 查询银行卡池列表
     *
     * @param tBankcardpool 银行卡池
     * @return 银行卡池集合
     */
    List<TBankcardpool> selectBankcardPoolList(TBankcardpool tBankcardpool);

    /**
     * 查询银行卡池
     *
     * @param bankpooid 银行卡池ID
     * @return 银行卡池
     */
    public TBankcardpool selectTBankcardpoolById(Long bankpooid);

    /**
     * 查询银行卡池列表
     *
     * @param tBankcardpool 银行卡池
     * @return 银行卡池集合
     */
     List<TBankcardpool> selectTBankcardpoolList(TBankcardpool tBankcardpool);

    /**
     * 新增银行卡池
     *
     * @param tBankcardpool 银行卡池
     * @return 结果
     */
    public int insertTBankcardpool(TBankcardpool tBankcardpool);

    /**
     * 修改银行卡池
     *
     * @param tBankcardpool 银行卡池
     * @return 结果
     */
    public int updateTBankcardpool(TBankcardpool tBankcardpool);

    /**
     * 批量删除银行卡池
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTBankcardpoolByIds(String ids);

    /**
     * 删除银行卡池信息
     *
     * @param bankpooid 银行卡池ID
     * @return 结果
     */
    public int deleteTBankcardpoolById(Long bankpooid);

    List<TBankcardpool> selectBankcardPoolListsum(TBankcardpool tBankcardpool);

    List<TBankcardpool> selectBankcardPoolListcheck(TBankcardpool tBankcardpool);

}
