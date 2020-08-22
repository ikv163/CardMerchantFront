package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.Paymentpool;
import com.pay.typay.biz.domain.Paypoolchannel;
import com.pay.typay.biz.mapper.PaymentpoolMapper;
import com.pay.typay.biz.mapper.TBankCommonMapper;
import com.pay.typay.biz.service.IPaymentpoolService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.domain.Ztree;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 财务分部支付池Service业务层处理
 *
 * @author oswald
 * @date 2020-01-19
 */
@Service
@DataSource(DataSourceType.typayv2)
public class PaymentpoolServiceImpl implements IPaymentpoolService {
    @Autowired
    private PaymentpoolMapper tPaypoolMapper;

    @Autowired
    private PaymentpoolMapper paymentpoolMapper;

    /**
     * 查询财务分部支付池
     *
     * @param paypoolid 财务分部支付池ID
     * @return 财务分部支付池
     */
    @Override
    public Paymentpool selectPaymentpoolById(Long paypoolid) {
        return tPaypoolMapper.selectPaymentpoolById(paypoolid);
    }
    @Override
    public List<Paypoolchannel> selectPaypoolchannelByPoolidlist(Long paypoolid){
        return paymentpoolMapper.selectPaypoolchannelByPoolidlist(paypoolid);
    }
    /**
     * 查询财务分部支付池列表
     *
     * @param tPaypool 财务分部支付池
     * @return 财务分部支付池
     */
    @Override

    public List<Paymentpool> selectPaymentpoolList(Paymentpool tPaypool) {
        return tPaypoolMapper.selectPaymentpoolList(tPaypool);
    }


    /**
     * 查询财务分部支付池列表
     *
     * @param tPaypool 财务分部支付池
     * @return 财务分部支付池集合
     */
    @Override

    public List<Paypoolchannel> selectPaymentpoolpaychannellist(Paymentpool tPaypool) {
        return tPaypoolMapper.selectPaymentpoolpaychannellist(tPaypool);
    }

    /**
     * 新增财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    @Autowired
    TBankCommonMapper tBankCommonMapper;

/*    @Autowired
    private ISysUserService sysUserService;*/

/*    @Autowired
    private BankinchannelMapper bankinchannelMapper;*/

/*    private void adddatasope(Paymentpool tPaypool){
        if (tPaypool != null && StringUtils.isNotEmpty(tPaypool.getPaychannelids())){
            List<Bankinchannel> list = bankinchannelMapper.selectBankinchannelListByChannelids(Convert.toStrArray(tPaypool.getPaychannelids()));
            Set<Long> channelids = new HashSet<>();
            for (Bankinchannel bankinchannel : list) {
                channelids.add(bankinchannel.getBankid());
            }
            sysUserService.updateDatascopeByBankid(ShiroUtils.getUserId(),channelids);
        }
    }*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPaymentpool(Paymentpool tPaypool) {

        Paymentpool maxPaypoolID = tBankCommonMapper.getMaxPaypoolID();
        Long id = 1L;
        if (maxPaypoolID != null) {
            id = maxPaypoolID.getPaypoolid() + 1;

        }
        tPaypool.setPaypoolid(id);
        tPaypool.setCreateby(ShiroUtils.getUserId());
        //增加权限
//        adddatasope(tPaypool);

        //添加支付池渠道信息
        if (StringUtils.isNotEmpty(tPaypool.getPaychannelids())){
            tPaypoolMapper.updatePaypoolchannelClean(id,tPaypool.getPaychannelids());
            tPaypoolMapper.insertPaypoolchannel(id,tPaypool.getPaychannelids());
        }
        return tPaypoolMapper.insertPaymentpool(tPaypool);
    }

    /**
     * 修改财务分部支付池
     *
     * @param tPaypool 财务分部支付池
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePaymentpool(Paymentpool tPaypool) {
        //增加权限
//        adddatasope(tPaypool);
        tPaypoolMapper.updatePaypoolchannelClean(tPaypool.getPaypoolid(),tPaypool.getPaychannelids());

        //添加支付池渠道信息
        if (StringUtils.isNotEmpty(tPaypool.getPaychannelids())){
            tPaypoolMapper.insertPaypoolchannel(tPaypool.getPaypoolid(),tPaypool.getPaychannelids());
        }
        return tPaypoolMapper.updatePaymentpool(tPaypool);
    }

    @Override
    public int updatePaymentpoolStatus(Paypoolchannel tPaypool) {
        return tPaypoolMapper.updatePaymentpoolStatus(tPaypool.getStatus(), tPaypool.getPaychannelid());
    }

    /**
     * 删除财务分部支付池对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePaymentpoolByIds(String ids) {
        return tPaypoolMapper.deletePaymentpoolByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除财务分部支付池信息
     *
     * @param paypoolid 财务分部支付池ID
     * @return 结果
     */
    @Override
    public int deletePaymentpoolById(Long paypoolid) {
        return tPaypoolMapper.deletePaymentpoolById(paypoolid);
    }

    @Override
    public List<Ztree> selectTreepoolchanneltreedata() {
        Long supplierbranchid = ShiroUtils.getSysUser().getSupplierbranchid();

        return tPaypoolMapper.selectTreepoolchanneltreedata(supplierbranchid);
    }
    @Override

    public List<Ztree> selectTreepoolchanneltreedataChoose(Paymentpool paymentpool) {
        return tPaypoolMapper.selectTreepoolchanneltreedataChoose(paymentpool);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatepoolchannel(Paypoolchannel tPaypool) {
        Paypoolchannel paypoolchannel = tPaypoolMapper.selectpoolchanneldeleteFirst(tPaypool);
        if (paypoolchannel != null) {
            tPaypoolMapper.updatepoolchanneldeleteFirst(paypoolchannel);
        }
        String mutiplepaychannelid = tPaypool.getMutiplepaychannelid();
        if (mutiplepaychannelid != null && !StringUtils.isEmpty(mutiplepaychannelid)) {
            tPaypool.setStatus(1L);
            return tPaypoolMapper.updatepoolchannel(tPaypool);
        }
        return 1;
    }
}
