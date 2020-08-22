package com.pay.typay.common.utils.telegram.impl;


import com.pay.typay.common.utils.telegram.TelegramUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;


/**
 * @Description
 * @Author Warren
 * @Date 2020-07-19
 * @Version 1.0
 */
@Slf4j
@Service
public class TelegramUtilImpl implements TelegramUtils {

    /**
     * 发送消息
     * @param chatId
     * @param text
     * @param parseMode   发送格式  默认html
     * @param token
     */
    @Override
    @Async("telegramThreadPoolTaskExecutor")
    public void sendMessage(String chatId, String text, String parseMode, String token){
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(text)
                .setParseMode(StringUtils.isEmpty(parseMode) ? ParseMode.HTML : parseMode);
        MessageSender messageSender = getMessageSender(token);
        try {
            messageSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Send Telegram Message Error{}",e);
        }
    }

    /**
     * 发送图片消息
     * @param chatId
     * @param fileName
     * @param inputStream
     */
    @Override
    @Async("telegramThreadPoolTaskExecutor")
    public void sendPhoto(String chatId, String fileName, InputStream inputStream, String token){
        SendPhoto sendPhoto = new SendPhoto().setChatId(chatId)
                .setPhoto(fileName,inputStream);
        MessageSender messageSender = getMessageSender(token);
        try {
            messageSender.sendPhoto(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Send Telegram Photo Error{}",e);
        }
    }

    /**
     * 初始化 MessageSender
     * @return
     */
    private MessageSender getMessageSender(String token){
        MessageSender messageSender = new DefaultSender(new DefaultAbsSender(new DefaultBotOptions()) {
            @Override
            public String getBotToken() {
                return token;
            }
        });
        return messageSender;
    }

}

