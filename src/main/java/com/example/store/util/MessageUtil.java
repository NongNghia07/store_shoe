package com.example.store.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public static String getMessage(String code) {
        // Sử dụng LocaleContextHolder để lấy ngôn ngữ hiện tại từ ngữ cảnh
        return messageSource.getMessage(code, null, "The server is down, please try again later", LocaleContextHolder.getLocale());
    }
}