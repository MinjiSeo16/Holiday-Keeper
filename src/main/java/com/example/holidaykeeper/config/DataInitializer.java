package com.example.holidaykeeper.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.holidaykeeper.service.HolidayService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer {

	private final HolidayService holidayService;

	@Async
	@EventListener(ApplicationReadyEvent.class)
	public void load() {
		holidayService.loadInitialData();
	}
}
