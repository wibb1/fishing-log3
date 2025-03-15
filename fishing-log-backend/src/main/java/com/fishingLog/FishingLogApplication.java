package com.fishingLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.fishingLog.spring.model")
public class FishingLogApplication {
	public static void main(String[] args) {
		SpringApplication.run(FishingLogApplication.class, args);
	}
}
