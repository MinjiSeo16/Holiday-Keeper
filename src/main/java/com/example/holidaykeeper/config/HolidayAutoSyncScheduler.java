package com.example.holidaykeeper.config;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.holidaykeeper.service.HolidayAutoSyncService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class HolidayAutoSyncScheduler {

	private final HolidayAutoSyncService autoSyncService;

	// 매년 1월 2일 01:00 (KST)
	@Scheduled(cron = "0 0 1 2 1 *", zone = "Asia/Seoul")
	public void autoSync() {
		log.info("AutoSync 스케줄러 실행");
		autoSyncService.syncPreviousAndCurrentYear();
	}
}
