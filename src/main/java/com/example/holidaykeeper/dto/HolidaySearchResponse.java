package com.example.holidaykeeper.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공휴일 검색 응답 DTO")
public record HolidaySearchResponse(
	@Schema(description = "Holiday ID", example = "10")
	Long id,

	@Schema(description = "국가명", example = "South Korea")
	String countryName,

	@Schema(description = "국가 코드", example = "KR")
	String countryCode,

	@Schema(description = "연도", example = "2025")
	int year,

	@Schema(description = "공휴일 영어명", example = "New Year's Day")
	String name,

	@Schema(description = "공휴일 현지어 표기", example = "새해")
	String localName,

	@Schema(description = "날짜", example = "2025-01-01")
	LocalDate date
) {
}
