package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.TBanktrans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 银行卡流水Mapper接口
 * 
 * @author Warren
 * @date 2020-01-08
 */
public interface TBanktransMapper 
{
    /**
     * 查询银行卡流水
     * 
     * @param id 银行卡流水ID
     * @return 银行卡流水
     */
    TBanktrans selectTBanktransById(Long id);

    /**
     * 查询银行卡流水
     * @param ids 银行卡流水ID
     * @return 银行卡流水列表
     */
    List<TBanktrans> selectBankTransByIds(@Param("ids") List ids);

    /**
     * 查询银行卡流水列表
     * 
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水集合
     */
    List <TBanktrans> selectTBanktransList(TBanktrans tBanktrans);

    List<TBanktrans> selectTotalBanktransList(TBanktrans tBanktrans);

    /**
     * 新增银行卡流水
     * 
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
    int insertTBanktrans(TBanktrans tBanktrans);

    /**
     * 修改银行卡流水
     * 
     * @param banktrans 银行卡流水
     * @return 结果
     */
    int updateTBanktrans(@Param("banktrans") List<TBanktrans> banktrans);

    /**
     * 删除银行卡流水
     * 
     * @param id 银行卡流水ID
     * @return 结果
     */
    int deleteTBanktransById(Long id);

    /**
     * 批量删除银行卡流水
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTBanktransByIds(String[] ids);

    /**
     *  出款卡流水列表
     * @param tBanktrans
     * @return
     */
    List<TBanktrans> selectWithdrawtransList(TBanktrans tBanktrans);

    List<TBanktrans> selectEBanktransList(TBanktrans tBanktrans);

    List<TBanktrans> selectWithdrawtransListTotal(TBanktrans tBanktrans);
}
