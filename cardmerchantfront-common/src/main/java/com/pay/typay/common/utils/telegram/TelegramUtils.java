package com.pay.typay.common.utils.telegram;

import java.io.InputStream;

public interface TelegramUtils {

    /**
     * 异步发送纸飞机消息
     * @param chatId
     * @param text
     * @param parseMode
     * @param token
     */
    void sendMessage(String chatId, String text, String parseMode, String token);

    /**
     * 发送图片消息
     * @param chatId
     * @param fileName
     * @param inputStream
     * @param token
     */
    void sendPhoto(String chatId, String fileName, InputStream inputStream, String token);
}

