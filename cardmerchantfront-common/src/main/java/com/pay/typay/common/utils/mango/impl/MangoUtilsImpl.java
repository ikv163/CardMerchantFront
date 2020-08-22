package com.pay.typay.common.utils.mango.impl;

import com.alibaba.fastjson.JSONObject;
import com.pay.typay.common.utils.HttpClientUtil;
import com.pay.typay.common.utils.mango.MangoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class MangoUtilsImpl implements MangoUtils {

    @Value("${mango.mangoUrl}")
    private String mangoUrl;

    @Override
    public void sendMessage(String userId, String text, String roomName, String token) {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("roomname",roomName);
        parameter.put("text",text);
        String url =mangoUrl.replaceAll("token",token).replaceAll("userid",userId);
        log.info("芒果推送参数{},url{},消息内容{}",parameter,url,text);
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(parameter);
            httpClientUtil.mangoDoPost(url,jsonObject.toString());
        } catch (Exception e) {
            log.error("Send Telegram Message Error{}",e);
        }

    }



    @Override
    public void sendPhoto(String chatId, String fileName, InputStream inputStream, String token) {
    }
}
