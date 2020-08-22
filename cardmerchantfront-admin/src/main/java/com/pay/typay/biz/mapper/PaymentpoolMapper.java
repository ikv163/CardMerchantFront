package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.Paymentpool;
import com.pay.typay.biz.domain.Paypoolchannel;
import com.pay.typay.common.core.domain.Ztree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 财务分部支付池Mapper接口
 *
 * @author oswald
 * @date 2020-01-19
 */
public interface PaymentpoolMapper {
    /**
     * 查询财务分部支付池
     *
     * @param paypoolid 财务分部支付池ID
     * @return 财务分部支付池
     */
    Paymentpool selectPaymentpoolById(Long paypoolid);

    /**
     * 查询财务分部支付池列表
     *
     * @param tPaypool 财务分部支付池
     * @return 财务分部支付池集合
     */
    List<Paymentpool> selectPaymentpoolList(Paymentpool tPaypool);

    /**
     * 新增财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    int insertPaymentpool(Paymentpool tPaypool);

    /**
     * 修改财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    int updatePaymentpool(Paymentpool tPaypool);

    /**
     * 删除财务分部支付池
     *
     * @param paypoolid 财务分部支付池ID
     * @return 结果
     */
    int deletePaymentpoolById(Long paypoolid);

    /**
     * 批量删除财务分部支付池
     *
     * @param paypoolids 需要删除的数据ID
     * @return 结果
     */
    int deletePaymentpoolByIds(String[] paypoolids);

    List<Paypoolchannel> selectPaymentpoolpaychannellist(Paymentpool tPaypool);

    List<Paypoolchannel> selectPaypoolchannelByPoolidlist(Long id);

    @Update("UPDATE t_paypoolchannel SET Status=#{status} WHERE PayChannelID=#{paychannelid}")
    int updatePaymentpoolStatus(@Param("status") Long status, @Param("paychannelid") Long paychannelid);


    List<Ztree> selectTreepoolchanneltreedata(Long supplierbranchid);
//    List<Ztree> selectTreepoolchanneltreedataChoose(@Param("supplierbranchid") Long supplierbranchid,@Param("paypoolid") Long paypoolid);
    List<Ztree> selectTreepoolchanneltreedataChoose(Paymentpool paymentpool);

    int updatepoolchanneldeleteFirst(Paypoolchannel tPaypool);

    Paypoolchannel selectpoolchanneldeleteFirst(Paypoolchannel tPaypool);

    int updatepoolchannel(Paypoolchannel tPaypool);



    int updatePaypoolchannelClean(@Param("paypoolid") Long paypoolid, @Param("paychannelids") String paychannelids);

    int insertPaypoolchannel(@Param("paypoolid") Long paypoolid, @Param("paychannelids") String paychannelids);
}
