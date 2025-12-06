package com.example.holidaykeeper.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커서 기반 공휴일 목록 응답")
public record HolidaySliceResponse(
	@Schema(description = "조회된 공휴일 리스트")
	List<HolidaySearchResponse> content,

	@Schema(description = "다음 페이지 존재 여부", example = "true")
	boolean hasNext
) {
}
