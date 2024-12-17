package com.example.store.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");  // Tên của tệp tin properties (không cần phần mở rộng .properties)
        messageSource.setDefaultEncoding("UTF-8");  // Đảm bảo mã hóa UTF-8 để hỗ trợ tiếng Việt
        return messageSource;
    }
}