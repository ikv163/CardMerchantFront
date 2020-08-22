package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.Merchant;
import com.pay.typay.biz.domain.MonitorsVO;
import com.pay.typay.biz.domain.Monitorsfundspool;
import com.pay.typay.biz.domain.TBankcard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡池资金监控Mapper接口
 * 
 * @author oswald
 * @date 2020-01-21
 */
public interface MonitorsfundspoolMapper 
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

    List<Monitorsfundspool> getToatlBalance(TBankcard tBankcard);

    List<TBankcard> getBankByTransit(TBankcard tBankcard);

    /**
     * 银行卡可转资金监控（收款，出款，中转，下发）
     * @return
     */
    List<Monitorsfundspool> getBankMonitors(@Param("bankType")String[] bankType,@Param("supplierbranchid")Long supplierbranchid);

    /**
     * 中转卡缓冲卡
     * @return
     */
    List<Monitorsfundspool> getBufferCanTrans(@Param("bankType")String[] bankType,@Param("calendar")String calendar,@Param("supplierbranchid")Long supplierbranchid);

    /**
     * 入款---按层级
     * @return
     */
    List<MonitorsVO> getBankMonitorsNew(Merchant merchant);
}
