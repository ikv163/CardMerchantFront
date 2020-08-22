package com.pay.typay.biz.dict;


import com.pay.typay.biz.domain.*;
import com.pay.typay.biz.mapper.OrdersCommonMapper;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysUserService;
import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 银行卡类型Service接口
 *
 * @author jsbucky
 * @date 2020-01-20
 */

@Service("dictorders")
public class DictOrdersCommonService {


    @Resource
    private OrdersCommonMapper ordersCommonMapper;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 银行卡存款-支付渠道
     */
    @DataSource(DataSourceType.typayv2)
    public List<Paychannel> getPaychannelByDeposit() {
        return ordersCommonMapper.getPaychannelByDeposit(UtilsUser.getUserSupplierbranchid());
    }
    /**
     * 银行卡存款-支付渠道
     */
    @DataSource(DataSourceType.typayv2)
    public List<Paychannel> getPaychannelName() {
        return ordersCommonMapper.getPaychannelName(UtilsUser.getUserSupplierbranchid());
    }
    /**
     *银行卡提款-支付渠道
     */
    @DataSource(DataSourceType.typayv2)
    public List<Paychannel> getPaychannelBywithdraw() {
        return ordersCommonMapper.getPaychannelBywithdraw(UtilsUser.getUserSupplierbranchid());
    }
    /**
     * 三方存款-支付渠道
     */
    @DataSource(DataSourceType.typayv2)
    public List<Paychannel> getThirdPaychannelByDeposit() {
        return ordersCommonMapper.getThirdPaychannelByDeposit(UtilsUser.getUserSupplierbranchid());
    }
    /**
     *三方提款-支付渠道
     */
    @DataSource(DataSourceType.typayv2)
    public List<Paychannel> getThirdPaychannelBywithdraw() {
        return ordersCommonMapper.getThirdPaychannelBywithdraw(UtilsUser.getUserSupplierbranchid());
    }




    @DataSource(DataSourceType.typayv2)
    public List<Merchant> getMerchantList() {
        List<Merchant> list = ordersCommonMapper.getMerchantList(UtilsUser.getUserSupplierbranchid());
        return list;
    }

    @DataSource(DataSourceType.typayv2)
    public List<Thirdmerchant> geThirdtMerchantList() {
        List<Thirdmerchant> list = ordersCommonMapper.geThirdtMerchantList(UtilsUser.getUserSupplierbranchid());
        return list;
    }


    @DataSource(DataSourceType.typayv2)
    public List<Paytype> getPayTypeList(){
        return ordersCommonMapper.getPayTypeList();

    }

    /**
     * 查询银行卡类型
     *
     * @return 银行卡类型
     */
    @DataSource(DataSourceType.tyadmin)
    public List<Admin> getAdminList() {
        List<Admin> list = ordersCommonMapper.getAdminList();
        return list;
    }
    /**
     * 查询银行卡类型
     *
     * @return 银行卡类型
     */
    public List<SysUser> getSysuser() {
        SysUser sysUser = new SysUser();
        sysUser.setSupplierbranchid(UtilsUser.getUserSupplierbranchid());
        List<SysUser> list = sysUserService.selectUserList(sysUser);
        return list;
    }
    /**
     * 查询银行卡类型
     *
     * @return 银行卡类型
     */
    public String getCurrentSysuserName() {
        String userName = UtilsUser.getUserName();
        return userName;
    }

}


