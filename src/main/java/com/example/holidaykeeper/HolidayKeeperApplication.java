package com.example.holidaykeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HolidayKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolidayKeeperApplication.class, args);
	}

}
