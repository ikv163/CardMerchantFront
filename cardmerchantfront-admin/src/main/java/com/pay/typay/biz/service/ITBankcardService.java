package com.pay.typay.biz.service;

import com.alibaba.fastjson.JSONArray;
import com.pay.typay.biz.domain.ParamSettingVO;
import com.pay.typay.biz.domain.TBankcard;

import java.util.List;

/**
 * 银行卡Service接口
 * 
 * @author Warren
 * @date 2020-01-05
 */
public interface ITBankcardService 
{
    /**
     * 查询银行卡
     *
     * @param bankids 银行卡ID集合
     * @return 银行卡集合
     */
    public List<TBankcard> selectTBankcardsByIds(List bankids);

    /**
     * 申请资金、修改状态查询用
     *
     * @param bankids 银行卡ID集合
     * @return 银行卡集合
     */
    public List<TBankcard> selectBankcardByIds(List bankids);

    /**
     * 查询中转卡
     * 
     * @param tBankcard 银行卡
     * @return 中转卡列表
     */
    public List<TBankcard> fetchTransitCardList(TBankcard tBankcard);

    /**
     * 批量更新银行卡状态
     * @param bankcards
     * @return
     */
    public int updateTBankcard(List<TBankcard> bankcards);

    /**
     *
     * @param bankcards
     * @return
     */
    public int updateTBankcardById(TBankcard bankcards);

    /**
     * 测卡
     * @param bankids
     * @param money
     * @param url
     * @return
     */
    public int checkCard(String bankids, String money,String url);

    /**
     * 重置
     * @param bankcardids
     * @return
     */
    public int reset(List bankcardids);

    /**
     * 开关
     * @param bankids
     * @param status
     * @return
     */
    public int openAndClose(String bankids,Long status);

    /**
     * 上下卡
     * @param bankids
     * @param status
     * @return
     */
    public JSONArray downAndUp(String bankids, Long status);

    /**
     * 只抓同行
     * @param status
     * @return
     */
    int onlySameBank(Long status);

    /**
     * 申请资金
     * @param bankids
     * @param money
     * @param perMinTrans
     * @param url
     * @return
     */
    int asktrans(String bankids, String money, String perMinTrans, String url);

    int paramSetting(ParamSettingVO paramSetting);

    ParamSettingVO getParam();

    List<TBankcard> fetchTransitCard(TBankcard tBankcard);

    int stopWithdraw(Integer Status);

    Boolean getWithdrawStatus();
}
