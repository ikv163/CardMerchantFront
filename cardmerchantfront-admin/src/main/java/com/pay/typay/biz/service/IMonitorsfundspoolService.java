package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.Merchant;
import com.pay.typay.biz.domain.MonitorsVO;
import com.pay.typay.biz.domain.Monitorsfundspool;
import com.pay.typay.biz.domain.TBankcard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡池资金监控Service接口
 * 
 * @author oswald
 * @date 2020-01-21
 */
public interface IMonitorsfundspoolService 
{
    /**
     * 查询卡池资金监控
     * 
     * @param bankid 卡池资金监控ID
     * @return 卡池资金监控
     */
     Monitorsfundspool selectMonitorsfundspoolById(Long bankid);

    /**
     * 查询卡池资金监控列表
     * 
     * @param tBankcard 卡池资金监控
     * @return 卡池资金监控集合
     */
     List<Monitorsfundspool> selectMonitorsfundspoolList(Monitorsfundspool tBankcard);

    List<TBankcard> getBankByTransit(TBankcard tBankcard);

    List<Monitorsfundspool> getTotalByCard(TBankcard tBankcard);

    /**
     * 银行卡可转资金监控（收款，出款，中转，下发）
     * @return
     */
    List<Monitorsfundspool> getBankMonitors(String[] bankType);

    /**
     * 中转卡缓冲卡
     * @return
     */
    List<Monitorsfundspool> getBufferCanTrans(String[] bankType,String calendar);

    /**
     * 入款---按层级
     * @return
     */
    List<MonitorsVO> getBankMonitorsNew(Merchant merchant);
}
