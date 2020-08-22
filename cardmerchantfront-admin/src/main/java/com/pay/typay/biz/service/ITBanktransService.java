package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.TBanktrans;
import com.pay.typay.biz.domain.TBanktransReversal;
import com.pay.typay.common.core.domain.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * 银行卡流水Service接口
 * 
 * @author Warren
 * @date 2020-01-08
 */
public interface ITBanktransService 
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
     * @param idsStr 银行卡流水ID
     * @return 银行卡流水列表
     */
    List<TBanktrans> selectBankTransByIds(String idsStr);

    /**
     * 查询银行卡流水列表
     *
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水集合
     */
    List<TBanktrans> selectTBanktransList(TBanktrans tBanktrans);

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
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
    int updateTBanktrans(List<TBanktrans> tBanktrans);

    /**
     * 批量删除银行卡流水
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTBanktransByIds(String ids);

    /**
     * 删除银行卡流水信息
     * 
     * @param id 银行卡流水ID
     * @return 结果
     */
    int deleteTBanktransById(Long id);

    /**
     * 手动匹配流水
     * @param url
     * @param banktrans
     * @return
     */
    int commit(String url, TBanktrans banktrans);

    /**
     * 修改订单号
     * @param url
     * @param idsStr
     * @param orderNumber
     * @return
     */
    int editMerchantOrderId(String url, String idsStr, String orderNumber);

    List<TBanktrans> selectWithdrawtransList(TBanktrans tBanktrans);

    List<TBanktrans> selectEBanktransList(TBanktrans tBanktrans);

    List<TBanktrans> selectTotalBanktransList(TBanktrans tBanktrans);

    List<TBanktrans> selectEBanktransListTotal(TBanktrans tBanktrans);

    List<TBanktrans> selectWithdrawtransListTotle(TBanktrans tBanktrans);



    Map<String,Object> selectReversalTransList(TBanktransReversal reversal,boolean totalType);

    AjaxResult rebaseorder(String queryTime);
}
