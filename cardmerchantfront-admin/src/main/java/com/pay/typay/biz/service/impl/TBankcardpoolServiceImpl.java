package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.TBankcardpool;
import com.pay.typay.biz.domain.TBankcardpoolInfo;
import com.pay.typay.biz.mapper.BankinchannelMapper;
import com.pay.typay.biz.mapper.TBankCommonMapper;
import com.pay.typay.biz.mapper.TBankcardCenterMapper;
import com.pay.typay.biz.mapper.TBankcardpoolMapper;
import com.pay.typay.biz.service.ITBankcardpoolService;
import com.pay.typay.common.annotation.DataScope;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;

import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 银行卡池Service业务层处理
 *
 * @author Warren
 * @date 2020-01-05
 */
@Service
@DataSource(DataSourceType.typayv2)
public class TBankcardpoolServiceImpl implements ITBankcardpoolService {
    @Autowired
    private TBankcardpoolMapper tBankcardpoolMapper;

    @Autowired
    private BankinchannelMapper bankinchannelMapper;

    @Autowired
    private TBankcardCenterMapper tbankcardCenterMapper;



    /**
     * 查询银行卡池列表
     *
     * @param tBankcardpool 银行卡池
     * @return 银行卡池
     */
    @Override

    public List<TBankcardpool> selectBankcardPoolList(TBankcardpool tBankcardpool) {
        tBankcardpool.setRemark(UtilsUser.getUserName());
        List<TBankcardpool> tBankcardpools = tBankcardpoolMapper.selectBankcardPoolList(tBankcardpool);
        return tBankcardpools;
    }

    @Override

    public List<TBankcardpool> selectBankcardPoolListsum(TBankcardpool tBankcardpool) {
        return tBankcardpoolMapper.selectBankcardPoolListsum(tBankcardpool);
    }

    @Override
    public List<TBankcardpool> selectBankcardPoolListcheck(TBankcardpool tBankcardpool) {
        return tBankcardpoolMapper.selectBankcardPoolListCheck(tBankcardpool);
    }


//    /**
//     * 查询银行卡池列表
//     *
//     * @return 银行卡池
//     */
//    @Override
//    @DataSource(value = DataSourceType.SLAVE)
//    public List<TBankcardpool> selectTBankCardPoolList() {
//        return tBankcardpoolMapper.selectTBankCardPoolList();
//    }

    /**
     * 查询银行卡池列表
     *
     * @param tBankcardpool 银行卡池
     * @return 银行卡池
     */
    @Override
    public List<TBankcardpool> selectTBankcardpoolList(TBankcardpool tBankcardpool) {
        return tBankcardpoolMapper.selectTBankcardpoolList(tBankcardpool);
    }


//
//    @Override
//    public List<TBankcardpool> selectTBankcardPoolGroupList(TBankcardpool tBankcardpool) {
//        return tBankcardpoolMapper.selectTBankcardPoolGroupList(tBankcardpool);
//    }


    /**
     * 查询银行卡池
     *
     * @param bankpooid 银行卡池ID
     * @return 银行卡池
     */
    @Override
    public TBankcardpool selectTBankcardpoolById(Long bankpooid) {
        return tBankcardpoolMapper.selectTBankcardpoolById(bankpooid);
    }


    /**
     * 新增银行卡池
     *
     * @param tBankcardpool 银行卡池
     * @return 结果
     */
    @Autowired
    TBankCommonMapper tBankCommonMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTBankcardpool(TBankcardpool tBankcardpool) {
        tBankcardpool.setCreatetime(new Date());
        tBankcardpool.setLastupdatetime(new Date());
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        tBankcardpool.setSupplierbranchid(userSupplierbranchid);
        String userName = UtilsUser.getUserName();
        tBankcardpool.setRemark(userName);
        TBankcardpoolInfo maxPoolId = tBankCommonMapper.getMaxPoolId();
        Long maxBankpooId;
        if(maxPoolId == null){
             maxBankpooId = 0L;
        }else{
             maxBankpooId = maxPoolId.getBankpooid();
        }

        //1.插入卡池5. 银行卡池查看的工作模式,不对
        Long bankpooid = maxBankpooId + 1;
        tBankcardpool.setBankpooid(bankpooid);
        tBankcardpool.setRemark(userName);
        int poolrs = tBankcardpoolMapper.insertTBankcardpool(tBankcardpool);
        //3.更新银行卡所在的卡池id
        String bankcardids = tBankcardpool.getBankcardids();
        if (bankcardids != null && !"".equals(bankcardids)) {
            int i = tBankcardpoolMapper.updateBankcardPoolid(bankpooid, bankcardids);
        }
        return poolrs;
    }

    /**
     * 修改银行卡池
     *
     * @param tBankcardpool 银行卡池
     * @return 结果
     */
    @Override
    public int updateTBankcardpool(TBankcardpool tBankcardpool) {
        //1.修改卡池信息
        //2.删除银行卡 poolid
        //3.修改银行卡 poolid
        String bankcardids = tBankcardpool.getBankcardids();
//        tBankcardpoolMapper.updateBankcardPoolidClean(tBankcardpool.getBankpooid(), bankcardids);
//        if (StringUtils.isNotEmpty(bankcardids)) {
//           情况原有卡的卡池分配
        tbankcardCenterMapper.resetBankcardPool(tBankcardpool.getBankpooid());
        tBankcardpoolMapper.updateTBankcardpool(tBankcardpool);
        return tBankcardpoolMapper.updateBankcardPoolid(tBankcardpool.getBankpooid(), bankcardids);

//            //4.根据卡池ID 查找 渠道表信息
//            //5.删除 银行卡渠道表信息
//            //6.关联 银行卡渠道表信息
//            List<String> channelids = bankinchannelMapper.selectChannelidsListBybankpoolid(tBankcardpool.getBankpooid());
//            if (!channelids.isEmpty()) {
//                String[] ss = channelids.toArray(new String[channelids.size()]);
//                bankinchannelMapper.deleteBankinchannelByChannelIds(ss);
//                bankinchannelMapper.deleteBankinchannelByIds(bankcarids);
//                List<Bankinchannel> addlist = new ArrayList<>();
//                for (String channelid : channelids) {
//                    for (String bankcarid : bankcarids) {
//                        Bankinchannel bankinchannel = new Bankinchannel();
//                        bankinchannel.setBankid(Long.valueOf(bankcarid));
//                        bankinchannel.setChannelid(Long.valueOf(channelid));
//                        addlist.add(bankinchannel);
//                    }
//                }
//                bankinchannelMapper.insertBankinchannelByBatch(addlist);
//            }
//        }

//        return tBankcardpoolMapper.updateTBankcardpool(tBankcardpool, bankcardids);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        String[] ss = list.toArray(new String[list.size()]);
        System.out.println(ss.length);
    }

    /**
     * 删除银行卡池对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTBankcardpoolByIds(String ids) {

        tBankcardpoolMapper.updateBatchBankcardPoolidClean(Convert.toStrArray(ids));
        return tBankcardpoolMapper.deleteTBankcardpoolByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除银行卡池信息
     *
     * @param bankpooid 银行卡池ID
     * @return 结果
     */
    @Override
    public int deleteTBankcardpoolById(Long bankpooid) {
        return tBankcardpoolMapper.deleteTBankcardpoolById(bankpooid);
    }

}
