package com.example.holidaykeeper.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.holidaykeeper.dto.HolidaySearchCondition;
import com.example.holidaykeeper.dto.HolidaySliceResponse;
import com.example.holidaykeeper.service.HolidaySearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/holidays")
@RequiredArgsConstructor
public class HolidayController {

	private final HolidaySearchService holidaySearchService;

	@GetMapping
	@Operation(
		summary = "공휴일 검색",
		description = "국가, 연도, 기간, 커서 기반으로 공휴일 목록을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "조회 성공")
	})
	public HolidaySliceResponse search(
		@Parameter(description = "국가 이름", example = "South Korea")
		@RequestParam(required = false) String countryName,
		@Parameter(description = "연도", example = "2025")
		@RequestParam(required = false) Integer year,
		@Parameter(description = "조회 시작일", example = "2025-01-01")
		@RequestParam(required = false) LocalDate from,
		@Parameter(description = "조회 종료일", example = "2025-01-12")
		@RequestParam(required = false) LocalDate to,
		@Parameter(description = "마지막으로 조회한 Holiday ID (커서 페이징)", example = "15")
		@RequestParam(required = false) Long lastId,
		@Parameter(description = "페이지 크기", example = "20")
		@RequestParam(defaultValue = "20") int size
	) {
		HolidaySearchCondition condition = new HolidaySearchCondition(
			countryName, year, from, to, lastId, size
		);
		return holidaySearchService.search(condition);
	}
}
