package com.example.holidaykeeper.startup;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.holidaykeeper.service.HolidayService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {

	private final HolidayService holidayService;

	@Override
	public void run(ApplicationArguments args) {
		holidayService.loadInitialData();
	}
}
