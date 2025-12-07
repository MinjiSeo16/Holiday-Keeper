package com.example.holidaykeeper.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.holidaykeeper.dto.HolidaySearchCondition;
import com.example.holidaykeeper.dto.HolidaySliceResponse;
import com.example.holidaykeeper.service.HolidayDeleteService;
import com.example.holidaykeeper.service.HolidayResyncService;
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
	private final HolidayResyncService holidayResyncService;
	private final HolidayDeleteService holidayDeleteService;

	@GetMapping
	@Operation(
		summary = "공휴일 검색",
		description = "국가, 연도, 기간, 커서 기반으로 공휴일 목록을 조회합니다."
	)
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공"))
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

	@PostMapping("/resync")
	@Operation(
		summary = "공휴일 재동기화",
		description = "특정 연도·국가의 공휴일 데이터를 외부 API에서 재조회하여 최신 상태로 갱신합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "재동기화 완료"),
		@ApiResponse(responseCode = "404", description = "해당 국가 코드가 존재하지 않음")
	})
	public ResponseEntity<Void> resync(
		@Parameter(description = "국가 코드", example = "KR")
		@RequestParam String countryCode,
		@Parameter(description = "연도", example = "2025")
		@RequestParam int year
	) {
		holidayResyncService.resync(countryCode, year);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	@Operation(
		summary = "공휴일 삭제",
		description = "특정 국가 코드와 연도를 기준으로 해당 연도의 공휴일 데이터를 모두 삭제합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "삭제 성공"),
		@ApiResponse(responseCode = "404", description = "국가 정보를 찾을 수 없음")
	})
	public ResponseEntity<Void> delete(
		@Parameter(description = "국가 코드", example = "KR")
		@RequestParam String countryCode,
		@Parameter(description = "연도", example = "2025")
		@RequestParam int year
	) {
		holidayDeleteService.deleteByCountryAndYear(countryCode, year);
		return ResponseEntity.noContent().build();
	}
}
