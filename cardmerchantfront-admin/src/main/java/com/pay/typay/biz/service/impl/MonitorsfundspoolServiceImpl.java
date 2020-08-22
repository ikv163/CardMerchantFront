package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.Merchant;
import com.pay.typay.biz.domain.MonitorsVO;
import com.pay.typay.biz.domain.Monitorsfundspool;
import com.pay.typay.biz.domain.TBankcard;
import com.pay.typay.biz.mapper.MonitorsfundspoolMapper;
import com.pay.typay.biz.service.IMonitorsfundspoolService;
import com.pay.typay.common.annotation.DataScope;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;

import com.pay.typay.utils.UtilsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 卡池资金监控Service业务层处理
 *
 * @author oswald
 * @date 2020-01-21
 */
@Service
@DataSource(DataSourceType.typayv2)
public class MonitorsfundspoolServiceImpl implements IMonitorsfundspoolService {
    @Autowired
    private MonitorsfundspoolMapper tBankcardMapper;

    /**
     * 查询卡池资金监控
     *
     * @param bankid 卡池资金监控ID
     * @return 卡池资金监控
     */
    @Override
    public Monitorsfundspool selectMonitorsfundspoolById(Long bankid) {
        return tBankcardMapper.selectMonitorsfundspoolById(bankid);
    }

    /**
     * 查询卡池资金监控列表
     *
     * @param tBankcard 卡池资金监控
     * @return 卡池资金监控
     */
    @Override

    public List<Monitorsfundspool> selectMonitorsfundspoolList(Monitorsfundspool tBankcard) {
        return tBankcardMapper.selectMonitorsfundspoolList(tBankcard);
    }


    @Override

    public List<TBankcard> getBankByTransit(TBankcard tBankcard) {
        return tBankcardMapper.getBankByTransit(tBankcard);
    }

    @Override

    public List<Monitorsfundspool> getTotalByCard(TBankcard tBankcard) {
        return tBankcardMapper.getToatlBalance(tBankcard);
    }

    @Override
    public List<Monitorsfundspool> getBankMonitors(String[] bankType) {
        return tBankcardMapper.getBankMonitors(bankType, UtilsUser.getUserSupplierbranchid());
    }

    @Override
    public List<Monitorsfundspool> getBufferCanTrans(String[] bankType,String calendar) {
        return tBankcardMapper.getBufferCanTrans(bankType,calendar, UtilsUser.getUserSupplierbranchid());
    }

    /**
     * 入款---按层级
     * @return
     */
    @Override
    public List<MonitorsVO> getBankMonitorsNew(Merchant merchant){
        return tBankcardMapper.getBankMonitorsNew(merchant);
    }
}
