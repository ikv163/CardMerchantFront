package com.pay.typay.biz.mapper;


import com.pay.typay.biz.domain.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单字典apper接口
 *
 * @author jsbucky
 * @date 2020-01-20
 */
public interface OrdersCommonMapper {

    @Select("SELECT DISTINCT PayChannelID,PayChannelName FROM t_PayChannel WHERE " +
            " Status!=-1 and WorkType in (1,4,7) AND (ChannelMerchantID IS NULL or ChannelMerchantID='') " +
            " and SupplierBranchID=#{supplierbranchid} order by PayChannelName")
    List<Paychannel> getPaychannelByDeposit(Long supplierbranchid);



    @Select("SELECT DISTINCT PayChannelID,PayChannelName FROM t_PayChannel WHERE " +
            " Status!=-1 and WorkType= 1 AND (MerchantID IS NULL or ChannelMerchantID='') " +
            " and SupplierBranchID=#{supplierbranchid} order by PayChannelName")
    List<Paychannel> getPaychannelName(Long supplierbranchid);

    @Select("SELECT DISTINCT PayChannelID,PayChannelName FROM t_PayChannel WHERE " +
            " Status!=-1 and WorkType in (2,3,4,7) AND (ChannelMerchantID IS NULL or ChannelMerchantID='') " +
            " and SupplierBranchID=#{supplierbranchid} order by PayChannelName")
    List<Paychannel> getPaychannelBywithdraw(Long supplierbranchid);

    @Select("SELECT DISTINCT PayChannelID,PayChannelName FROM t_PayChannel WHERE " +
            " Status!=-1 and WorkType in (1,4,7) AND MerchantID > 0 and WriteThirdTable = 1 " +
            " and SupplierBranchID=#{supplierbranchid} order by PayChannelName")
    List<Paychannel> getThirdPaychannelByDeposit(Long supplierbranchid);

    @Select("SELECT DISTINCT PayChannelID,PayChannelName FROM t_PayChannel WHERE " +
            " Status!=-1 and WorkType in (2,3,4,7) AND MerchantID > 0 AND WriteThirdTable = 1 " +
            " and SupplierBranchID=#{supplierbranchid} order by PayChannelName")
    List<Paychannel> getThirdPaychannelBywithdraw(Long supplierbranchid);

    @Select("SELECT DISTINCT t1.MerchantID,t1.MerchantName  " +
            " FROM t_merchant t1 " +
            " LEFT JOIN t_merchantpaypool t2 on t1.MerchantID=t2.MerchantID " +
            " WHERE t1.Status<>-1 and t2.Status<>-1 and t2.SupplierBranchID=#{supplierbranchid} " +
            " order by MerchantName")
    List<Merchant> getMerchantList(Long supplierbranchid);


    @Select("SELECT DISTINCT t1.MerchantID,t1.MerchantName " +
            " FROM t_thirdmerchant t1 " +
            " LEFT JOIN t_paychannel p ON t1.MerchantID = p.MerchantID" +
            " WHERE p.SupplierBranchID = #{supplierbranchid} AND p.MerchantID IS NOT NULL  " +
            " order by t1.MerchantName;")
    List<Thirdmerchant> geThirdtMerchantList(Long supplierbranchid);



    @Select("SELECT DISTINCT id,username FROM tyadmin.admin WHERE status=0")
    List<Admin> getAdminList();

    @Select("SELECT DISTINCT PayTypeID,PayTypeName FROM t_paytype WHERE status!=-1 ORDER BY PayTypeName DESC")
    List<Paytype> getPayTypeList();

}
