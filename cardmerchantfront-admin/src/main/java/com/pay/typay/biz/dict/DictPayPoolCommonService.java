package com.pay.typay.biz.dict;


import com.pay.typay.biz.domain.Paypoolchannel;
import com.pay.typay.biz.mapper.PaymentpoolMapper;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行卡类型Service接口
 *
 * @author jsbucky
 * @date 2020-02-01
 */

@Service("dictpaypool")

public class DictPayPoolCommonService {
    @Autowired
    private PaymentpoolMapper paymentpoolMapper;
    /**
     * 支付渠道对象
     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public List<Paypoolchannel> getPaychannel(Long paypoolid){
        return paymentpoolMapper.selectPaypoolchannelByPoolidlist(paypoolid);
    }

}