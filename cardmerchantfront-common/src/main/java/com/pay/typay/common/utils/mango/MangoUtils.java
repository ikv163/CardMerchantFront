package com.pay.typay.common.utils.mango;

import java.io.InputStream;

public interface MangoUtils {

    /**
     * 异步发送纸飞机消息
     * @param userId
     * @param text
     * @param roomName
     * @param token
     */
    void sendMessage(String userId, String text, String roomName, String token);

    /**
     * 发送图片消息
     * @param chatId
     * @param fileName
     * @param inputStream
     * @param token
     */
    void sendPhoto(String chatId, String fileName, InputStream inputStream, String token);
}

