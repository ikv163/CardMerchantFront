package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.*;
import com.pay.typay.biz.mapper.*;
import com.pay.typay.biz.messages.AesTestUtil;
import com.pay.typay.biz.service.IPayChannelService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;

import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @desc: 支付渠道Service业务层处理
 * @author： Warren
 * @createtime： 2020/1/15
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
@Service
@DataSource(DataSourceType.typayv2)
public class PayChannelServiceImpl implements IPayChannelService {

    @Autowired
    private PaychannelMapper tPaychannelMapper;

    @Autowired
    private ThirdmerchantMapper tThirdmerchantMapper;

    @Autowired
    private BankinchannelMapper bankinchannelMapper;

    /**
     * 查询支付渠道
     *
     * @param paychannelid 支付渠道ID
     * @param type         1、三方   2、普通
     * @return
     */
    @Override
    public Paychannel selectPaychannelById(Long paychannelid, Long type) {
        Paychannel paychannel = tPaychannelMapper.selectPaychannelById(paychannelid, type);
        paychannel.setUserName(ShiroUtils.getSysUser().getUserName());
        return paychannel;
    }


    /**
     * 根据id集合查询支付渠道集合
     *
     * @param ids
     * @return
     */
    @Override
    public List<Paychannel> selectPayChannelByIds(List ids, Long type) {
        return ids.isEmpty() ? null : tPaychannelMapper.selectPayChannelByIds(ids, type);
    }

    @Override

    public List<Paychannel> selectBankPaychannelList(Paychannel tPaychannel) {
        return tPaychannelMapper.selectBankPaychannelList(tPaychannel);
    }




    /**
     * 新增支付渠道
     *
     * @param tPaychannel 支付渠道
     * @return
     */
    @Autowired
    TBankCommonMapper tBankCommonMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPaychannel(Paychannel tPaychannel) {
        Paychannel maxPaychannelID = tBankCommonMapper.getMaxPaychannelID();
        long paychannelID;
        if(maxPaychannelID == null){
            paychannelID = 0;
        }else{
            paychannelID = maxPaychannelID.getPaychannelid() + 1;

        }
        tPaychannel.setPaychannelid(paychannelID);
        tPaychannel.setSupplierbranchid(ShiroUtils.getSysUser().getSupplierbranchid());
        tPaychannel.setAgentId(ShiroUtils.getAgentId());
        //查询银行卡卡池配置的银行卡
        List<Long> bankIds = new ArrayList<Long>();
        if (!tPaychannel.getBankids().isEmpty()) {
            String[] ids = tPaychannel.getBankids().split(",");
            for (int i = 0; i < ids.length; i++) {
                bankIds.add(Long.valueOf(ids[i]));
            }
        } else {
            bankIds = tPaychannelMapper.selectBankIdsByPoolId(tPaychannel.getPaypoolid());
        }
        if (!bankIds.isEmpty()) {
            //insert t_bankinchannel
            tPaychannelMapper.insertBankInChannel(tPaychannel.getPaychannelid(), bankIds);
        }
        int i = tPaychannelMapper.insertPaychannel(tPaychannel);
        //新增的,就默认有该数据权限
        SysUser sysUser = ShiroUtils.getSysUser();
        return i;
    }

    @Autowired
    SysUserMapper userMapper;


    /**
     * 更新支付渠道
     *
     * @param paychannels 支付渠道
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePaychannels(List<Paychannel> paychannels) {
        return tPaychannelMapper.updatePayChannels(paychannels);
    }

    /**
     * 更新支付渠道
     *
     * @param paychannel 支付渠道
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePaychannel(Paychannel paychannel) {
        //可以考虑先判断是否修改卡池  再执行如下操作
        //delete t_bankinchannel By PaychannelId
        tPaychannelMapper.deleteByChannelId(paychannel.getPaychannelid());
        //查询银行卡卡池配置的银行卡
        List<Long> bankIds = new ArrayList();
        if (!paychannel.getBankids().isEmpty()) {
            String[] ids = paychannel.getBankids().split(",");
            for (int i = 0; i < ids.length; i++) {
                bankIds.add(Long.valueOf(ids[i]));
            }
        } else {
            bankIds = tPaychannelMapper.selectBankIdsByPoolId(paychannel.getPaypoolid());
        }
        if (!bankIds.isEmpty()) {
            tPaychannelMapper.deleteByBankId(bankIds);
        }
        //查询银行卡卡池配置的银行卡
//        List<Long> bankIds = tPaychannelMapper.selectBankIdsByPoolId(paychannel.getPaypoolid());
        if (!bankIds.isEmpty()) {
            //insert t_bankinchannel
            tPaychannelMapper.insertBankInChannel(paychannel.getPaychannelid(), bankIds);
        }
        return tPaychannelMapper.updatePayChannel(paychannel);
    }


    public static void main(String[] args) {
        System.out.println(AesTestUtil.decrypt("0de97f37af9888c1b98e0c1956a7a726a532858f43e8c8a225a4ac9e545cb5725073ad56279cb9418b13dd01bfb5da90cc45af1dfca92e4b63517fb85fb8e5df69251c611281d3880a24a07e6bcd6f98"));
    }
}
