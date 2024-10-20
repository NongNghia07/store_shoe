package com.example.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class LockConfig {

    @Bean
    public Lock lock() {
        return new ReentrantLock(); // Tạo và quản lý đối tượng lock bằng spring
    }
}
