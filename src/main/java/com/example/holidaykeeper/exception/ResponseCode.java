package com.example.holidaykeeper.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {

	API_ERROR(500, "외부 API 호출 중 오류가 발생했습니다."),
	NOT_FOUND_COUNTRY(404,"해당 국가 코드가 존재하지 않습니다.");

	private final int status;
	private final String message;

	ResponseCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
