package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.TBankcardpool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 银行卡池Mapper接口
 *
 * @author Warren
 * @date 2020-01-05
 */
public interface TBankcardpoolMapper {

    /**
     * 查询银行卡池列表
     *
     * @return 银行卡池集合
     */
    List <TBankcardpool> selectTBankCardPoolList();
    List<TBankcardpool> selectBankcardPoolListsum(TBankcardpool tBankcardpool);
//    List <TBankcardpool> selectTBankCardPoolGroupList();
    /**
     * 查询银行卡池列表
     *
     * @param tBankcardpool 银行卡池
     * @return 银行卡池集合
     */
    List<TBankcardpool> selectBankcardPoolList(TBankcardpool tBankcardpool);
    List<TBankcardpool> selectBankcardPoolListCheck(TBankcardpool tBankcardpool);
    /**
     * 查询银行卡池列表
     *
     * @param tBankcardpool 银行卡池
     * @return 银行卡池集合
     */
    List <TBankcardpool> selectTBankcardpoolList(TBankcardpool tBankcardpool);

//    List <TBankcardpool> selectTBankcardPoolGroupList(TBankcardpool tBankcardpool);


    int updateBankcardPoolid(@Param("poolid") Long poolid, @Param("ids") String ids);

    int updateBankcardPoolidClean(@Param("poolid") Long poolid, @Param("ids") String ids);
    int updateBatchBankcardPoolidClean(String[] ids);

    /**
     * 查询银行卡池
     *
     * @param bankpooid 银行卡池ID
     * @return 银行卡池
     */
    public TBankcardpool selectTBankcardpoolById(Long bankpooid);


    /**
     * 新增银行卡池
     *
     * @param tBankcardpool 银行卡池
     * @return 结果
     */
    int insertTBankcardpool(TBankcardpool tBankcardpool);

    /**
     * 修改银行卡池
     *
     * @param tBankcardpool 银行卡池
     * @return 结果
     */
    int updateTBankcardpool(TBankcardpool tBankcardpool);

    /**
     * 删除银行卡池
     *
     * @param bankpooid 银行卡池ID
     * @return 结果
     */
    int deleteTBankcardpoolById(Long bankpooid);

    /**
     * 批量删除银行卡池
     *
     * @param bankpooids 需要删除的数据ID
     * @return 结果
     */
    int deleteTBankcardpoolByIds(String[] bankpooids);


}
