package com.pay.typay.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pay.typay.biz.domain.ParamSettingVO;
import com.pay.typay.biz.domain.TBankcard;
import com.pay.typay.biz.domain.TBankcardsummary;
import com.pay.typay.biz.mapper.ServiceAddressMapper;
import com.pay.typay.biz.mapper.TBankcardMapper;
import com.pay.typay.biz.mapper.TBankcardsummaryMapper;
import com.pay.typay.biz.service.ITBankcardService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.cache.RedisUtil;
import com.pay.typay.common.constant.RedisConstant;
import com.pay.typay.common.enums.DataSourceType;

import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.HttpClientUtil;
import com.pay.typay.common.utils.JsonUtils;
import com.pay.typay.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 银行卡Service业务层处理
 *
 * @author Warren
 * @date 2020-01-05
 */
@Service
public class TBankcardServiceImpl implements ITBankcardService {
    @Autowired
    private TBankcardMapper tBankcardMapper;

    @Autowired
    private TBankcardsummaryMapper tBankcardsummaryMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ServiceAddressMapper serviceAddressMapper;

    private static String ServiceKey = "WithdrawCatchSameBankOrder";
    /**
     * 根据ID查询银行卡集合
     *
     * @param bankids 银行卡ID集合
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public List<TBankcard> selectTBankcardsByIds(List bankids) {
        return Optional.ofNullable(tBankcardMapper.selectTBankcardsByIds(bankids)).orElse(null);
    }

    /**
     * 申请资金、修改状态查询用
     *
     * @param bankids 银行卡ID集合
     * @return 银行卡集合
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public List<TBankcard> selectBankcardByIds(List bankids) {
        return Optional.ofNullable(tBankcardMapper.selectBankcardByIds(bankids)).orElse(null);
    }

    /**
     * 查询中转卡
     *
     * @param bankcard 银行卡
     * @return 中转卡列表
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)

    public List<TBankcard> fetchTransitCardList(TBankcard bankcard) {
        List<String> types = null;
        if(bankcard.getBanktype() != null && bankcard.getBanktype() != "") {
             types =  Arrays.asList(bankcard.getBanktype().split(","));
             bankcard.setTypes(types);
        }
        List<TBankcard> bankcards = tBankcardMapper.fetchTransitCardList(bankcard);
        if (bankcards.size() == 0){
            return  bankcards;
        }
        for(TBankcard bankinfo :  bankcards) {
            String date = DateUtils.dateTime();
            Object OfflineCard = redisUtil.get("OfflineCard_" + bankinfo.getBankcode());
            bankinfo.setCacheAmount(String.valueOf(redisUtil.get("handleOverBalance_" + bankinfo.getBankid())));
            bankinfo.setOfflineCard(Optional.ofNullable(OfflineCard).orElse("0")+"");
            Integer total =  (Integer)redisUtil.get("total" + bankinfo.getBankcode() + date);
            bankinfo.setAllOrderCount(total == null ? 0 : total);
            Integer success = (Integer) redisUtil.get("success" + bankinfo.getBankcode() + date);

            bankinfo.setSuccessOrderCount(success == null ? 0 :success);
            bankinfo.setSuccessRate(bankinfo.getAllOrderCount() == 0 ?0:
                    bankinfo.getSuccessOrderCount() / bankinfo.getAllOrderCount() * 100);
            bankinfo.setOtherCount(bankinfo.getAllOrderCount() - bankinfo.getSuccessOrderCount());
        }
        bankcards.sort(Comparator.comparing(TBankcard :: getOfflineCard));
        return bankcards;
    }

    /**
     * 批量更新银行卡状态
     *
     * @param bankcards
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    @Transactional(rollbackFor = Exception.class)
    public int updateTBankcard(List<TBankcard> bankcards) {
        return tBankcardMapper.updateTBankcard(bankcards);
    }

    /**
     * 批量更新银行卡状态
     *
     * @param bankcard
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    @Transactional(rollbackFor = Exception.class)
    public int updateTBankcardById(TBankcard bankcard) {
        return tBankcardMapper.updateTBankcardById(bankcard);
    }


    @Override
    public int checkCard(String bankids, String money, String url) {
        Map<String, String> map = new HashMap<>(2);
        map.put("BankIDs", bankids);
        map.put("money", money);
        String result = HttpClientUtil.doPost(url + "/backendapis/order/testcards", JsonUtils.objectToJson(map));
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject.getInteger("status");
        }
        return 0;
    }

    @Override
    @DataSource(value = DataSourceType.typayv2)
    public int reset(List bankcardids) {
        List<TBankcardsummary> bankcardsummaries = tBankcardsummaryMapper.selectTBankcardsummaryById(bankcardids);
        if (bankcardsummaries.isEmpty()) {
            return 1;
        }
        //重置redis余额
        bankcardsummaries.stream().forEach(tBankcard -> redisUtil.set("handleOverBalance_" + tBankcard.getBankid(), BigDecimal.ZERO, RedisConstant.HOUR));
        return tBankcardsummaryMapper.updateTBankcardsummarys(bankcardsummaries);
    }

    /**
     * 开关
     *
     * @param bankids
     * @param status
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public int openAndClose(String bankids, Long status) {
        List<TBankcard> bankcards = tBankcardMapper.selectTBankcardsByIds(buildBankIds(bankids));
        if (bankcards.isEmpty()) {
            return 0;
        }
        bankcards.stream().forEach(tBankcard -> {
            if (status == 1) {
                redisUtil.set("OfflineCard_" + tBankcard.getBankcode().trim(), 1);
            } else {
                redisUtil.del("OfflineCard_" + tBankcard.getBankcode().trim());
            }
        });
        return 1;
    }

    /**
     * 上下卡
     *
     * @param bankids·
     * @param status
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public JSONArray downAndUp(String bankids, Long status) {
        List<TBankcard> bankcards = tBankcardMapper.selectTBankcardsByIds(buildBankIds(bankids));
        if (bankcards.isEmpty()) {
            return null;
        }
        return buildData(bankcards, status);
    }

    @Override
    @DataSource(value = DataSourceType.tyadmin)
    @Transactional(rollbackFor = Exception.class)
    public int onlySameBank(Long status) {
        if(status == null){
            return 0;
        }
        if (status == 0){
            serviceAddressMapper.update("0",ServiceKey);
            redisUtil.set("withdrawCatchSameBankOrder","0",RedisConstant.DAY);
        }else if (status == 1){
            serviceAddressMapper.update("1",ServiceKey);
            redisUtil.set("withdrawCatchSameBankOrder","1",RedisConstant.DAY);
        }
        return 1;
    }

    @Override
    public int asktrans(String bankids, String money, String perMinTrans, String url) {
        Map<String, String> map = new HashMap<>(4);
        map.put("BankIDs", bankids);
        map.put("money", money);
        map.put("PerMinTrans", perMinTrans);
        String result = HttpClientUtil.doPost(url + "/backendapis/order/asktrans", JsonUtils.objectToJson(map));
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject.getInteger("status");
        }
        return 0;
    }

    /**
     * 参数设置
     * @param paramSetting
     * @return
     */
    @Override
    public int paramSetting(ParamSettingVO paramSetting) {
        if(paramSetting == null){
            return 0;
        }
        //WeskerAutoOfflineBankSetting 兼容php java暂时不用
        StringBuilder sb = new StringBuilder();
        sb.append("[{\"transTimes\":\"").append(paramSetting.getTransTimes()).append("\",")
                .append("\"successRate\":\"").append(paramSetting.getSuccessRate()).append("\",")
                .append("\"status\":\"").append(paramSetting.getStatus()?"1":"0").append("\"}]");
        redisUtil.set("WeskerAutoOfflineBankSetting",sb.toString());

        redisUtil.set("Java_WeskerAutoOfflineBankSetting",paramSetting);
        return 1;
    }

    /**
     * 获取入款参数
     * @return
     */
    @Override
    public ParamSettingVO getParam() {
        ParamSettingVO paramSettingVO = (ParamSettingVO)redisUtil.get("Java_WeskerAutoOfflineBankSetting");

        //第一次java取不到值 就取php
        if(paramSettingVO == null){
            Object obj = redisUtil.get("WeskerAutoOfflineBankSetting");
            if(Objects.isNull(obj)){
                return null;
            }
            JSONObject json = JSON.parseObject(obj.toString());
            paramSettingVO.setTransTimes(json.getInteger("transTimes"));
            paramSettingVO.setSuccessRate(json.getString("successRate"));
            paramSettingVO.setStatus(json.getInteger("status") == 1);
        }
        return paramSettingVO;
    }
    /**
     * 查询中转卡
     *
     * @param bankcard 银行卡
     * @return 中转卡列表
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)

    public List<TBankcard> fetchTransitCard(TBankcard tBankcard) {
        List<String> types = null;
        if(tBankcard.getBanktype() != null && tBankcard.getBanktype() != "") {
            types =  Arrays.asList(tBankcard.getBanktype().split(","));
            tBankcard.setTypes(types);
        }
        List<TBankcard> bankcards = tBankcardMapper.fetchTransitCard(tBankcard);
        if(bankcards.size() == 0){
            return  bankcards;
        }
        for(TBankcard bankinfo :  bankcards) {
            Object OfflineCard = redisUtil.get("OfflineCard_" + bankinfo.getBankcode());
            bankinfo.setOfflineCard(Optional.ofNullable(OfflineCard).orElse("0")+"");
            bankinfo.setCacheAmount(String.valueOf(redisUtil.get("handleOverBalance_" + bankinfo.getBankid())));
        }
        bankcards.sort(Comparator.comparing(TBankcard :: getOfflineCard));
        return bankcards;
    }

    @Override
    public int stopWithdraw(Integer status) {
        if(status.equals(1)){
            redisUtil.set("handlemonitor_status", "stop");
            redisUtil.expire("handlemonitor_status",1800L);
        }else{
            redisUtil.del("handlemonitor_status");
        }
        return  1;
    }

    @Override
    public Boolean getWithdrawStatus() {
       String Status =  (String) redisUtil.get("handlemonitor_status");
       return  Status == null || !"stop".equals(Status);
    }


    /**
     * 构造前端发送websocket数据内容
     * @param bankcards
     * @param status
     * @return
     */
    private JSONArray buildData(List<TBankcard> bankcards, Long status) {
        JSONArray array = new JSONArray();
        //过滤掉未找到设备的数据
        bankcards.stream().filter(b ->
                !(redisUtil.get("UsbKey" + b.getBankcode()) == null
                        && redisUtil.get("UsbClick" + b.getBankcode()) == null))
                .forEach(b -> {
                    JSONObject object = new JSONObject();
                    JSONObject bankObject = new JSONObject();
                    Map<String, Object> map = new HashMap<>(2);
                    int connectType = 0;
                    if (status == 1) {
                        map.put("UsbKey", redisUtil.get("UsbKey" + b.getBankcode()));
                        map.put("UsbClick", redisUtil.get("UsbClick" + b.getBankcode()));
                        connectType = 1;
                    } else if (status == 3) {
                        map.put("UsbKey", redisUtil.get("UsbKey" + b.getBankcode()));
                        connectType = 2;
                    }
                    bankObject.put("OwnerName", b.getOwnername());
                    bankObject.put("BankAccount", b.getOwnername());
                    bankObject.put("BankNo", b.getOwnername());
                    bankObject.put("BankNum", b.getOwnername());
                    bankObject.put("LoginPassword", b.getOwnername());
                    bankObject.put("PayPassword", b.getOwnername());
                    bankObject.put("ServerTime", DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));
                    object.put("UserActionType",status == 1 || status == 3 || status == 4 ? 4 : 5);
                    object.put("DeviceList",map);
                    object.put("BankType",b.getBanktype());
                    object.put("ConnectType",connectType);
                    object.put("IsExec",false);
                    object.put("ExecErrorText","");
                    object.put("ClientID","");
                    object.put("MachineCode","");
                    array.add(object);
                });
        return array;
    }

    private List buildBankIds(String bankids) {
        return Arrays.asList(Optional.ofNullable(bankids).orElse("").split(","));
    }
}
