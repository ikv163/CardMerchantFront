package com.pay.typay.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pay.typay.biz.domain.TBanktrans;
import com.pay.typay.biz.domain.TBanktransReversal;
import com.pay.typay.biz.mapper.TBanktransMapper;
import com.pay.typay.biz.mapper.TBanktransReversalMapper;
import com.pay.typay.biz.reports.domain.BankAccountRecord;
import com.pay.typay.biz.service.ITBanktransService;
import com.pay.typay.common.annotation.DataScope;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;

import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.HttpClientUtil;
import com.pay.typay.common.utils.JsonUtils;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.util.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 银行卡流水Service业务层处理
 * 
 * @author Warren
 * @date 2020-01-08
 */
@Service
public class TBanktransServiceImpl implements ITBanktransService 
{
    protected final Logger logger = LoggerFactory.getLogger(TBanktransServiceImpl.class);
    @Autowired
    private TBanktransMapper tBanktransMapper;

    @Autowired
    private TBanktransReversalMapper tBanktransReversalMapper;

    /**
     * 查询银行卡流水
     * 
     * @param id 银行卡流水ID
     * @return 银行卡流水
     */
    @Override
    public TBanktrans selectTBanktransById(Long id)
    {
        return tBanktransMapper.selectTBanktransById(id);
    }

    @Override
    @DataSource(value = DataSourceType.typayv2)
    public List<TBanktrans> selectBankTransByIds(String idsStr) {
        List bankCardIds = Arrays.asList(Optional.ofNullable(idsStr).orElse("").split(","));
        return tBanktransMapper.selectBankTransByIds(bankCardIds);
    }

    /**
     * 查询银行卡流水列表
     * 
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水
     */
    @Override
    @DataSource(value = DataSourceType.typayv2slave)

    public List<TBanktrans> selectTBanktransList(TBanktrans tBanktrans)
    {
        return tBanktransMapper.selectTBanktransList(tBanktrans);
    }

    /**
     * 查询银行卡流水列表
     *
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水
     */
    @Override
    @DataSource(value = DataSourceType.typayv2slave)

    public List<TBanktrans> selectEBanktransList(TBanktrans tBanktrans)
    {
        return tBanktransMapper.selectTBanktransList(tBanktrans);
    }

    /**
     * 查询异常银行卡流水列表
     *
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水
     */
    @Override
    @DataSource(value = DataSourceType.typayv2slave)

    public List<TBanktrans> selectEBanktransListTotal(TBanktrans tBanktrans)
    {
        return tBanktransMapper.selectTotalBanktransList(tBanktrans);
    }



    /**
     * 查询银行卡流水列表统计数据
     *
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水
     */
    @Override
    @DataSource(value = DataSourceType.typayv2slave)

    public List<TBanktrans> selectTotalBanktransList(TBanktrans tBanktrans) {
        return tBanktransMapper.selectTotalBanktransList(tBanktrans);
    }

    /**
     * 新增银行卡流水
     *
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public int insertTBanktrans(TBanktrans tBanktrans)
    {
        return tBanktransMapper.insertTBanktrans(tBanktrans);
    }

    /**
     * 修改银行卡流水
     *
     * @param tBanktrans 银行卡流水
     * @return 结果
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    @Transactional(rollbackFor = Exception.class)
    public int updateTBanktrans(List<TBanktrans> tBanktrans)
    {
        return tBanktransMapper.updateTBanktrans(tBanktrans);
    }

    /**
     * 删除银行卡流水对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTBanktransByIds(String ids)
    {
        return tBanktransMapper.deleteTBanktransByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除银行卡流水信息
     *
     * @param id 银行卡流水ID
     * @return 结果
     */
    @Override
    public int deleteTBanktransById(Long id)
    {
        return tBanktransMapper.deleteTBanktransById(id);
    }

    /**
     * 手动匹配流水
     * @param url
     * @param banktrans
     * @return
     */
    @Override
    public int commit(String url,TBanktrans banktrans){
        Map<String,String> map = new HashMap<>(4);
        map.put("TransAmount", banktrans.getTransamount().toString());
        map.put("Name",banktrans.getName());
        map.put("BankAccount",banktrans.getBankaccount());
        String result = HttpClientUtil.doPost(url+"/backendapis/order/commit", JsonUtils.objectToJson(map));
        if(StringUtils.isNotEmpty(result)){
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject.getInteger("status");
        }
        return 1;
    }

    /**
     * 修改订单号
     * @param url
     * @param idsStr
     * @param orderNumber
     * @return
     */
    @Override
    public int editMerchantOrderId(String url, String idsStr, String orderNumber) {
        Map<String,String> map = new HashMap<>(2);
        map.put("PayChannelID",idsStr.trim());
        map.put("orderNumber",orderNumber.trim());
        String result = HttpClientUtil.doPost(url+"/backendapis/order/edit", JsonUtils.objectToJson(map));
        if(StringUtils.isNotEmpty(result)){
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject.getInteger("status");
        }
        return 1;
    }

    /**
     * 出来银行卡流水
     * @param tBanktrans
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.typayv2slave)

    public List<TBanktrans> selectWithdrawtransList(TBanktrans tBanktrans) {
        return tBanktransMapper.selectWithdrawtransList(tBanktrans);
    }

    /**
     * 查询出款流水银行卡流水列表统计
     *
     * @param tBanktrans 银行卡流水
     * @return 银行卡流水
     */
    @Override
    @DataSource(value = DataSourceType.typayv2slave)

    public List<TBanktrans> selectWithdrawtransListTotle(TBanktrans tBanktrans)
    {
        return tBanktransMapper.selectWithdrawtransListTotal(tBanktrans);
    }


    @Override
    @DataSource(value = DataSourceType.typayv2)
    public Map<String,Object> selectReversalTransList(TBanktransReversal reversal ,boolean totalType) {
        Map<String,Object> map = new HashMap<>();
        if(totalType){
            reversal.setTranslevel(0);
        }
        if(StringUtils.isNotBlank(reversal.getQueryTime())){
            reversal.getParams().put("beginTranstime",reversal.getQueryTime().trim() + " 00:00:00" );
            reversal.getParams().put("endTranstime",reversal.getQueryTime().trim() + " 23:59:59" );
        }

        //先找出冲正流水，再匹配主流水
        List<TBanktransReversal> reversals = tBanktransReversalMapper.selectReversalTransList(reversal);
        if (reversals.isEmpty()){
            map.put("list", new ArrayList<>());
            return map;
        }
        //主流水匹配规则： 1、交易方式=支出  2、交易时间= 冲正流水相差10分钟内  3、余额=冲正流水余额-交易金额
        int mateCount=0;
        BigDecimal mateAmount = new BigDecimal(0);
        int mateCountw=0;
        BigDecimal mateAmountw = new BigDecimal(0);

        List<TBanktransReversal> results = new ArrayList<>();
        for (TBanktransReversal vo : reversals) {
            TBanktransReversal query = new TBanktransReversal();
            query.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
            query.setBankaccount(vo.getBankaccount());
            query.setTransamount(vo.getTransamount());
            query.setBalance(vo.getBalance().subtract(vo.getTransamount()));
            query.getParams().put("beginTranstime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,
                    new Date(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,vo.getTranstime()).getTime() - (10*60*1000))));
            query.getParams().put("endTranstime",vo.getTranstime());
            query = tBanktransReversalMapper.selectMainTransList(query);
            //流水类型
            if(StringUtils.isNotBlank(vo.getMerchantorderid()) && query != null && query.getStatus() ==2){
                vo.setTransordertype(vo.getTransordertype() + "(匹配成功)");
            }else{
                vo.setTransordertype(vo.getTransordertype() + "(匹配失败)");
            }
            //流水状态 过滤
            if(reversal.getTranslevel() == null || reversal.getTranslevel() == 0){
                if (query != null){
                    results.add(query);
                }
                results.add(vo);
            }else if (reversal.getTranslevel() == 1){
                //主流水成功,冲正流水匹配主流水成功
                if (query != null &&  query.getStatus() == 2 && StringUtils.isNotBlank(vo.getMerchantorderid())){
                    results.add(query);
                    results.add(vo);
                }
            }else if (reversal.getTranslevel() == 2){
                //主流水成功,冲正流水匹配主流水失败
                if (query != null &&  query.getStatus() == 2 && StringUtils.isBlank(vo.getMerchantorderid()) ){
                    results.add(query);
                    results.add(vo);
                }
            }else if (reversal.getTranslevel() == 3){
                //主流水失败,冲正流水匹配主流水失败
                if (query != null &&  query.getStatus() == 3 && StringUtils.isBlank(vo.getMerchantorderid())){
                    results.add(query);
                    results.add(vo);
                }
            }
            //计算笔数
            if(StringUtils.isNotBlank(vo.getMerchantorderid())){
                mateCount++;
                mateAmount.add(vo.getTransamount());
                if (query != null && query.getStatus() ==2){
                    mateCountw++;
                    mateAmountw.add(query.getTransamount());
                }
            }

        }
        TBanktransReversal total = new TBanktransReversal();
        total.setMateCount(mateCount);
        total.setMateAmount(mateAmount);
        total.setMateCountw(mateCountw);
        total.setMateAmountw(mateAmountw);
        map.put("list", results);
        map.put("total", total);
        return map;
    }

    @Override
    @DataSource(value = DataSourceType.typayv2)
    public AjaxResult rebaseorder(String queryTime) {
        int suc = 0;
        int err = 0;

        TBanktransReversal reversal = new TBanktransReversal();
        reversal.setMerchantorderidfilter("1");
        if(StringUtils.isNotBlank(queryTime)){
            reversal.getParams().put("beginTranstime",queryTime.trim() + " 00:00:00" );
            reversal.getParams().put("endTranstime",queryTime.trim() + " 23:59:59" );
        }
        //先找出冲正流水，再匹配主流水
        List<TBanktransReversal> reversals = tBanktransReversalMapper.selectReversalTransList(reversal);
        if (reversals.isEmpty()){
            return AjaxResult.success("所选择的日期没有补单数据！");
        }

        for (TBanktransReversal bankTrans : reversals) {
            try {
                TBanktransReversal query = new TBanktransReversal();
                query.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
                query.setBankaccount(bankTrans.getBankaccount());
                query.setTransamount(bankTrans.getTransamount());
                query.setBalance(bankTrans.getBalance().subtract(bankTrans.getTransamount()));
                query.getParams().put("beginTranstime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,
                        new Date(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,bankTrans.getTranstime()).getTime() - (10*60*1000))));
                query.getParams().put("endTranstime",bankTrans.getTranstime());
                query.setStatus(2L);
                query = tBanktransReversalMapper.selectMainTransList(query);
                if (query == null){
                    continue;
                }
                if (StringUtils.isBlank(query.getMerchantorderid())){
                    continue;
                }
                String orderid;
                if(StringUtils.isNotBlank(bankTrans.getMd5sign())){
                    orderid = bankTrans.getMd5sign().substring(0,10);
                }else{
                    orderid = UUID.randomUUID().toString().substring(0,10);
                }
                BigDecimal newbalace = bankTrans.getBalance().add(bankTrans.getTransamount()).add(bankTrans.getTransfee());
                BankAccountRecord record = new BankAccountRecord();
                record.setBankid(query.getBankid());
                record.setTransid(bankTrans.getId());
                record.setOrderid(orderid);
                record.setMerchantorderid(query.getMerchantorderid());
                record.setTranstype(0);
                record.setTransamount(bankTrans.getTransamount());
                record.setPaidamount(bankTrans.getTransamount());
                record.setPaychannelfee(bankTrans.getTransfee());
                record.setPrebalance(newbalace.doubleValue());
                record.setBalance(bankTrans.getBalance().doubleValue());
                record.setSupplierpaidchannelfee(new BigDecimal(0));
                record.setSupplierbranchid(bankTrans.getSupplierbranchid());
                record.setCreatetime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,bankTrans.getCreatetime()));
                tBanktransReversalMapper.insertBankAccountRecord(record);
                suc ++ ;
            }catch (Exception e){
                logger.error("冲正流水补单异常" + queryTime,e);
                err++;
            }
        }
        return AjaxResult.success(StringUtils.format("补单成功笔数:{}，补单失败笔数:{}" , suc,err));
    }
}
