package com.pay.typay.biz.messages;

import com.alibaba.fastjson.JSON;
import com.pay.typay.common.utils.reflect.ReflectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

public class ConstantsSelectUIToExcel {

    public static final String MerchantTransType = "0=充值,1=提款,2=准备金,3=商户下发";
    public static final String TransType = "0=支出,1=收入,2=内转";
    public static final String TransOrderType = "1=存款交易流水,2=提款交易流水,3=内转交易流水,4=测卡交易流水";
    public static final String PayType = "0=网银转账,110=支付宝转银行卡,1=银行卡转账,2=银联快捷,112=银行卡大额充值,3=微信,113=云闪付转银行卡,4=支付宝,114=离线支付宝转银行卡,5=QQ钱包,115=微信转银行卡,6=京东钱包,116=微信个码,7=云闪付,999=江西农商码";
    public static final String OrderStatus = "0=创建,1=处理中,2=出款中,3=成功,4=成功,-1=挂起,5=超时,6=超时失败,7=失败";
    public static final String AgentOrderStatus = "0=创建,1=处理中,2=出款中,3=正常支付,4=成功,-1=挂起,5=超时,6=超时失败,7=失败";
    public static final String AgentPayType = "1=银行卡,2=第三方,3=其他方式";
    public static final String OrderNotifyStatus = "0=未成功通知,1=已成功通知,2=通知超时异常";
    public static final String WorkType = "1=收款,2=出款,3=中转,4=下发,5=手动出款,6=风云出款,7=风云入款,8=结汇,9=第三方,10=第四方,11=技术支持,12=U盾";
    public static final String Statussearch = "0=停用,1=启用,100=在线,-2=预启用,-3=交易限额停用,-4=余额超额停用";
    public static final String UserLevel = "11=VIP11,12=VIP12,13=VIP13,14=VIP14,15=VIP15,16=VIP16,17=VIP17,18=VIP18,19=VIP19,0=不限,1=VIP01,2=VIP02,3=VIP03,4=VIP04,5=VIP05,6=VIP06,7=VIP07,8=VIP08,999=VIP0,9=VIP09,20=VIP20,10=VIP10";







    public static final String getSelectUIType(String key) {
       ConstantsSelectUI selectUI = new ConstantsSelectUI();
        String workType = ReflectUtils.getFieldValue(selectUI, key);
        Map<String,String> obj = (Map) JSON.parse(workType);
        String str="";
        for (Map.Entry<String,String> entry: obj.entrySet()) {
            if (StringUtils.isEmpty(entry.getKey())){
                continue;
            }
            str += entry.getKey() + "=" + entry.getValue() + ",";
        }
        if (!StringUtils.isEmpty(str)){
            str = str.substring(0,str.length()-1);
        }
        return str;
    }
    public static void main(String[] args) {
        System.out.println(getSelectUIType("UserLevel"));
    }

}

