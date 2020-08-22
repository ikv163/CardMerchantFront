package com.pay.typay.biz.dict;

import com.pay.typay.biz.domain.TBanktype;
import com.pay.typay.biz.service.ITBankcardpoolService;
import com.pay.typay.biz.service.ITBanktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * html调用 thymeleaf 实现字典读取
 *
 * @author Warren
 */
@Service("bank")
public class BankTypeDictService {
    @Autowired
    private ITBanktypeService banktypeService;


    @Autowired
    private ITBankcardpoolService bankcardpoolService;

//    /**
//     * 获取所有卡池
//     *
//     * @return 卡池列表
//     */
//    public List<TBankcardpool> fetchBankCardPools() {
//        return bankcardpoolService.selectTBankCardPoolList();
//    }

    /**
     * 获取银行类型列表
     *
     * @return 银行类型列表
     */
    public List<TBanktype> fetchBankTypes() {
        return banktypeService.selectTBanktypeList();
    }

}
