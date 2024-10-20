package com.example.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Bật tính năng JPA Auditing
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

}
