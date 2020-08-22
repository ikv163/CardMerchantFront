package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.BankcardChannelDto;
import com.pay.typay.biz.domain.TBankcard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 银行卡Mapper接口
 * 
 * @author Warren
 * @date 2020-01-05
 */
@Repository
public interface TBankcardMapper 
{

    /**
     * 查询中转卡列表
     * 
     * @param tBankcard 中转卡
     * @return 中转卡集合
     */
    List <TBankcard> fetchTransitCardList(TBankcard tBankcard);

    /**
     * 批量更新银行卡状态
     * @param bankcards
     * @return
     */
    int updateTBankcard(@Param("bankcards") List<TBankcard> bankcards);

    /**
     *
     * @param bankids
     * @return
     */
    List<TBankcard> selectTBankcardsByIds(@Param("bankids") List bankids);

    /**
     * 申请资金、修改状态查询用
     * @param bankids
     * @return
     */
    List<TBankcard> selectBankcardByIds(@Param("bankids") List bankids);

    /**
     *
     * @param bankcards
     * @return
     */
    int updateTBankcardById(TBankcard bankcards);


    List<BankcardChannelDto> findBankCardChannes(@Param("WorkType") int workType, @Param("PayAmount") BigDecimal payAmount, @Param("Calendar") String Calendar, @Param("bankIDs") List<Long> bankIDs);

    List<TBankcard> selectPBankcardsByIds(@Param("bankids") List<Long> ids, Integer worktype, BigDecimal payAmount);

    List<TBankcard> fetchTransitCard(TBankcard tBankcard);
}
